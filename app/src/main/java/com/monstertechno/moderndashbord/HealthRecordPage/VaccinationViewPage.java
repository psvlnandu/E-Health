package com.monstertechno.moderndashbord.HealthRecordPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monstertechno.moderndashbord.R;

import java.util.ArrayList;

public class VaccinationViewPage extends AppCompatActivity {
    FloatingActionButton add_vaccine_btn;
    RecyclerView recyclerView;
    myVaccineAdapter myVaccineAdapter;
    ArrayList<VaccinationHelperClass> list;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_view_page);
        add_vaccine_btn=findViewById(R.id.Add_vaccine_floating);
        recyclerView=findViewById(R.id.recyclerView);

        add_vaccine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Vaccination.class);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myVaccineAdapter=new myVaccineAdapter(this,list);
        recyclerView.setAdapter(myVaccineAdapter);
        databaseReference=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("vaccination");

        databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            VaccinationHelperClass vaccine=dataSnapshot.getValue(VaccinationHelperClass.class);
                            list.add(vaccine);
                        }
                        myVaccineAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }
}