package com.example.inclass08_simplified.Fragments;

import android.os.Bundle;

import androidx.camera.core.CameraSelector;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inclass08_simplified.R;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCameraController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCameraController extends Fragment implements View.OnClickListener {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private CameraSelector cameraSelector;
    private Preview preview;
    private ImageCapture imageCapture;
    private ProcessCameraProvider cameraProvider = null;
    private int lenseFacing;
    private int lenseFacingBack;
    private int lenseFacingFront;

    private DisplayTakenPhoto mListener;

    private FloatingActionButton buttonTakePhoto;
    private FloatingActionButton buttonSwitchCamera;
    private FloatingActionButton buttonOpenGallery;


    public FragmentCameraController() {
        // Required empty public constructor
    }

    public static FragmentCameraController newInstance() {
        FragmentCameraController fragment = new FragmentCameraController();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lenseFacingBack = CameraSelector.LENS_FACING_BACK;
        lenseFacingFront = CameraSelector.LENS_FACING_FRONT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_camera_controller, container, false);
        previewView = rootView.findViewById(R.id.previewView);

        buttonTakePhoto = rootView.findViewById(R.id.buttonTakePhoto);
        buttonSwitchCamera = rootView.findViewById(R.id.buttonSwitchCamera);
        buttonOpenGallery = rootView.findViewById(R.id.buttonOpenGallery);

        buttonTakePhoto.setOnClickListener(this);
        buttonSwitchCamera.setOnClickListener(this);
        buttonOpenGallery.setOnClickListener(this);

//        default lense facing....
        lenseFacing = lenseFacingBack;

        setUpCamera(lenseFacing);

        return rootView;
    }



    private void setUpCamera(int lenseFacing) {
        //            binding hardware camera with preview, and imageCapture.......
        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        cameraProviderFuture.addListener(()->{
            preview = new Preview.Builder()
                    .build();
            preview.setSurfaceProvider(previewView.getSurfaceProvider());
            imageCapture = new ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build();
            try {
                cameraProvider = cameraProviderFuture.get();
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(lenseFacing)
                        .build();
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle((LifecycleOwner) getContext(),cameraSelector, preview, imageCapture);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        },ContextCompat.getMainExecutor(getContext()));

    }
    //  TakePhoto implementation....
    private void takePhoto() {
        long timestamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/CameraX-Image");
        }

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions
                .Builder(
                getContext().getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
        )
                .build();


        imageCapture.takePicture(outputFileOptions,
                ContextCompat.getMainExecutor(getContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        Log.d("demo", "onImageSaved: "+ outputFileResults.getSavedUri());
                        mListener.onTakePhoto(outputFileResults.getSavedUri());
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonTakePhoto:
                takePhoto();
                break;
            case R.id.buttonOpenGallery:
                mListener.onOpenGalleryPressed();
                break;
            case R.id.buttonSwitchCamera:
                if(lenseFacing==lenseFacingBack){
                    lenseFacing = lenseFacingFront;
                    setUpCamera(lenseFacing);
                }else{
                    lenseFacing = lenseFacingBack;
                    setUpCamera(lenseFacing);
                }
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DisplayTakenPhoto){
            mListener = (DisplayTakenPhoto) context;
        }else{
            throw new RuntimeException(context+" must implement DisplayTakenPhoto");
        }
    }

    public interface DisplayTakenPhoto{
        void onTakePhoto(Uri imageUri);
        void onOpenGalleryPressed();
    }


}