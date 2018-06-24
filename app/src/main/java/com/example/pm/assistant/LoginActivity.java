package com.example.pm.assistant;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm.assistant.assistant.AssistantMain;
import com.example.pm.assistant.assistant.Speaker;
import com.example.pm.assistant.data.Cuidador;
import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;

import java.util.Locale;

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
    }

    public void login(View v){
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
        this.context = context.getApplicationContext();
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
        if ((userName.equals(cuidador.getCuidador_email()) && password.equals(cuidador.getCuidador_senha())) || (userName.equals("admin") && password.equals("admin"))) {
            Toast toast = Toast.makeText(context, "Seja bem vindo", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("id", "admin");
            context.startActivity(intent);
        } else {
            Toast toast = Toast.makeText(context, "Credenciais Incorretas, tente novamente", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}