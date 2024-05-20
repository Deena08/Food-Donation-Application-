package com.deena.justdonate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://justdonate-71d7b-default-rtdb.firebaseio.com/");
    private Button SignInButton;
    private TextView SignupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        final EditText phoneNumber =findViewById(R.id.registerPhoneNumber);
        final EditText password=findViewById(R.id.registerPassword);
        SignInButton=findViewById(R.id.SignInButtom);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PhoneText=phoneNumber.getText().toString();
                final String passwordText=password.getText().toString();
                if(passwordText.isEmpty()||PhoneText.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your Phone number", Toast.LENGTH_SHORT).show();
                }
                else{
                      databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                              if(snapshot.hasChild(PhoneText)){
                                  final String getpassword=snapshot.child(PhoneText).child("password").getValue(String.class);
                                  if(getpassword.equals(passwordText)){
                                      Toast.makeText(Login.this, "Sucessfully logged in", Toast.LENGTH_SHORT).show();
                                      startActivity(new Intent(Login.this,MainHome.class));
                                      Toast.makeText(Login.this, "WELCOME DONATOR", Toast.LENGTH_LONG).show();
                                      finish();
                                  }
                                  else{
                                      Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {

                          }
                      });
                }

            }
        });
        SignupButton=findViewById(R.id.SingupButton);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,DonaterRegistrationActivity.class));
            }
        });
    }
}