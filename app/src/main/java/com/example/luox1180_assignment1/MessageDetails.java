package com.example.luox1180_assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MessageDetails extends AppCompatActivity {
    String msg;
    int ID;
    protected static final String ACTIVITY_NAME = "MessageDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        if (savedInstanceState == null) {
            MessageFragment msgFragment = new MessageFragment();
            Bundle args = new Bundle();
            ID = getIntent().getExtras().getInt("id");
            msg  = getIntent().getExtras().getString("message").toString();
            args.putInt("id", ID);
            args.putString("message",msg);
            msgFragment.setArguments(args);
            FragmentTransaction ft =
                    getSupportFragmentManager().beginTransaction();
            ft.add(R.id.flContainer, msgFragment);
            ft.commit();
            Log.i(ACTIVITY_NAME,"Started Message Frame");
        }

        }

    public void onMessageDelete(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("IdToDelete", ID);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


}