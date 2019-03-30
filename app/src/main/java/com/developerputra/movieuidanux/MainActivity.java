package com.developerputra.movieuidanux;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            setFragment(new MenuFragment(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String title = "";

       if (id == R.id.nav_pencarian) {
           Intent pencarian1 = new Intent(MainActivity.this,Pencarian.class);
           startActivity(pencarian1);
        } else if (id == R.id.nav_bahasa) {
           Intent pencarian2 = new Intent(Settings.ACTION_LOCALE_SETTINGS);
           startActivity(pencarian2);
       }

        setFragment(fragment, title);
        return true;
    }

    public void setFragment(Fragment fragment, String title){
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }
        getSupportActionBar().setTitle(title);
    }

}
