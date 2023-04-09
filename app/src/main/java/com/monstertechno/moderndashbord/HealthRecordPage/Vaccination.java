package com.monstertechno.moderndashbord.HealthRecordPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.monstertechno.moderndashbord.MainActivity;
import com.monstertechno.moderndashbord.R;

public class Vaccination extends AppCompatActivity {
    EditText record_name, vc_date, vc_name, vc_dose, vc_center;
    Button addVaccine;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        rootNode=FirebaseDatabase.getInstance();//will call root node
        reference=rootNode.getReference("users");

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        record_name = findViewById(R.id.record_name);
        vc_date = findViewById(R.id.vc_date);
        vc_name = findViewById(R.id.vc_name);
        vc_dose = findViewById(R.id.vc_dose);
        vc_center = findViewById(R.id.vc_center);
        addVaccine=findViewById(R.id.addVaccine);

        addVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=record_name.getText().toString();
                String date=vc_date.getText().toString();
                String vaccinename=vc_name.getText().toString();
                String vaccinedose=vc_dose.getText().toString();
                String vaccinecenter=vc_center.getText().toString();

                VaccinationHelperClass vaccinationHelperClass = new VaccinationHelperClass(name,vaccinecenter, date,vaccinedose, vaccinename );
                reference.child(mUser.getUid()).child("vaccination").child(vaccinename).setValue(vaccinationHelperClass);
                //Toast.makeText(Vaccination.this, ""+String.valueOf(mUser.getUid()), Toast.LENGTH_SHORT).show();
                Toast.makeText(Vaccination.this, "Vaccine Added!", Toast.LENGTH_SHORT).show();
                record_name.setText("");
                vc_date.setText("");
                vc_name.setText("");
                vc_dose.setText("");
                vc_center.setText("");
                finish();

            }
        });

    }
    private void validateVaccine(){
        if(record_name.getText().toString()==null || vc_name.getText().toString().isEmpty())
        {
            record_name.setError("Record name is required");
            return;
        }

    }


}