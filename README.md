stackmob-android-starter
========================

A Starter Project for New Android Projects that use StackMob for their Backend


***
### Running Project in Eclipse ###

  1.  Clone, fork, or download the source code from this repo
  2.  Fire up Eclipse and go to `Add New Project` -> `Android Project From Existing Code`
  3.  Make `Root Directory` points to `stackmob-android-starter` directory
  4.  Hit `Finish`
  5.  Open `BaseActivity.java` in `src -> com.stackmobstarterproject`
  6.  Inside `onCreate` method, you will see `StackMobAndroid.init`. Change `YOUR_PUBLIC_KEY` into your app's public key
  7.  Run the project as `Android Application`
  8.  Create a new Android Virtual Device (AVD) if you need to. Just make sure that the AVD needs to have at least API level 14 or Android version 4.0+

***
### Running Project in Android Studio ###

  1. Clone, fork, or download the source code from this repo
  2. Open Android Studio and choose `Import Project`
  3. Select `stackmob-android-starter` then hit `OK`
  4. Select `Create project from existing sources` and `Next`
  5. For simplicity, don't change `Project Name` and `Project Location`. Hit `Next`
  6. You should see 2 directories checked, 1 is `path/to/stackmob-android-starter` and the other one is `path/to/stackmob-android-starter/src`. Hit `Next`
  7. For libraries, you should see 2 of them: `android-support-v4.jar` and `stackmob-android-sdk-1.3.5.jar`
  8. For dependencies, `stackmob-android-starter` should be checked to use `libs`
  9. Make sure you have Java Virtual Machines since we will need to have that (if you don't have it, Google how to install JDK). Hit `Next`
  10. You should see `AndroidManifest.xml` checked. Hit `Finish`
  11. Open `BaseActivity.java` in `src -> com.stackmobstarter.project`
  12. Inside `onCreate` method, you will see `StackMobAndroid.init`. Change `YOUR_PUBLIC_KEY` into your app's public key
  13. Run the project
  14. Create a new Android Virtual Device (AVD) if you need to. Just make sure that the AVD needs to have at least API level 14 or Android version 4.0+

***
### Project Info ###
* `android:minSdkVersion="14"` and `android:targetSdkVersion="19"`. Hence, this project only works for Android 4.0+.
* Uses StackMob Android SDK v1.3.5
