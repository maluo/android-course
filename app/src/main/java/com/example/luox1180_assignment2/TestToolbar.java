package com.example.luox1180_assignment2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.luox1180_assignment1.R;
import com.example.luox1180_assignment1.databinding.ActivityTestToolbarBinding;
import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    protected static final String ACTIVITY_NAME = "TestToolbarActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");

        super.onCreate(savedInstanceState);
        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.letterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.testtoolbar_lbutton), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem mi) {
        switch (mi.getItemId()){
            case R.id.action_one:
                Log.d(ACTIVITY_NAME,"Action One Selected");
                break;
            case R.id.action_two:
                Log.d(ACTIVITY_NAME,"Action Two Selected");
                break;
            case R.id.action_three:
                Log.d(ACTIVITY_NAME,"Action Three Selected");
                break;
            case R.id.action_about:
                Toast.makeText(this,"Version 1.0 by "+getString(R.string.testtoolbar_myname),Toast.LENGTH_SHORT).show();
                Log.d(ACTIVITY_NAME,"Action About Selected");
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    //@Override
    // public boolean onSupportNavigateUp() {
    //     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
    //     return NavigationUI.navigateUp(navController, appBarConfiguration)
    //             || super.onSupportNavigateUp();
    // }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }
}