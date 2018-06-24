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
import java.util.List;

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

    @Override
    public void onFaceDetected(byte[] imgBytes) {
        List<String> faceTokens = FaceSetUtils.reconFaces(user.getFaceSetToken(), imgBytes);
        if(faceTokens.size() > 0) {
            for(String faceToken : faceTokens) {
                if(db != null) {
                    Contato ct = db.dao().getContatoByFaceToken(faceToken);
                    if(ct != null) {
                        if(user.isDicaAtiv()) {
                            speaker.speak("Você encontrou uma pessoa conhecida chamada " + ct.getContato_nome());
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            speaker.speak("Ela é seu " + ct.getContato_relacionamento());
                        }
                        else {
                            speaker.speak("Você encontrou uma pessoa conhecida!");
                            speaker.speak("O nome dessa pessoa é " + ct.getContato_nome() + " e ela é seu " + ct.getContato_relacionamento());
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public AssistantMain(Usuario user) {
        this.user = user;
        speaker = new Speaker(getApplicationContext(), 1.0f);
        db = myDatabase.getsInstance(getApplicationContext());
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
                    callback.onFaceDetected(bytes);
                }
            });
        }
    }
}