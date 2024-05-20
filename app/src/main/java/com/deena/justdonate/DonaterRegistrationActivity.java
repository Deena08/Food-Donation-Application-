package com.deena.justdonate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonaterRegistrationActivity extends AppCompatActivity {
   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://justdonate-71d7b-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_donater_registration);
        final EditText DonatorFullname=findViewById(R.id.Donatorfullname);
        final EditText DonatorPhoneNumber=findViewById(R.id.DonatorPhoneNumber);
        final EditText DonateEmail=findViewById(R.id.DonatorEmail);
        final EditText DonatorPassword=findViewById(R.id.DonatorPassword);
        final TextView Loginnow=findViewById(R.id.LoginNow);
        final Button DSignupButton=findViewById(R.id.DSignupButton);
        DSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = DonatorFullname.getText().toString();
                final String email = DonateEmail.getText().toString();
                final String phoneNumber = DonatorPhoneNumber.getText().toString();
                final String Password = DonatorPassword.getText().toString();
                if(fullname.isEmpty()||email.isEmpty()||phoneNumber.isEmpty()||Password.isEmpty()){
                    Toast.makeText(DonaterRegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
              //  else if(!Password.equals(Password)){
                //    Toast.makeText(DonaterRegistrationActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
              //  }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneNumber)){
                                Toast.makeText(DonaterRegistrationActivity.this, "Phone number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("users").child(phoneNumber).child("Donatorfullname").setValue(fullname);
                                databaseReference.child("users").child(phoneNumber).child("password").setValue(Password);
                                databaseReference.child("users").child(phoneNumber).child("email").setValue(email);
                                Toast.makeText(DonaterRegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                Intent intent=new Intent(DonaterRegistrationActivity.this,Login.class);
                startActivity(intent);

            }
        });
        Loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonaterRegistrationActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
}