package com.example.luox1180_assignment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListItemsActivity extends AppCompatActivity {

  protected static final String ACTIVITY_NAME = "ListItemActivity";
  static final int REQUEST_IMAGE_CAPTURE = 123;
  ImageButton imgBtn;
  String mCurrentPhotoPath = ""; // the path of the last image file created
  Switch aSwitch;
  int duration = Toast.LENGTH_SHORT;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.i(ACTIVITY_NAME, "In onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_items);

    imgBtn = findViewById(R.id.imgBtn);
    aSwitch = findViewById(R.id.swtichBox);
  }

  //Step 4.
  public void imageClicked(View v) {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
  }

  //Step 5.
  public void setOnCheckedChanged(View v) {
    if (aSwitch.isChecked()) {
      CharSequence text = getString(R.string.swtich_on);
      Toast toast = Toast.makeText(this, text, duration);
      toast.show();
    } else {
      CharSequence text = getString(R.string.switch_off);
      Toast toast = Toast.makeText(this, text, duration);
      toast.show();
    }
  }

  //Step 6.
  public void OnCheckChanged(View v) {
    AlertDialog.Builder builder = new AlertDialog.Builder(
      ListItemsActivity.this
    );
    builder
      .setMessage(R.string.dialog_message) //Add a dialog message to strings.xml
      .setTitle(R.string.dialog_title)
      .setPositiveButton(
        R.string.ok,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            //Step 7. Return data directly to the main activity
            //Step 8. Continues on the Main Activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Response", getString(R.string.list_response));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
          }
        }
      )
      .setNegativeButton(
        R.string.cancel,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
          }
        }
      )
      .show();
  }


  @Override
  protected void onActivityResult(
    int requestCode,
    int resultCode,
    Intent data
  ) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      Bundle extras = data.getExtras();
      Bitmap imageBitmap = (Bitmap) extras.get("data");
      imgBtn.setImageBitmap(imageBitmap);
      //imgBtn.setImageURI(Uri.parse(mCurrentPhotoPath));
    }
    super.onActivityResult(requestCode, resultCode, data);
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

  private File createImageFile() throws IOException {
    // Create file name with time stamp
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
    .format(new Date());
    String imageFileName = "IMG_" + timeStamp + "_";

    // Grab storage directory of Pictures/
    File storageDir = Environment.getExternalStoragePublicDirectory(
      Environment.DIRECTORY_PICTURES
    );

    // Create temporary file with the name , file type, & path
    File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);

    // Save a file: path for use with ACTION_VIEW intents
    mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath(); // this is a URI string
    return imageFile;
  }

  public void takeAPicture(View v) {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Ensure there's a camera activity to handle intent
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      Log.i(ACTIVITY_NAME, "Trying to create a file");
      File photoFile = null;
      try {
        // create a temp file where the photo will go
        photoFile = createImageFile();
      } catch (IOException ex) {
        Log.e("MainActivity", "Cannot create file");
        Log.e("MainActivity", ex.getMessage());
      }

      if (photoFile != null) {
        takePictureIntent.putExtra(
          MediaStore.EXTRA_OUTPUT,
          Uri.fromFile(photoFile)
        ); // tell where the camera should save the photo
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); // fire Camera app to request an image capture
      }
    } //End Of If
  }
}
