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

    EditText editName;
    Spinner editGender;
    EditText editDate;
    EditText editAddress;
    CheckBox editTip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        db = myDatabase.getsInstance(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        // Pegar as informações do perfil no bd e preencher nos campos
        Button btn = (Button) findViewById(R.id.editButton);

        editName = (EditText) findViewById(R.id.nameEditText);
        editGender = (Spinner) findViewById(R.id.genderSpinner);
        editDate = (EditText) findViewById(R.id.dateEditText);
        editAddress = (EditText) findViewById(R.id.addressEditText);
        editTip = (CheckBox) findViewById(R.id.checkBox);

        new FillEditProfileFields(db, editName, editGender, editDate, editAddress, editTip).execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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



class FillEditProfileFields extends AsyncTask<Void, Void, Usuario> {

    private myDatabase db;
    private EditText name;
    private Spinner gender;
    private EditText birth;
    private EditText address;
    private CheckBox tip;

    public FillEditProfileFields(myDatabase myDb, EditText eName, Spinner eGender, EditText eBirth ,EditText eAddress, CheckBox eTip) {
        this.db = myDb;
        this.name = eName;
        this.gender = eGender;
        this.birth = eBirth;
        this.address = eAddress;
        this.tip = eTip;
    }

    @Override
    protected Usuario doInBackground(Void... voids) {
        return db.dao().getUsuario();
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        super.onPostExecute(usuario);

        name.setText(usuario.getUsuario_nome());
        birth.setText(usuario.getUsuario_dataDeNascimento());
        address.setText(usuario.getUsuario_endereco());
//        tip.setActivated(usuario.isDicaAtiv());
        //TODO colocar valores de tip e gender

    }

}