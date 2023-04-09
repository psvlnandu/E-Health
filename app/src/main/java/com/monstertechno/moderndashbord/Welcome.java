package com.monstertechno.moderndashbord;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.monstertechno.moderndashbord.ChatBots.Chatbot;
import com.monstertechno.moderndashbord.HealthRecordPage.VaccinationViewPage;
import com.monstertechno.moderndashbord.chatgpt.Chatgpt;

public class Welcome extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
 public static Welcome newInstance(String param1, String param2) {
        Welcome fragment = new Welcome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Welcome() {
        // Required empty public constructor
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


        View view=inflater.inflate(R.layout.fragment_welcome,container,false);
        Button get_started_btn=view.findViewById(R.id.getStartedBtn);
        Context context = view.getContext();
        Intent intent=new Intent(getContext(), Chatbot.class);

        // show a toast message
        get_started_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //book appointment
               // gotoURL("https://app.wotnot.io/bot-preview/oL2qHK5HtKug164118518990iJsiricf");
                startActivity(intent);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void gotoURL(String s) {
     Uri uri= Uri.parse(s);
     startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}