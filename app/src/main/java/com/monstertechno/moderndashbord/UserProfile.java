package com.monstertechno.moderndashbord;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.monstertechno.moderndashbord.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    TextInputLayout fullName,email,phoneNo,password;
    TextView fullNameLabel,usernameLabel;
    CircleImageView profile_image;


    //globa, variables to hold data inside this actiity
    String _PASSWORD,_NAME,_EMAIL,_USERNAME,_PHONENO;
    DatabaseReference reference;

    //for profile
    ActivityMainBinding binding;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ProgressDialog progressDialog;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        reference = FirebaseDatabase.getInstance().getReference("users");
        profile_image=findViewById(R.id.profile_image);

        progressDialog = new ProgressDialog(this);


        //hooks
        fullName=findViewById(R.id.full_name_profile);
        email=findViewById(R.id.Email_profile);
        phoneNo=findViewById(R.id.phone_number_profile);
        password=findViewById(R.id.password_profile);
        fullNameLabel=findViewById(R.id.fullname_field);
        usernameLabel=findViewById(R.id.username_field);

        //showAll Data
        showAllUserData();

        //img
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pickImage();
            }
        });


    }
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();


            // Upload the image to Firebase Storage
            uploadImage(imageUri);

            // Display the image in the ImageView using Glide
            Glide.with(this).load(imageUri).into(profile_image);
        }
    }

    private void uploadImage(Uri imageUri) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Set your profile");
        progressDialog.setMessage("Please wait, while we are uploading profile");
        progressDialog.show();

        // Create a reference to the Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Create a reference to the image file in the Firebase Storage
        StorageReference imageRef = storageRef.child("image");

        // Upload the image to the Firebase Storage
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Register a listener to monitor the upload progress
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Image uploaded successfully
            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }).addOnFailureListener(e -> {
            // Image upload failed
            Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void imageSelector(View view) {
        launcher.launch("image/*");


    }


    public void showAllUserData() {
        Intent intent=getIntent();
        String user_username=intent.getStringExtra("username");
        Toast.makeText(this, "in userprofile--"+user_username, Toast.LENGTH_SHORT).show();
        String user_name=intent.getStringExtra("name");
        String user_email=intent.getStringExtra("email");
        String user_phoneNo=intent.getStringExtra("phoneNo");
        String user_password=intent.getStringExtra("password");
        String image_url=intent.getStringExtra("image");

        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);
        Glide.with(getApplicationContext()).load(image_url).into(profile_image);

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child("image");
//
//        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                String imageUrl = uri.toString();
//                // Display the image in the ImageView using Glide
//                Glide.with(getApplicationContext()).load(imageUrl).into(profile_image);
//                // Do something with the URL, like set it to an ImageView
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(UserProfile.this, "Pls add profile image !", Toast.LENGTH_SHORT).show();
//                // Handle any errors that occurred while retrieving the URL
//            }
//        });



        _NAME=user_name;
        _USERNAME=user_username;
        _PASSWORD=user_password;


    }

    public void update(View view){
        if(isNameChanged()|| isPasswordChanged())
            Toast.makeText(this, "Data has been updated!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data is same, can't be updated", Toast.LENGTH_SHORT).show();
    }
    private boolean isPasswordChanged(){

        if(!_PASSWORD.equals(password.getEditText().getText().toString()))

        {
            FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("user")
                    .child("password").setValue(password.getEditText().getText().toString());

            _PASSWORD=password.getEditText().getText().toString();

            return true;

        }else{

            return false;


        }

    }

    private boolean isNameChanged(){
        if(!_NAME.equals(fullName.getEditText().getText().toString())){

            FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("user")
                    .child("name").setValue(fullName.getEditText().getText().toString());

            _NAME=fullName.getEditText().getText().toString();

            return true;

        }else{

            return false;

        }

    }
}

