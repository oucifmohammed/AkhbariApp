package com.example.akhbariapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.akhbariapp.Fragments.AdminPost;
import com.example.akhbariapp.Fragments.AdminPostMessage;
import com.example.akhbariapp.Fragments.Education;
import com.example.akhbariapp.Fragments.Inbox;
import com.example.akhbariapp.Fragments.Past;
import com.example.akhbariapp.Fragments.Politics;
import com.example.akhbariapp.Fragments.ThisWeek;
import com.example.akhbariapp.Fragments.Today;
import com.example.akhbariapp.Fragments.Transport;
import com.example.akhbariapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private SharedPreferences admin;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_interface);

        admin = getSharedPreferences("Admin",MODE_PRIVATE);

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, new AdminPostMessage()).commit());



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.admin_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, new Today()).commit();
            navigationView.setCheckedItem(R.id.today);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.admin_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);



    }



    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment fragment=null;
        switch (item.getItemId()){

            case R.id.nav_home:
                fragment = new Today();
                break;
            case R.id.add_post:
                fragment = new AdminPost();
                break;
            case R.id.nav_msg:
                fragment = new Inbox();
                break;

        }
        assert fragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,fragment).commit();
        return true;
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.today :
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Today()).commit();
                break;
            case R.id.this_week:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new ThisWeek()).commit();
                break;

            case R.id.past:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Past()).commit();
                break;

            case R.id.transport:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Transport()).commit();
                break;

            case R.id.politics:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Politics()).commit();
                break;

            case R.id.education:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new Education()).commit();
                break;

            case R.id.log_out:
                admin = getSharedPreferences("Admin",MODE_PRIVATE);
                editor = admin.edit();
                editor.putString("access","no");
                editor.apply();
                Intent intent = new Intent(this, SignUpSignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            editor = admin.edit();
            editor.putString("access","yes");
            editor.apply();
        }
    }
}
