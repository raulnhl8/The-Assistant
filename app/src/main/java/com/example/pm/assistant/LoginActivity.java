package com.example.pm.assistant;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm.assistant.assistant.Speaker;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
            if(email.equals("admin") && password.equals("admin")){
                Toast toast = Toast.makeText(this, "Seja bem vindo", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", "admin");
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(this, "Credenciais Incorretas, tente novamente", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

    public void goToRegister(View v){
        Toast toast = Toast.makeText(this, "Indo para o cadastro", Toast.LENGTH_LONG);
        toast.show();
        Intent intent =  new Intent(this, Register1Activity.class);
        startActivity(intent);
    }
}
