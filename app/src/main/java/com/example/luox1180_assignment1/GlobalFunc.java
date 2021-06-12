package com.example.luox1180_assignment1;

import android.widget.EditText;

public class GlobalFunc {

  public static boolean isEditTXTExist(EditText txt) {
    return String.valueOf(txt.getText()).length() > 0;
  }
}
