package com.example.pm.assistant.assistant;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.pm.assistant.data.Contato;
import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;
import com.example.pm.assistant.faceppcom.FaceSetUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

interface FaceDetectCallback {
    void onFaceDetected(byte[] imgBytes);
}

public class AssistantMain extends Service implements FaceDetectCallback {
    private static final String TAG = "AssistantService";
    private CameraSource camera;
    private FaceDetector faceDetector;
    private Speaker speaker;
    private myDatabase db;
    private Usuario user;
    private Context context;

    @Override
    public void onFaceDetected(byte[] imgBytes) {
        Log.e("FACE", "FACEDETECTED");
        try {
            new CheckKnowPersonTask(user.getFaceSetToken(), imgBytes).execute().get();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    private class CheckKnowPersonTask extends AsyncTask<Void, Void, Void> {
        private byte[] imgBytes;
        private String fsToken;

        public CheckKnowPersonTask(String fsToken, byte[] imgBytes) {
            this.fsToken = fsToken;
            this.imgBytes = imgBytes;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<String> faceTokens = FaceSetUtils.reconFaces(fsToken, imgBytes);
            Log.e("USERFSTOKEN", fsToken);
            if(faceTokens.size() > 0) {
                for(String faceToken : faceTokens) {
                    Log.e("MATCH", faceToken);
                    Contato ct = db.dao().getContatoByFaceToken(faceToken);
                    if(ct != null) {
                        if(user.isDicaAtiv()) {
                            speaker.speak("Você encontrou um conhecido que é seu " + ct.getContato_relacionamento());
                            speaker.silence(3000);
                            speaker.speak("O nome dele é " + ct.getContato_nome());
                        }
                        else {
                            speaker.speak("Você encontrou um conhecido!");
                            speaker.speak("A pessoa encontrada se chama " + ct.getContato_nome());
                        }
                    }
                }
            }
            return null;
        }
    }

    public void onCreate() {
        context = getApplicationContext();
        speaker = new Speaker(context, 1.0f);
        db = myDatabase.getsInstance(context);
        user = db.dao().getUsuario();
        Log.e("ASSISTANT", "CREATED ASSISTANT SERVICE");
    }

    public void onDestroy() {
        camera.stop();
        camera.release();
        Log.e(TAG, "Assitant DESTROYED");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onStart(Intent intent, int startId) {
        Log.e("STARTING SERVICE", "");
        faceDetector = new FaceDetector.Builder(context).setClassificationType(FaceDetector.ALL_CLASSIFICATIONS).build();
        faceDetector.setProcessor(new LargestFaceFocusingProcessor(faceDetector, new FaceTracker(this)));

        if(!faceDetector.isOperational()) {
            Log.e(TAG, "FaceDetector isn't operational");
        }

        camera = new CameraSource.Builder(context, faceDetector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(0.5f)
                .build();


        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if(code != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Google API error");
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
                    callback.onFaceDetected(bytes);
                }
            });
        }
    }
}