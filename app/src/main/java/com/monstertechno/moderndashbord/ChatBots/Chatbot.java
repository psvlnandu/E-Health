package com.monstertechno.moderndashbord.ChatBots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.monstertechno.moderndashbord.R;
import com.monstertechno.moderndashbord.chatgpt.Chatgpt;

public class Chatbot extends AppCompatActivity {
    CardView bookAppointmentCV,chatGptCV,AIDoctor,Artciles,previousBookings,CurrentBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        bookAppointmentCV=findViewById(R.id.book_appointment_cv);;
        chatGptCV=findViewById(R.id.chatgpt_cv);
        Artciles=findViewById(R.id.articles_cv);
        previousBookings=findViewById(R.id.Previous_bookings_cv);
        CurrentBookings=findViewById(R.id.current_bookings_cv);
        AIDoctor=findViewById(R.id.consultAI_cv);

        bookAppointmentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoBookAppointment("https://app.wotnot.io/bot-preview/oL2qHK5HtKug164118518990iJsiricf");
            }
        });
        chatGptCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Chatgpt.class);
                startActivity(intent);
            }
        });
        AIDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAIDoctor("https://app.wotnot.io/bot-preview/text-mode/3ugHRMVomaMa171136734749V6B9XsmP");
            }
        });



    }
    public void gotoBookAppointment(String s) {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    public void gotoAIDoctor(String s) {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}