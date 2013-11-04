package com.stackmobstarterproject;

import android.app.Activity;
import android.os.Bundle;

import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMob;

/**
 * BaseActivity to initialize StackMob
 *
 * Why do we need to have this?
 * We need this because StackMob will not be initialized once your app is wiped from the memory.
 * By extending this `BaseActivity`, StackMob will always be guaranteed to get initialized
 */
public class BaseActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // initialize StackMob --- Change YOUR_PUBLIC_KEY with your app's public key
    if (StackMob.getStackMob() == null) {
      StackMobAndroid.init(getApplicationContext(), 0, "YOUR_PUBLIC_KEY");
    }
  }
}
