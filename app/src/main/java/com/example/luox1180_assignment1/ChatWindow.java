package com.example.luox1180_assignment1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

  Button btnSend;
  EditText toSend;
  ListView chatView;
  ArrayList<String> chatHistory;
  protected static final String ACTIVITY_NAME = "ChatWindowActivity";
  ChatAdapter messageAdapter;

  SQLiteDatabase readable;
  ChatDatabaseHelper dbHelper;
  SQLiteDatabase writable;

  public static final String TABLE_ITEMS = "items";
  public static final String COLUMN_ID = "KEY_ID";
  public static final String COLUMN_ITEM = "KEY_MESSAGE";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_window);
    findComponents();

    dbHelper = new ChatDatabaseHelper(this);
    readable = dbHelper.getReadableDatabase();
    dbHelper.onOpen(readable);
    writable = dbHelper.getWritableDatabase();
    ReadDB();
    messageAdapter = new ChatAdapter( this);
    chatView.setAdapter (messageAdapter);
  }

  private void ReadDB(){
    Cursor c = readable.rawQuery("select * from items",new String[]{});
    /*int colIndex = c.getColumnIndex(COLUMN_ITEM);
    for(int i = 0; i < c.getCount(); i++){
      String message = c.getString(colIndex);
      chatHistory.add(message);
      c.moveToNext();
    }*/
    Log.i(ACTIVITY_NAME, "Cursor’s  column count = " + c.getColumnCount() );
    c.moveToFirst();
    while(!c.isAfterLast() ) {
      String message = c.getString(c.getColumnIndex(ChatDatabaseHelper.COLUMN_ITEM));
      Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + message);
      chatHistory.add(message);
      c.moveToNext();
    }
  }

  public void onTextSend(View v) {
    if (toSend.getText().length() > 0) {
      chatHistory.add(toSend.getText().toString());
      dbHelper.insertMsg(toSend.getText().toString(),writable);
      messageAdapter.notifyDataSetChanged();
    }
    toSend.setText(""); //Clear the content
    Log.i(ACTIVITY_NAME, getString(R.string.user_send_chat));
    Log.i(ACTIVITY_NAME, String.valueOf(chatHistory.toArray().length));
  }

  void findComponents() {
    chatHistory = new ArrayList<String>();
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
    dbHelper.close();
  }

  @Override
  protected void onStop() {
    Log.i(ACTIVITY_NAME, "In onStop()");
    super.onStop();
  }

  private class ChatAdapter extends ArrayAdapter<String> {

     public ChatAdapter(Context ctx) {
      super(ctx, 0);
    }

    @Override
    public int getCount() {
      //return super.getCount();
      return chatHistory.size();
    }

    @Override
    public String getItem(int position) {
       return chatHistory.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) ChatWindow.this.getLayoutInflater();

      View result = null;
      if (position % 2 == 0) result =
        inflater.inflate(R.layout.chat_row_outgoing, null); else result =
        inflater.inflate(R.layout.chat_row_incoming, null);

      TextView message = (TextView) result.findViewById(R.id.message_text);
      message.setText(getItem(position)); // get the string at position
      return result;
      //return super.getView(position, convertView, parent);
    }
  }
}
