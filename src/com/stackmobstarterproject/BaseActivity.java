package com.stackmobstarterproject;

import android.app.Activity;
import android.os.Bundle;

import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMob;

/**
 * Created by ardo on 11/1/13.
 */
public class BaseActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // initialize StackMob --- Change YOUR_PUBLIC_KEY with your app's public key
    if (StackMob.getStackMob() == null) {
      StackMobAndroid.init(getApplicationContext(), 0, "cff876ce-f111-410e-b35c-ab22abeac7d7");
    }
  }
}
