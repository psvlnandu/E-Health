package com.monstertechno.moderndashbord;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.monstertechno.moderndashbord.HealthRecordPage.Vaccination;
import com.monstertechno.moderndashbord.databinding.ActivityMainBinding;


import java.net.Inet4Address;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    Fragment healthrecords=new HealthRecords();
    Fragment doctors=new Doctors();
    Fragment welcome=new Welcome();

    TextView navUserName;
    TextView navUserMail;
    ImageView navUserImg;
    String imageUrl;

    NavigationView navigationView;


    ActivityResultLauncher<String> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.teal_700));
        }

        BottomNavigationView bottomNavigationView=findViewById(R.id.battonnavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Home);

        //navigation
        MaterialToolbar toolbar=findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigation_view);
        CircleImageView profile_image_in_nv=findViewById(R.id.profile_image_in_nv);
        CircleImageView nav_header_img_profile=findViewById(R.id.nav_header_profile_image);



        //setting profile image
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("image");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageUrl= uri.toString();
                // Display the image in the ImageView using Glide to navigation and also toolbar
                Glide.with(getApplicationContext()).load(imageUrl).into(profile_image_in_nv);
                // Do something with the URL, like set it to an ImageView
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Pls add profile image !", Toast.LENGTH_SHORT).show();
                // Handle any errors that occurred while retrieving the URL
            }
        });


        //ToolBar
        setSupportActionBar(toolbar);

        //navigation Drawer Menu
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigatio_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                UserMenuSelector(item);
                return false;
            }
        });

        getUsersData();

    }

    public String user_username;
    public String user_email;
    public String user_phoneNo;
    public String user_password;
    public String user_name;

    public void getUsersData(){
        View headerView=navigationView.getHeaderView(0);
        //navigation hooks:
        navUserName=headerView.findViewById(R.id.user_name_navHeader);
        navUserMail=headerView.findViewById(R.id.nav_user_mail);

        Intent intent=getIntent();
         user_username=intent.getStringExtra("username");
         user_name=intent.getStringExtra("name");
         user_email=intent.getStringExtra("email");
         user_phoneNo=intent.getStringExtra("phoneNo");
         user_password=intent.getStringExtra("password");


        navUserName.setText(user_username);
        navUserMail.setText(user_email);
      Toast.makeText(this, ""+user_username+":"+user_email, Toast.LENGTH_SHORT).show();
    }

    public void UserMenuSelector(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.nav_settings:
                Intent intent=new Intent(getApplicationContext(),UserProfile.class);
                intent.putExtra("username", user_username);
                intent.putExtra("email", user_email);
                intent.putExtra("phoneNo", user_phoneNo);
                intent.putExtra("password", user_password);
                intent.putExtra("name",user_name);
                intent.putExtra("image",imageUrl);

                startActivity(intent);
                //Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_LONG).show();
                break;


        }
    }


            @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case (R.id.Home):
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,welcome).commit();
                return true;
            case (R.id.doctors):

               getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,doctors).commit();
                return true;
            case (R.id.HealthRecords):
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,healthrecords).commit();
                return  true;


        }
        return false;
    }

}