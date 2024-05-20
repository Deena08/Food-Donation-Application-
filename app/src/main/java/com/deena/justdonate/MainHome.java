package com.deena.justdonate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.deena.justdonate.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.deena.justdonate.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.deena.justdonate.HelperClasses.HomeAdapter.FeaturedAdpater;
import com.deena.justdonate.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.deena.justdonate.HelperClasses.HomeAdapter.MostViewedAdpater;
import com.deena.justdonate.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView Logout, menu_icon;
    private ImageView Donate,history,status1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    RecyclerView categoriesRecycler;
    RecyclerView mostViewedRecycler;
    private TextView cate1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_home);
        history=findViewById(R.id.historyPage);
        Logout = findViewById(R.id.Logout1);
        Donate = findViewById(R.id.Donate);
        menu_icon = findViewById(R.id.menu_icon);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        featuredRecycler=findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        cate1=findViewById(R.id.categories1);
        status1=findViewById(R.id.status_img);
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
        navigationDrawer();
        status1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHome.this,StatusPage.class);
                startActivity(intent);
            }
        });
        cate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHome.this,categories.class);
                startActivity(intent);
            }
        });
        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHome.this, Donation.class);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHome.this, Login.class);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainHome.this,History.class);
                startActivity(intent);
            }
        });
    }


    private void mostViewedRecycler() {
        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.organization1, "charity1"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.organization2, "charity2"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.organization3, "charity3"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.organization4, "charity4"));
        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);
    }

    private void categoriesRecycler() {
        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.fooddd, "FOOD"));
        categoriesHelperClasses.add(new CategoriesHelperClass( R.drawable.cloths, "CLOTHS"));
        categoriesHelperClasses.add(new CategoriesHelperClass( R.drawable.money, "MONEY"));
        categoriesHelperClasses.add(new CategoriesHelperClass( R.drawable.car4455, "MONEY"));
        categoriesHelperClasses.add(new CategoriesHelperClass( R.drawable.atv, "CAR"));

        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);
    }




    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



        ArrayList<FeaturedHelperClass> featuredDonations = new ArrayList<>();
        featuredDonations.add(new FeaturedHelperClass(R.drawable.buildings2, "charity1", "WE REQUEST,WE REQUESTWE REQUESTWE REQUESTWE REQUESTWE REQUEST "));
        featuredDonations.add(new FeaturedHelperClass(R.drawable.buildings2, "charity2", "WE REQUEST,WE REQUESTWE REQUESTWE REQUESTWE REQUESTWE REQUEST"));
        featuredDonations.add(new FeaturedHelperClass(R.drawable.buildings2, "charity3", "WE REQUEST,WE REQUESTWE REQUESTWE REQUESTWE REQUESTWE REQUEST"));

        adapter = new FeaturedAdpater(featuredDonations);
        featuredRecycler.setAdapter(adapter);

      //  GradientDrawable gradient1= new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xffeff400,0xffaff600});

    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_all_categories:
                Intent intent=new Intent(getApplicationContext(),categories.class);
                startActivity(intent);
                break;
            case R.id.logout:
                Intent intent1=new Intent(getApplicationContext(),Login.class);
                startActivity(intent1);
                break;
            case R.id.nav_home:
                Intent intent2=new Intent(getApplicationContext(),MainHome.class);
                startActivity(intent2);
                break;
        }
        return true;

    }
}