package com.monstertechno.moderndashbord;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Doctors#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Doctors extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Doctors() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Doctors.
     */
    // TODO: Rename and change types and number of parameters
    public static Doctors newInstance(String param1, String param2) {
        Doctors fragment = new Doctors();
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

//        MaterialToolbar toolbar=  findViewById(R.id.topAppBar);
//        DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
//        NavigationView navigationView=findViewById(R.id.navigation_view);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id=item.getItemId();
//
//                //to highlight item
//                item.setChecked(true);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                switch (id){
//                    case R.id.nav_home:
//
//                        break;
//                    case R.id.nav_login:
//
//                        break;
//                    case R.id.nav_messages:
//
//                        Toast.makeText(MainActivity.this, "Messages page", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_settings:
//
//                        break;
//                    case R.id.nav_share:
//
//                        Toast.makeText(MainActivity.this, "Share page", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        return true;
//                }
//                return true;
//            }
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }
}