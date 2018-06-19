package com.example.pm.assistant.assistant;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.IOException;

interface FaceDetectCallback {
    void onFaceDetected(String b64Image);
}

public class AssistantMain extends Service implements FaceDetectCallback {
    private static final String TAG = "AssistantService";
    private CameraSource camera;
    private FaceDetector faceDetector;
    private Speaker speaker;

    private static final int RC_HANDLE_GMS = 9001;

    @Override
    public void onFaceDetected(String b64Image) {
        Log.d(TAG, b64Image);
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
                    callback.onFaceDetected(b64EncodeImage(bytes));
                }
            });
        }

        private String b64EncodeImage(byte[] data)
        {
            char[] tbl = {
                    'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
                    'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
                    'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
                    'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/' };

            StringBuilder buffer = new StringBuilder();
            int pad = 0;
            for (int i = 0; i < data.length; i += 3) {

                int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
                if (i + 1 < data.length) {
                    b |= (data[i+1] & 0xFF) << 8;
                } else {
                    pad++;
                }
                if (i + 2 < data.length) {
                    b |= (data[i+2] & 0xFF);
                } else {
                    pad++;
                }

                for (int j = 0; j < 4 - pad; j++) {
                    int c = (b & 0xFC0000) >> 18;
                    buffer.append(tbl[c]);
                    b <<= 6;
                }
            }
            for (int j = 0; j < pad; j++) {
                buffer.append("=");
            }

            return buffer.toString();
        }
    }
}