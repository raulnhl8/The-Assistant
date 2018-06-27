package com.example.pm.assistant;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

interface FDetectCallback {
    void onFaceDetected(byte[] imgBytes);
}

public class Register1Activity extends AppCompatActivity implements FDetectCallback {
    private CameraSource camera;
    private FaceDetector faceDetector;
    private String name;
    private String cellphone;
    private String relationship;
    private String address;
    private byte[] photo;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String TAG = "RegiterActivity1";

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Button btn = (Button) findViewById(R.id.nextButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.nameEditText);
                EditText editCell = (EditText) findViewById(R.id.cellphoneEditText);
                EditText editRelationship = (EditText) findViewById(R.id.relationshipEditText);
                EditText editAddress = (EditText) findViewById(R.id.addressEditText);

                name = editName.getText().toString();
                cellphone = editCell.getText().toString();
                relationship = editRelationship.getText().toString();
                address = editAddress.getText().toString();

                next(v);
            }
        });

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        faceDetector = new FaceDetector.Builder(getApplicationContext()).setClassificationType(FaceDetector.ALL_CLASSIFICATIONS).build();
        faceDetector.setProcessor(new LargestFaceFocusingProcessor(faceDetector, new FaceTracker(this)));

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

    public void next(View v) {
        if(name.equals("") || cellphone.equals("") || relationship.equals("") || address.equals("") || photo == null) {
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(this, "name "+ name + "cellphone " + cellphone + "relationship " + relationship + "address " + address , Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, Register2Activity.class);
            intent.putExtra("nameCare", name);
            intent.putExtra("cellphoneCare", cellphone);
            intent.putExtra("relationshipCare", relationship);
            intent.putExtra("addressCare", address);
            intent.putExtra("photoCare", photo);
            startActivity(intent);
        }
    }

    public void vibrateDevice(int timeMilis) {
        if(vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(timeMilis, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else {
                //deprecated in API 26
                vibrator.vibrate(timeMilis);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = data.getParcelableExtra("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            photo = stream.toByteArray();
        }
    }

    public void addPhoto(View v) {
        if(camera != null) {
            try {
                Toast.makeText(this, "Posicione sua camera na frente do rosto", Toast.LENGTH_LONG).show();
                camera.start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onFaceDetected(byte[] imgBytes) {
        vibrateDevice(600);
        photo = imgBytes;
        camera.stop();
        camera.release();
        Toast.makeText(this, "Rosto foi capturado com sucesso", Toast.LENGTH_LONG).show();
    }

    private class FaceTracker extends Tracker<Face> {
        private FDetectCallback callback;

        public FaceTracker(FDetectCallback callback) {
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
