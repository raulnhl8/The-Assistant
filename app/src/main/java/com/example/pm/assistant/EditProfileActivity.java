package com.example.pm.assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private String id;
    private String name;
    private String gender;
    private String birth;
    private String address;
    private boolean tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
            Toast toast = Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }
}
