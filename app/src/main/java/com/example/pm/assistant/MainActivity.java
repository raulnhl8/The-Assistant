package com.example.pm.assistant;

import android.Manifest;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private myDatabase db;

    private TextView mTextMessage;
    private String user;


    private String newContactName;
    private String newContactRelationship;

    private AssistantMain assistant;

    private boolean cameraStatus = true;

    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        db = Room.databaseBuilder(getApplicationContext(), myDatabase.class, "Database").fallbackToDestructiveMigration().build();
        db = myDatabase.getsInstance(getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.dao().getUsuario() == null) {
                    Log.i("NULO", "ta nulo na main");
                }
            }
        }).start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = intent.getStringExtra("id");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.onOrOffButton);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_contact);
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
        //COLOCAR AQUI AS COISAS DO EDIT PROFILE


        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id", user);
        startActivity(intent);
    }

    public void addContact(View v){
        EditText newName = (EditText) findViewById(R.id.nameAddEditText);
        EditText newRelationship = (EditText) findViewById(R.id.relationshipAddEditText);

        newContactName = newName.getText().toString();
        newContactRelationship = newRelationship.getText().toString();

        if(newContactName.equals("") || newContactRelationship.equals("")){
            Toast toast = Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            // adicionar no banco de dados
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Contato contato = new Contato(newContactName, newContactRelationship, "caminhodafoto.png");
                    db.dao().addContato(contato);
                }
            }).start();


            Toast toast = Toast.makeText(this, "Adicionado com sucesso", Toast.LENGTH_LONG);
            toast.show();

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(this);
            navigation.setSelectedItemId(R.id.navigation_contact);
        }
    }

    public void addPhoto(View v){
        Toast toast = Toast.makeText(this, "Foto Adicionada", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onOrOffCamera(View v){
        Context contextInstance = getApplicationContext();
        if(cameraStatus){
            Toast toast = Toast.makeText(this, "Camera OFF", Toast.LENGTH_LONG);
            toast.show();
            floatingActionButton.setBackgroundTintList(contextInstance.getResources().getColorStateList(R.color.red));
            cameraStatus = false;
        }else{
            Toast toast = Toast.makeText(this, "Camera ON", Toast.LENGTH_LONG);
            toast.show();
            floatingActionButton.setBackgroundTintList(contextInstance.getResources().getColorStateList(R.color.green));
            cameraStatus = true;
        }

    }


}

