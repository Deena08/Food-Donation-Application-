package com.deena.justdonate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Donation extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://justdonate-71d7b-default-rtdb.firebaseio.com/");
    public static final String TAG = "TAG";
    private StorageReference storageReference;
    //FirebaseAuth fAuth;
    FirebaseUser user;
    RadioGroup rg1;

    FirebaseFirestore fStore;
    private Button submitButton;

    //Donation donations;


    EditText mobile, address, state_name, tvLatitude, tvLongitude,username,email;

    private RadioButton Rdonation_Type,onlyanimals, onlypeoples, both, foodT, FnC, cloths, pickup, self,weight1,weight2;

    private String donation_For="",donationType ="", donationweight = "", donationvehiclet = "",
            currentUserID, DonationDate, DonationTime, locationLatitude, locationLongitude,
            currentLocation;

    FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference userDatabaseReference;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient
                (Donation.this);
        getLocation();


        onlyanimals = findViewById(R.id.onlyanimals);
        onlypeoples = findViewById(R.id.onlypeople);
        both = findViewById(R.id.both);
        cloths = findViewById(R.id.cloths);
        self = findViewById(R.id.self);
        pickup = findViewById(R.id.pickup);
        foodT = findViewById(R.id.FoodT);
        FnC = findViewById(R.id.FnC);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.Address);
        state_name = findViewById(R.id.state_name);
        tvLatitude = findViewById(R.id.tv_latitude);
        tvLongitude = findViewById(R.id.tv_longitude);
        submitButton = findViewById(R.id.submitButton);
        username=findViewById(R.id.user_name);
        weight1=findViewById(R.id.weight1);
        weight2=findViewById(R.id.weight2);
        email=findViewById(R.id.email);
        rg1=findViewById(R.id.Rg1);


       weight1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               weight2.setChecked(false);
               donationweight="2kg - 25kg";
           }
       });
        weight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight1.setChecked(false);
                donationweight="25kg and Above";
            }
        });
       onlyanimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlypeoples.setChecked(false);
                both.setChecked(false);
                donation_For = "Only Animals";
            }
        });

        onlypeoples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyanimals.setChecked(false);
                both.setChecked(false);
                donation_For = "Only Peoples";
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyanimals.setChecked(false);
                onlypeoples.setChecked(false);
                donation_For = "Both";
            }
        });

        cloths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodT.setChecked(false);
                FnC.setChecked(false);
                donationType = "cloths";
            }
        });

        foodT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FnC.setChecked(false);
                cloths.setChecked(false);
                donationType = "Food";
            }
        });

        FnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cloths.setChecked(false);
                foodT.setChecked(false);
                donationType = "Both";
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickup.setChecked(false);
                donationvehiclet = "self Delivery";
            }
        });

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.setChecked(false);
                donationvehiclet = "Request Pickup";
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String animals = onlyanimals.getText().toString();
                String people = onlypeoples.getText().toString();
                String all = both.getText().toString();
                String food_ = foodT.getText().toString();
                String cloths_ = cloths.getText().toString();
                String FnC_ = FnC.getText().toString();
                String self_ = self.getText().toString();
                String pickup_ = pickup.getText().toString();

                String txt_address = address.getText().toString();
                String txt_state = state_name.getText().toString();
                String txt_mobile = mobile.getText().toString();
                String txt_name=username.getText().toString();
                String txt_email=email.getText().toString();
                String txt_latitude = tvLatitude.getText().toString();
                String txt_longitude = tvLongitude.getText().toString();

                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy:MM:dd");
                String date = currentDate.format(calendar1.getTime());


                Calendar calendar3 = Calendar.getInstance();
                SimpleDateFormat currentTime1 = new SimpleDateFormat("HHmmss");
                String requestId = currentTime1.format(calendar3.getTime());



                Calendar calendar2 = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
                String time = currentTime.format(calendar2.getTime());


                if (txt_address.isEmpty() || txt_state.isEmpty()|| txt_mobile.isEmpty()) {
                    Toast.makeText(Donation.this, "Fill all Fill all fields", Toast.LENGTH_SHORT).show();;
                }
             //   else if (txt_latitude.isEmpty()) {
               //     tvLatitude.setError("Latitude is Empty");

               // } else if (txt_longitude.isEmpty()) {
               //     tvLongitude.setError("Longitude is Empty");
               // }
                else{
                    databaseReference.child("Donations").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Donations").child(txt_mobile).child("Donation For").setValue(donation_For);
                            databaseReference.child("Donations").child(txt_mobile).child("Food or cloths").setValue(donationType);
                            databaseReference.child("Donations").child(txt_mobile).child("Request pick or Self Delivery").setValue(donationvehiclet);
                            databaseReference.child("Donations").child(txt_mobile).child("Donation weight").setValue(donationweight);
                            databaseReference.child("Donations").child(txt_mobile).child("Full name").setValue(txt_name);
                            databaseReference.child("Donations").child(txt_mobile).child("Address").setValue(txt_address);
                            databaseReference.child("Donations").child(txt_mobile).child("State Name").setValue(txt_state);
                            databaseReference.child("Donations").child(txt_mobile).child("Date").setValue(date);
                            databaseReference.child("Donations").child(txt_mobile).child("Time").setValue(time);
                            databaseReference.child("Donations").child(txt_mobile).child("Email address").setValue(txt_email);
                            databaseReference.child("Donations").child(txt_mobile).child("Request ID").setValue(requestId);
                            databaseReference.child("Donations").child(txt_mobile).child("Location").setValue(txt_latitude);
                            databaseReference.child("Donations").child(txt_mobile).child("Location").setValue(txt_longitude);
                            Toast.makeText(Donation.this, "Donation successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                Intent intent1 = new Intent(Donation.this, PickUp.class);
               // Toast.makeText(Donation.this, "Success !",
                     //   Toast.LENGTH_LONG).show();
                startActivity(intent1);
                try {
                    String stringSenderEmail = "21eucs504@skcet.ac.in";
                    String stringReceiverEmail = txt_email;
                    String stringPasswordSenderEmail = "d@9551543067D";

                    String stringHost = "smtp.gmail.com";

                    Properties properties = System.getProperties();

                    properties.put("mail.smtp.host", stringHost);
                    properties.put("mail.smtp.port", "465");
                    properties.put("mail.smtp.ssl.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                        }
                    });

                    MimeMessage mimeMessage = new MimeMessage(session);
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

                    mimeMessage.setSubject("Subject: Android App email");
                    mimeMessage.setText("Hi "+txt_name+" Greeting from Just Donate! \n " +
                            ",\n\nThanking you for your valuable Donation. \n\n*******Cheers!Cheers*******\n*****Keeping Donating keeping Rocking********");

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Transport.send(mimeMessage);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Donation.this, "Conformation mail Sent", Toast.LENGTH_LONG).show();

            }

        });
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Donation.this, Manifest.
                permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Donation.this, Manifest.
                permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //////////When Both permissions are granted/////////////

            /////////////call Method/////////
            getCurrentLocation();
        } else {
            //////When permission is not granted
            //////Request permission
            ActivityCompat.requestPermissions(Donation.this, new
                    String[]{Manifest.
                    permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions,
                                           @NonNull int[] grantResults) {
        ///////////Check condition
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] +
                grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            ///////////////////when permission Granted
            ///Call Methods
            getCurrentLocation();
        } else {
            //////////////////when permissions are denied
            Toast.makeText(Donation.this, "Permission denied",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        //////////////Intialize Location manager////////////////////
        LocationManager locationManager = (LocationManager) getSystemService(Context.
                LOCATION_SERVICE);

        ////////////////check condition/////////////////////
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            ///////////////When Location is enabled//////////////////
            ///////////Get Last Location////////////////////////////
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    ////////////Initialize Location////////////////////
                    Location location = task.getResult();
                    /////check condition//////
                    if (location != null) {
                        ////////////// when location result is not null///////////
                        //// Set Lalitude
                        tvLatitude.setText(String.valueOf(location.getLatitude()));
                        /////set Longitude
                        tvLongitude.setText(String.valueOf(location.getLongitude()));

                    } else {
                        ///When Location result is null
                        /////Intialize Location request
                        LocationRequest locationRequest = new LocationRequest().setPriority
                                        (LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000).setFastestInterval(1000).setNumUpdates(1);

                        ///////////Initialize Location call back
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                //////////Initialize Location
                                Location location1 = locationResult.getLastLocation();
                                ///////////set Latitude
                                tvLatitude.setText(String.valueOf(location1.getLatitude()));
                                /////////set Longitude
                                tvLongitude.setText(String.valueOf(location1.getLongitude()));

                            }
                        };

                        ////////////Request Location updates
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }

                }
            });

        } else {
            ///////////When Location service is not enabled
            //////////Open Location Setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).
                    setFlags(Intent.
                            FLAG_ACTIVITY_NEW_TASK));
        }
    }


    public void refresh(View view) {
        Toast.makeText(Donation.this, "Refresh", Toast.LENGTH_SHORT).show();
        getLocation();

    }

    public void backflotingbtn(View view) {
        Intent intent = new Intent(Donation.this, MainHome.class);
        startActivity(intent);
    }
}