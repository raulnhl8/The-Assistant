package com.example.pm.assistant;

import android.Manifest;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm.assistant.assistant.AssistantMain;
import com.example.pm.assistant.data.Contato;
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

interface FcDetectCallback {
    void onFaceDetected(byte[] imgBytes);
}

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FcDetectCallback {
    private CameraSource camera;
    private FaceDetector faceDetector;

    private myDatabase db;

    private TextView mTextMessage;
    private String user;


    private String newContactName;
    private String newContactRelationship;

    private boolean cameraStatus = true;

    private FloatingActionButton floatingActionButton;

    private Intent assistantIntent;

    private static final int PERMISSION_REQUEST_CODE = 101;

    private byte[] contactPhoto;

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = myDatabase.getsInstance(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = intent.getStringExtra("id");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.onOrOffButton);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_contact);

        if(cameraStatus) {
            if(!hasRequiredPermissions())
                requestRequiredPermissions();
            else
                startAssistantService();
        }

        faceDetector = new FaceDetector.Builder(getApplicationContext()).setClassificationType(FaceDetector.ALL_CLASSIFICATIONS).build();
        faceDetector.setProcessor(new LargestFaceFocusingProcessor(faceDetector, new MainActivity.FaceTracker(this)));

        if(!faceDetector.isOperational()) {
            Log.e(TAG, "FaceDetector isn't operational");
        }

        camera = new CameraSource.Builder(getApplicationContext(), faceDetector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(0.5f)
                .build();


        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        if(code != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Google API error");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startAssistantService();
                }
                break;

            default:
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_add:
                fragment = new AddContactFragment();
                break;
            case R.id.navigation_contact:
                fragment = new ContactsFragment();
                break;
            case R.id.navigation_profile:
                fragment = new UserProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }

    public void goToEditProfile(View v){
        Toast toast = Toast.makeText(this, "Edit", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id", user);
        startActivity(intent);
    }

    public void addContact(View v){
            new NewContactTask().execute();

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(this);
            navigation.setSelectedItemId(R.id.navigation_contact);
        }

    public class NewContactTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            EditText newName = (EditText) findViewById(R.id.nameAddEditText);
            EditText newRelationship = (EditText) findViewById(R.id.relationshipAddEditText);

            newContactName = newName.getText().toString();
            newContactRelationship = newRelationship.getText().toString();

            if (newContactName.equals("") || newContactRelationship.equals("") || contactPhoto == null) {
                return false;
            } else {
                // adicionar no banco de dados
                List<String> faceToken = FaceSetUtils.detectFaces(contactPhoto);
                if (faceToken.size() > 0)
                    if (FaceSetUtils.addFace(db.dao().getUsuario().getFaceSetToken(), faceToken.get(0))) {
                        Contato contato = new Contato(newContactName, newContactRelationship, "caminhodafoto.png", "");
                        db.dao().addContato(contato);
                        contactPhoto = null;
                        return true;
                    }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if(b) {
                Toast toast = Toast.makeText(getApplicationContext(), "Adicionado com sucesso", Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Falha ao adicionar contato", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }



    public void addPhoto(View v) {
        if(camera != null) {
            try {
                Toast.makeText(this, "Posicione sua camera na frente do rosto", Toast.LENGTH_LONG);
                camera.start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void onOrOffCamera(View v){
        Context contextInstance = getApplicationContext();
        if(cameraStatus){
            stopAssistantService();
            Toast toast = Toast.makeText(this, "Camera OFF", Toast.LENGTH_LONG);
            toast.show();
            floatingActionButton.setBackgroundTintList(contextInstance.getResources().getColorStateList(R.color.red));
            cameraStatus = false;
        }else{
            if(!hasRequiredPermissions()) requestRequiredPermissions();
            startAssistantService();
            Toast toast = Toast.makeText(this, "Camera ON", Toast.LENGTH_LONG);
            toast.show();
            floatingActionButton.setBackgroundTintList(contextInstance.getResources().getColorStateList(R.color.green));
            cameraStatus = true;
        }

    }

    public void startAssistantService() {
        if(assistantIntent == null) {
            assistantIntent = new Intent(this, AssistantMain.class);
            startService(assistantIntent);
        }
    }

    public void stopAssistantService() {
        if(assistantIntent != null)
            stopService(assistantIntent);
    }

    public boolean hasRequiredPermissions() {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    public void requestRequiredPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 101);
    }

    @Override
    public void onFaceDetected(byte[] imgBytes) {
        camera.stop();
        contactPhoto = imgBytes;
    }

    private class FaceTracker extends Tracker<Face> {
        private FcDetectCallback callback;

        public FaceTracker(FcDetectCallback callback) {
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

