package com.example.pm.assistant;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm.assistant.data.Contato;
import com.example.pm.assistant.data.Cuidador;
import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;

import java.util.List;

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
    private myDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = Room.databaseBuilder(this, myDatabase.class, "Database").build();
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

    public void showToast(String msg)
    {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void finalizeRegister(View v){
        if(email.equals("") || password.equals("") || password2.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            if(password.equals(password2)){
                // Inserir no banco de Dados e checar se o login ja existe
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // checar se ja existe um cuidador/usuario cadastrado
                        Cuidador alreadyExistingCuidador = db.dao().getCuidador();
                        if (alreadyExistingCuidador == null) {
                            //pode criar um novo cuidador!
                            // 1. criar contato
                            // 2. criar cuidador e atrelar a contato
                            // 3. criar usuario
                            Contato contato = new Contato(nameCare, relationshipCare, "caminhodafoto.png");
                            db.dao().addContato(contato);
                            int idContato;
                            List<Contato> allContatos = db.dao().getAllContatos();
                            idContato = allContatos.size() - 1;
                            Cuidador cuidador = new Cuidador(email, password, cellphoneCare, addressCare, idContato);
                            db.dao().addCuidador(cuidador);
                            boolean genderBool = gender.equals("masculino");
                            Usuario usuario = new Usuario(name, genderBool, birth, true, "" );
                            db.dao().addUsuario(usuario);
                            showToast("Cadastro efetuado com sucesso");
                        } else {
                            //JA EXISTE UM CUIDADOR CADASTRADO
                            showToast("Ja existe um cuidador cadastrado!");
                        }
                    }
                }).start();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(this, "Os password diferem", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
