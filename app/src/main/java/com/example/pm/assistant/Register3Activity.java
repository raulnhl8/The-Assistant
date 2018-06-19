package com.example.pm.assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register3Activity extends AppCompatActivity {

    private String nameCare;
    private String cellphoneCare;
    private String relationshipCare;
    private String addressCare;
    private String name;
    private String gender;
    private String birth;
    private String address;
    private String email;
    private String password;
    private String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        Intent intent =  getIntent();
        nameCare = intent.getStringExtra("nameCare");
        cellphoneCare = intent.getStringExtra("cellphoneCare");
        relationshipCare = intent.getStringExtra("relationshipCare");
        addressCare = intent.getStringExtra("addressCare");
        name = intent.getStringExtra("name");
        gender = intent.getStringExtra("gender");
        birth = intent.getStringExtra("birth");
        address = intent.getStringExtra("address");

        Button btn = (Button) findViewById(R.id.registerButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editEmail = (EditText) findViewById(R.id.emailEditText);
                EditText editPassword = (EditText) findViewById(R.id.passwordEditText);
                EditText editPassword2 = (EditText) findViewById(R.id.password2EditText);

                email = editEmail.getText().toString();
                password = editPassword.getText().toString();
                password2 = editPassword2.getText().toString();


                finalizeRegister(v);
            }
        });
    }

    public void finalizeRegister(View v){
        if(email.equals("") || password.equals("") || password2.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            if(password.equals(password2)){
                Toast toast = Toast.makeText(this, "Cadastro Efetuado com sucesso", Toast.LENGTH_LONG);
                // Inserir no banco de Dados e checar se o login ja existe
                toast.show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(this, "Os password diferem", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
