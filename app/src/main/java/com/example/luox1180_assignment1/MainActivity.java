package com.example.luox1180_assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.luox1180_assignment2.TestToolbar;

public class MainActivity extends AppCompatActivity {

  protected static final String ACTIVITY_NAME = "MainActivity";
  Button button;
  int LAUNCH_SECOND_ACTIVITY = 10; // This is just a sample request code would have something meaningful if possible
  int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
  int duration_long = Toast.LENGTH_LONG;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.i(ACTIVITY_NAME, "In onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    button = findViewById(R.id.btnClick);
    button.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent i = new Intent(MainActivity.this, ListItemsActivity.class);
          startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
        }
      }
    );
  }

  public void onStartChat(View v) {
    Intent i = new Intent(MainActivity.this, ChatWindow.class);
    startActivity(i);
    Log.i(ACTIVITY_NAME, getString(R.string.user_start_chat));
  }

  public void onStartTestToolBar(View v) {
    Intent i = new Intent(MainActivity.this, TestToolbar.class);
    startActivity(i);
    Log.i(ACTIVITY_NAME, getString(R.string.user_start_chat));
  }


  @Override
  protected void onActivityResult(
    int requestCode,
    int resultCode,
    Intent data
  ) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == LAUNCH_SECOND_ACTIVITY) {
      Log.i(
        ACTIVITY_NAME,
        "Returned to MainActivity.onActivityResult from ListItemActivity"
      );

      if (resultCode == Activity.RESULT_OK) {
        String result = data.getStringExtra("Response");
        Toast toast = Toast.makeText(this, result, duration_long);
        toast.show();
      }
      if (resultCode == Activity.RESULT_CANCELED) {
        Toast toast = Toast.makeText(this, "List Activity Cancelled", duration); //this is the ListActivity
        toast.show();
      }
    }
  } //onActivityResult

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
