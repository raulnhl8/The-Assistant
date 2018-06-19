package com.example.pm.assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register2Activity extends AppCompatActivity {

    private String nameCare;
    private String cellphoneCare;
    private String relationshipCare;
    private String addressCare;
    private String name;
    private String gender;
    private String birth;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Intent intent =  getIntent();
        nameCare = intent.getStringExtra("nameCare");
        cellphoneCare = intent.getStringExtra("cellphoneCare");
        relationshipCare = intent.getStringExtra("relationshipCare");
        addressCare = intent.getStringExtra("addressCare");

        Button btn = (Button) findViewById(R.id.nextButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.nameEditText);
                Spinner editGender = (Spinner) findViewById(R.id.genderSpinner);
                EditText editDate = (EditText) findViewById(R.id.dateEditText);
                EditText editAddress = (EditText) findViewById(R.id.addressEditText);

                name = editName.getText().toString();
                gender = editGender.getSelectedItem().toString();
                birth = editDate.getText().toString();
                address = editAddress.getText().toString();

                next(v);
            }
        });
    }

    public void next(View v){
        if(name.equals("") || gender.equals("") || birth.equals("") || address.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(this, "name "+ name + "gender " + gender + "birth " + birth + "address " + address , Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, Register3Activity.class);
            intent.putExtra("nameCare", nameCare);
            intent.putExtra("cellphoneCare", cellphoneCare);
            intent.putExtra("relationshipCare", relationshipCare);
            intent.putExtra("addressCare", addressCare);
            intent.putExtra("name", name);
            intent.putExtra("gender", gender);
            intent.putExtra("birth", birth);
            intent.putExtra("addressCare", address);
            startActivity(intent);
        }
    }
}
