package com.uas.fitguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private int intentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Hooks
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Mengaktifkan Toolbar dan NavigationView
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        //Membuat objek untuk class ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Apabila pointer navigationView tidak mengarahkan kemana-mana, maka akan kembali ke home.
        if (savedInstanceState == null){
            intentFragment = getIntent().getExtras().getInt("frgToLoad");
            //Untuk membuka fragment yang diminta berdasarkan code yang diminta pada intentFragment
            if ( intentFragment == 0) {
                Intent intent = getIntent();
                String coba = intent.getStringExtra("vnama");
                Bundle data2 = new Bundle();
                HomeFragment home = new HomeFragment();
                data2.putString(home.KEY_ACTIVITY, coba);
                home.setArguments(data2);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, home)
                        .commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }else if (intentFragment == 1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FoodFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_food);
            } else if (intentFragment == 2){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RatingFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_rate);
            }else if (intentFragment == 3) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WorkoutFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_workout);
            }
        }
    }

    //Method Pada Saat Setiap Navigasi Diklik
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_workout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WorkoutFragment()).commit();
                break;
            case R.id.nav_food:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FoodFragment()).commit();
                break;
            case R.id.nav_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmation");
                dialog.setMessage("Are you sure want to Log Out?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finish();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            case R.id.nav_website:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WebsiteFragment()).commit();
                break;
            case R.id.nav_rate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RatingFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Method untuk menutup navigation drawer saat tombol back diklik
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}