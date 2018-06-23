package com.example.pm.assistant;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;

import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private String id;
    private String name;
    private String gender;
    private String birth;
    private String address;
    private boolean tip;
    private myDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        db = myDatabase.getsInstance(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        // Pegar as informações do perfil no bd e preencher nos campos
        Button btn = (Button) findViewById(R.id.editButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.nameEditText);
                Spinner editGender = (Spinner) findViewById(R.id.genderSpinner);
                EditText editDate = (EditText) findViewById(R.id.dateEditText);
                EditText editAddress = (EditText) findViewById(R.id.addressEditText);
                CheckBox editTip = (CheckBox) findViewById(R.id.checkBox);

                name = editName.getText().toString();
                gender = editGender.getSelectedItem().toString();
                birth = editDate.getText().toString();
                address = editAddress.getText().toString();
                tip = editTip.isChecked();

                editRegister(v);
            }
        });
    }

    public void editRegister(View v){
        if(name.equals("") || gender.equals("") || birth.equals("") || address.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Usuario user = new Usuario(0, name, gender.equals("Masculino"), birth, tip, address, "");
                    db.dao().updateUsuario(user);


                    Log.i("NAME", db.dao().getUsuario().getUsuario_nome());

                }
            }).start();
            Toast toast = Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }
}

//
//
//class UpdateEditProfile extends AsyncTask<Void, Void, Usuario> {
//
//    private myDatabase db;
//    private TextView name;
//    private TextView gender;
//    private TextView birth;
//    private TextView address;
//    private TextView tip;
//
//    public UpdateEditProfile(myDatabase myDb, TextView pName, TextView pGender, TextView pBirth ,TextView pAddress, TextView pTip) {
//        this.db = myDb;
//        this.name = pName;
//        this.gender = pGender;
//        this.birth = pBirth;
//        this.address = pAddress;
//        this.tip = pTip;
//    }
//
//    @Override
//    protected Usuario doInBackground(Void... voids) {
//
//        if (db == null) {
//            Log.i("USER", "db ta nulo no asynk");
//        }
//
//        return db.dao().getUsuario();
//    }
//
//    @Override
//    protected void onPostExecute(Usuario usuario) {
//        super.onPostExecute(usuario);
//
//        if(usuario == null) {
//            Log.i("USER", "nulo no post");
//        }
//
//        name.setText(usuario.getUsuario_nome());
//        if (usuario.isUsuario_sexo()) {
//            gender.setText("Masculino");
//        } else {
//            gender.setText("Feminino");
//        }
//        birth.setText(usuario.getUsuario_dataDeNascimento());
//
//        address.setText(usuario.getUsuario_endereco());
//
//        if (usuario.isDicaAtiv()) {
//            tip.setText("Ativado");
//        } else {
//            tip.setText("Desativado");
//        }
//
//    }
//
//}