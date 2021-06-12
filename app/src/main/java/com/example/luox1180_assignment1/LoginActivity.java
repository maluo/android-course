package com.example.luox1180_assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

  protected static final String ACTIVITY_NAME = "LoginActivity";
  Button button;
  EditText emailTxt;
  int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.i(ACTIVITY_NAME, "In onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    button = findViewById(R.id.btnSubmit);
    emailTxt = findViewById(R.id.editTextTextEmail);
    LoadEmailText();
    button.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          saveEmailToSharedPreferences();
        }
      }
    );
  }

  private void saveEmailToSharedPreferences() {
    if (GlobalFunc.isEditTXTExist(emailTxt)) {
      SharedPreferences sharedPreferences = getSharedPreferences(
        "data",
        Context.MODE_PRIVATE
      );
      SharedPreferences.Editor editor = sharedPreferences.edit();
      //Clean Editor Before Putting The Next Email In
      editor.clear();
      editor.commit();
      editor.putString("email", String.valueOf(emailTxt.getText()));
      editor.commit();

      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
      startActivity(intent);
    } else {
      /*Make a toast*/
      Toast toast = Toast.makeText(this , "Email did not get entered yet",duration); //this is the ListActivity
      toast.show(); //display your message box
    }
  }

  private void LoadEmailText() {
    SharedPreferences sharedPreferences = getSharedPreferences(
      "data",
      Context.MODE_PRIVATE
    );
    String val = sharedPreferences.getString("email", "");
    if (val.length() > 0) {
      emailTxt.setText(val);
    }
  }

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
