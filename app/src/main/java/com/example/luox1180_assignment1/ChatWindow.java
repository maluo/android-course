package com.example.luox1180_assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
  boolean isFragmented = false;
  Cursor c;
  int LAUNCH_PHONE_ACTIVITY = 10;
  int DELETE_MESSAGE = 11;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_window);
    findComponents();

    isFragmented = findViewById(R.id.sidebar) !=  null;
    Log.i(ACTIVITY_NAME, getString(R.string.chat_fragment));

    dbHelper = new ChatDatabaseHelper(this);
    readable = dbHelper.getReadableDatabase();
    dbHelper.onOpen(readable);
    writable = dbHelper.getWritableDatabase();
    ReadDB();
    messageAdapter = new ChatAdapter( this);
    chatView.setAdapter (messageAdapter);

    chatView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView parent, View view,int position, long id) {
        //tablet
        Bundle args = new Bundle();
        args.putInt("id", (int)id);
        args.putString("message",c.getString(c.getColumnIndex(ChatDatabaseHelper.COLUMN_ITEM)));
        if (isTablet() && isFragmented) {
          Log.i(ACTIVITY_NAME,"Started Side Bar");
          //Will give flexibility for landscape mode here
          //getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
          MessageFragment msgFragment = new MessageFragment(ChatWindow.this);
          msgFragment.setArguments(args);
          FragmentTransaction ft =
                  getSupportFragmentManager().beginTransaction();
          //ft.
          ft.replace(R.id.sidebar, msgFragment);//Using the sidebar to the right side of list view
          ft.commit();
        }else{
          Intent i = new Intent(ChatWindow.this, MessageDetails.class);
          i.putExtras(args);
          startActivityForResult(i, DELETE_MESSAGE);
        }

      }
    });//End of listenner
  }

  protected void onActivityResult(
          int requestCode,
          int resultCode,
          Intent data
  ) {
    if (requestCode == DELETE_MESSAGE && resultCode == RESULT_OK) {
      DeleteMsg(data.getIntExtra("IdToDelete",0));

    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  public void DeleteMsg(int id){
    dbHelper.deleteMsg((long) id,writable);
    ReadDB();
    messageAdapter.notifyDataSetChanged();
  }

  private void ReadDB(){
    chatHistory.clear();
    c = readable.rawQuery("select * from items",new String[]{});
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
      ReadDB();
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

  private boolean isTablet() {
    Display display = ((Activity) this).getWindowManager().getDefaultDisplay();
    DisplayMetrics metrics = new DisplayMetrics();
    display.getMetrics(metrics);

    float widthInches = metrics.widthPixels / metrics.xdpi;
    float heightInches = metrics.heightPixels / metrics.ydpi;
    double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
    return diagonalInches >= 7.0;
  }

  private class ChatAdapter extends ArrayAdapter<String> {

     public ChatAdapter(Context ctx) {
      super(ctx, 0);
    }

    @Override
    public long getItemId(int position) {
       //Try to get database id from here
      c.moveToPosition(position);
      return c.getInt(c.getColumnIndex(ChatDatabaseHelper.COLUMN_ID));
      //return super.getItemId(position);
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
