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

public class recipient_registration extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://justdonate-71d7b-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipient_registration);
        final EditText recipientfullname=findViewById(R.id.recipientfullname);
        final EditText recipientLocation=findViewById(R.id.recipientLocation);
        final EditText recipientPhoneNumber=findViewById(R.id.recipientPhoneNumber);
        final EditText recipientEmail=findViewById(R.id.recipientEmail);
        final EditText recipientPassword=findViewById(R.id.recipientPassword);
        final TextView Loginnow=findViewById(R.id.LoginNow);
        final Button RSignupButton=findViewById(R.id.RSignupButton);

        RSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = recipientfullname.getText().toString();
                final String email =  recipientLocation.getText().toString();
                final String phoneNumber =recipientPhoneNumber.getText().toString();
                final String Password = recipientEmail.getText().toString();
                final String Location = recipientPassword.getText().toString();
                if(fullname.isEmpty()||email.isEmpty()||phoneNumber.isEmpty()||Password.isEmpty()||Location.isEmpty()){
                    Toast.makeText(recipient_registration.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                //  else if(!Password.equals(Password)){
                //    Toast.makeText(DonaterRegistrationActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                //  }
                else{
                    databaseReference.child("recipient").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneNumber)){
                                Toast.makeText(recipient_registration.this, "Phone number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("recipient").child(phoneNumber).child("Donatorfullname").setValue(fullname);
                                databaseReference.child("recipient").child(phoneNumber).child("email").setValue(email);
                                databaseReference.child("recipient").child(phoneNumber).child("password").setValue(Password);
                                Toast.makeText(recipient_registration.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                Intent intent=new Intent(recipient_registration.this,Login.class);
                startActivity(intent);

            }
        });
        Loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(recipient_registration.this,Login.class);
                startActivity(intent);
            }
        });
    }
}