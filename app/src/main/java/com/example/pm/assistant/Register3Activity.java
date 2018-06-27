package com.example.pm.assistant;

import android.content.Context;
import android.content.Intent;
import android.media.FaceDetector;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm.assistant.assistant.AssistantMain;
import com.example.pm.assistant.data.Contato;
import com.example.pm.assistant.data.Cuidador;
import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;
import com.example.pm.assistant.faceppcom.FaceSetUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Register3Activity extends AppCompatActivity {
    private String nameCare;
    private String cellphoneCare;
    private String relationshipCare;
    private String addressCare;
    private byte[] photoCare;
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
        db = myDatabase.getsInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        Intent intent =  getIntent();
        nameCare = intent.getStringExtra("nameCare");
        cellphoneCare = intent.getStringExtra("cellphoneCare");
        relationshipCare = intent.getStringExtra("relationshipCare");
        addressCare = intent.getStringExtra("addressCare");
        photoCare = intent.getByteArrayExtra("photoCare");
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

    public void finalizeRegister(View v) {
        if(email.equals("") || password.equals("") || password2.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            if(password.equals(password2)){
                // Inserir no banco de Dados e checar se o login ja existe
                try {
                    boolean b = new RegisterUser(this, db, nameCare, cellphoneCare, relationshipCare, addressCare, photoCare, name, gender, birth, address, email, password).execute().get();

                    if (b) {
                        Toast toast = Toast.makeText(this, "Cadastro efetuado com sucesso", Toast.LENGTH_LONG);
                        toast.show();
                        Log.i("CADASTRO", "Cadastro efetuado com sucesso");

                        Intent i = new Intent(this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        Toast toast = Toast.makeText(this, "Ja existe um usuario cadastrado!", Toast.LENGTH_LONG);
                        toast.show();
                        Log.i("CADASTRO", "Cadastro nao foi efetuado com sucesso");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                Toast toast = Toast.makeText(this, "Os password diferem", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}


class RegisterUser extends AsyncTask<Void, Void, Boolean> {
    private myDatabase db;
    private String nameCare;
    private String cellphoneCare;
    private String relationshipCare;
    private String addressCare;
    private byte[] photoCare;
    private String name;
    private String gender;
    private String birth;
    private String address;
    private String email;
    private String password;
    private Context context;

    public RegisterUser(
            Context context,
            myDatabase db,
            String nameCare,
            String cellphoneCare,
            String relationshipCare,
            String addressCare,
            byte[] photoCare,
            String name,
            String gender,
            String birth,
            String address,
            String email,
            String password
    ) {
        this.context = context.getApplicationContext();
        this.db = db;
        this.nameCare = nameCare;
        this.cellphoneCare = cellphoneCare;
        this.relationshipCare = relationshipCare;
        this.addressCare = addressCare;
        this.photoCare = photoCare;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String fsToken = null, faceToken = null;

        if (db.dao().getCuidador() == null && db.dao().getUsuario() == null) {
            try {
                fsToken = FaceSetUtils.create(null);
                Log.e("DEbug", fsToken);
                if(fsToken != null) {
                    faceToken = FaceSetUtils.detectFaces(photoCare).get(0);
                    if(!FaceSetUtils.addFace(fsToken, faceToken)) {
                        Log.e("FacePP", "Face not ADDED");
                        return false;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }

            Contato contato = new Contato(nameCare, relationshipCare, "caminhodafoto.png", faceToken);
            db.dao().addContato(contato);
            int idContato;
            List<Contato> allContatos = db.dao().getAllContatos();
            idContato = allContatos.size() - 1;
            Cuidador cuidador = new Cuidador(email, password, cellphoneCare, addressCare, idContato);
            db.dao().addCuidador(cuidador);
            boolean genderBool = gender.equals("Masculino");

            Usuario usuario = new Usuario(name, genderBool, birth, true, address, fsToken);
            db.dao().addUsuario(usuario);

        }

        return true;
    }
}
