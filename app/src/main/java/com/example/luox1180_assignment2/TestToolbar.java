package com.example.luox1180_assignment2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.luox1180_assignment1.R;
import com.example.luox1180_assignment1.databinding.ActivityTestToolbarBinding;
import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    protected static final String ACTIVITY_NAME = "TestToolbarActivity";
    Snackbar snackbar;

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

    void actionThreeDialog(){
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);
        customDialog.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_message_box);
                        String message = edit.getText().toString();
                        Snackbar.make(findViewById(R.id.dialogbox), message, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }

    void backToMain(){
        Log.d(ACTIVITY_NAME,getString(R.string.testtoolbar_back_title));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.testtoolbar_back_title);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem mi) {
        switch (mi.getItemId()){
            case R.id.action_one:
                Log.d(ACTIVITY_NAME,getString(R.string.testtoolbar_action_one));
                Snackbar.make(findViewById(R.id.dialogbox), getString(R.string.testtoolbar_select_action_one), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                backToMain();
                Log.d(ACTIVITY_NAME,getString(R.string.testtoolbar_action_two));
                break;
            case R.id.action_three:
                Log.d(ACTIVITY_NAME,getString(R.string.testtoolbar_action_three));
                actionThreeDialog();
                break;
            case R.id.action_about:
                Toast.makeText(this,"Version 1.0 by "+getString(R.string.testtoolbar_myname),Toast.LENGTH_SHORT).show();
                Log.d(ACTIVITY_NAME,getString(R.string.testtoolbar_action_about));
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