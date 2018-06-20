package com.example.pm.assistant.assistant;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.pm.assistant.faceppcom.FaceppUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.IOException;
import java.util.List;

import android.util.Base64;

interface FaceDetectCallback {
    void onFaceDetected(String b64Image);
}

public class AssistantMain extends Service implements FaceDetectCallback, FaceppUtils.FaceTokensResultCallback {
    private static final String TAG = "AssistantService";
    private CameraSource camera;
    private FaceDetector faceDetector;
    private Speaker speaker;
    private AsyncTask bgTask;

    @Override
    public void onFaceDetected(String b64Image) {
        Log.d(TAG, "FACE DETECTED!!!!!!!!!!!!!!");
        if(bgTask == null || bgTask.getStatus() == AsyncTask.Status.FINISHED)
            bgTask = new FaceppUtils.FetchFaceTokensTask(this).execute(b64Image);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public AssistantMain() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camera.stop();
        camera.release();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Context context = getApplicationContext();

        faceDetector = new FaceDetector.Builder(context).setClassificationType(FaceDetector.ALL_CLASSIFICATIONS).build();
        faceDetector.setProcessor(new LargestFaceFocusingProcessor(faceDetector, new FaceTracker(this)));

        if(!faceDetector.isOperational()) {
            Log.e(TAG, "FaceDetector isn't operational");
            stopSelf();
        }

        camera = new CameraSource.Builder(context, faceDetector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(0.5f)
                .build();


        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if(code != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Google API error");
            stopSelf();
        }

        if(camera != null) {
            try {
                camera.start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFaceTokensCallback(List<String> tokens) {
        if(tokens.size() == 0)
            Log.d(TAG, "RECEIVED 0 TOKENS!!!!!!!!");
        for(String token : tokens)
            Log.d(TAG, token);
    }

    private class FaceTracker extends Tracker<Face> {
        private FaceDetectCallback callback;

        public FaceTracker(FaceDetectCallback callback) {
            this.callback = callback;
        }
        @Override
        public void onNewItem(int i, Face face) {
            camera.takePicture(null, new CameraSource.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes) {
                    callback.onFaceDetected(Base64.encodeToString(bytes, Base64.NO_WRAP));
                }
            });
        }
    }
}