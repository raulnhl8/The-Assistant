package com.example.pm.assistant;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm.assistant.data.Cuidador;
import com.example.pm.assistant.data.myDatabase;

public class LoginActivity extends AppCompatActivity {
    private String email;
    private String password;
    private myDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = myDatabase.getsInstance(getApplicationContext());

        Button btn = (Button) findViewById(R.id.loginButton);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editEmail = (EditText) findViewById(R.id.emailEditText);
                EditText editPassword = (EditText) findViewById(R.id.passwordEditText);

                email = editEmail.getText().toString();
                password = editPassword.getText().toString();

                login(v);
            }
        });

        checkPermissions();
    }

    public void checkPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 101);
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 101);
    }

    public void login(View v) {
        if(email.equals("") || password.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            // Checar no banco de dados
            new CheckLoginCredentials(db, this, email, password).execute();
        }
    }

    public void goToRegister(View v){
        Toast toast = Toast.makeText(this, "Indo para o cadastro", Toast.LENGTH_LONG);
        toast.show();
        Intent intent =  new Intent(this, Register1Activity.class);
        startActivity(intent);
    }
}


class CheckLoginCredentials extends AsyncTask<Void, Void, Cuidador> {

    private myDatabase db;
    private String userName;
    private String password;
    private Context context;

    public CheckLoginCredentials(myDatabase myDb, Context context, String userName, String password) {
        this.db = myDb;
        this.context = context;
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected Cuidador doInBackground(Void... voids) {
        return db.dao().getCuidador();
    }

    @Override
    protected void onPostExecute(Cuidador cuidador) {
        super.onPostExecute(cuidador);
        if (cuidador != null) {
            if (userName.equals(cuidador.getCuidador_email()) && password.equals(cuidador.getCuidador_senha())) {
                Toast toast = Toast.makeText(context, "Seja bem vindo", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", "admin");
                context.startActivity(intent);
            } else {
                Toast toast = Toast.makeText(context, "Credenciais Incorretas, tente novamente", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(context, "Credenciais Incorretas, tente novamente", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}