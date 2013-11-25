/**
 * Copyright 2012-2013 StackMob
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
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
