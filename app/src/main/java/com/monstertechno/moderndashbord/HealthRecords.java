package com.monstertechno.moderndashbord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.monstertechno.moderndashbord.HealthRecordPage.Vaccination;
import com.monstertechno.moderndashbord.HealthRecordPage.VaccinationViewPage;
import com.monstertechno.moderndashbord.Hospitalization.Hospitalization;

public class HealthRecords extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HealthRecords() {

    }
    public static HealthRecords newInstance(String param1, String param2) {
        HealthRecords fragment = new HealthRecords();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_health_records,container,false);
        CardView vaccine_iv=view.findViewById(R.id.vaccine_iv);
        Context context = view.getContext();
        Intent intent=new Intent(getContext(), VaccinationViewPage.class);

        // show a toast message
         vaccine_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);

            }
        });

         CardView Hospital_iv=view.findViewById(R.id.Hospital_iv);
         Hospital_iv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getContext(), Hospitalization.class));
             }
         });

        return view;
    }


}