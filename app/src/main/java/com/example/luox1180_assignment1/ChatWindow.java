package com.example.luox1180_assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    Button btnSend;
    EditText toSend;
    ListView chatView;
    ArrayList<String> chatHistory;
    protected static final String ACTIVITY_NAME = "ChatWindowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        findComponents();

    }

    public void onTextSend(View v) {
        if(toSend.getText().length()>0){
            chatHistory.add(toSend.getText().toString());
        }
        Log.i(ACTIVITY_NAME, getString(R.string.user_send_chat));
    }

    void findComponents(){
        btnSend = findViewById(R.id.btnSend);
        toSend = findViewById(R.id.txtToSend);
        chatView = findViewById(R.id.chatView);
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

class ChatAdapter extends ArrayAdapter<String>
{
    public ChatAdapter(Context ctx) {
        super(ctx, 0);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}