package com.monstertechno.moderndashbord.HealthRecordPage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.monstertechno.moderndashbord.R;

import java.util.ArrayList;

import kotlin.jvm.internal.Lambda;

public class myVaccineAdapter extends RecyclerView.Adapter<myVaccineAdapter.MyViewHolder> {

    Context context;
    ArrayList<VaccinationHelperClass> list;

    public myVaccineAdapter(Context context, ArrayList<VaccinationHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.vaccination_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VaccinationHelperClass vaccine= list.get(position);
        holder.vaccine_name.setText(vaccine.getVaccineName());
        holder.vaccine_dose.setText(vaccine.getVaccineDose());
        holder.vaccine_center.setText(vaccine.getVaccineCenter());
       // holder.vaccine_date.setText(vaccine.getVaccineDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView vaccine_dose,vaccine_name,vaccine_center,vaccine_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vaccine_center=itemView.findViewById(R.id.vaccineCenter);
            vaccine_dose=itemView.findViewById(R.id.vaccineDose);
            vaccine_name=itemView.findViewById(R.id.vaccineName);
           // vaccine_date=itemView.findViewById(R.id.vaccineDate);
        }
    }

}