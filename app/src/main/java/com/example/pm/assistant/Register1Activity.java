package com.example.pm.assistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register1Activity extends AppCompatActivity {

    private String name;
    private String cellphone;
    private String relationship;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Button btn = (Button) findViewById(R.id.nextButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.nameEditText);
                EditText editCell = (EditText) findViewById(R.id.cellphoneEditText);
                EditText editRelationship = (EditText) findViewById(R.id.relationshipEditText);
                EditText editAddress = (EditText) findViewById(R.id.addressEditText);

                name = editName.getText().toString();
                cellphone = editCell.getText().toString();
                relationship = editRelationship.getText().toString();
                address = editAddress.getText().toString();

                next(v);
            }
        });
    }

    public void next(View v){
        if(name.equals("") || cellphone.equals("") || relationship.equals("") || address.equals("")){
            Toast toast = Toast.makeText(this, "Preencham todos so campos", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(this, "name "+ name + "cellphone " + cellphone + "relationship " + relationship + "address " + address , Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, Register2Activity.class);
            intent.putExtra("nameCare", name);
            intent.putExtra("cellphoneCare", cellphone);
            intent.putExtra("relationshipCare", relationship);
            intent.putExtra("addressCare", address);
            startActivity(intent);
        }
    }

    public void addPhoto(View v){
        Toast toast = Toast.makeText(this, "Carregar foto", Toast.LENGTH_LONG);
        toast.show();
    }
}
