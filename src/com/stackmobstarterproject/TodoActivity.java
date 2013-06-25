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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.adapter.TodoAdapter;
import com.constants.Constants;
import com.model.Todo;
import com.stackmob.android.sdk.common.StackMobAndroid;
import com.stackmob.sdk.api.StackMobQuery;
import com.stackmob.sdk.callback.StackMobCallback;
import com.stackmob.sdk.callback.StackMobQueryCallback;
import com.stackmob.sdk.exception.StackMobException;
import com.util.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This is our main activity
 */
public class TodoActivity extends Activity {

  // this is where we are going to store our todo items
  private List<Todo> mTodos = new ArrayList<Todo>();

  private ListView mTodoListView;

  // this is for our spinner when refreshing todos
  private Menu optionsMenu;

  private TodoAdapter mTodoAdapter;

  private final Context thisContext = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_todo);

    // initialize StackMob --- Change YOUR_PUBLIC_KEY with your app's public key
    StackMobAndroid.init(getApplicationContext(), 0, "YOUR_PUBLIC_KEY");

    mTodoListView = (ListView) this.findViewById(R.id.atTodoListView);

    /**
     * One may ask, "why don't we add android:label in the manifest file for this activity?"
     * Because if we do so, our project name will show as "Todo List" instead of "StackMob Starter Project"
     * It's just an Android thing
     */
    setTitle("Todo List"); // set the title bar

    setOnClickListeners();
    fetchAllTodos();
  }

  @Override
  public void onResume() {
    super.onResume();
    /**
     * On activity resume, we want to make sure we have the latest data
     * Always fetch all todos
     */
    fetchAllTodos();
  }

  private void setOnClickListeners() {
    mTodoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent,
                              final View view,
                              final int position,
                              long id) {

        final Todo item = (Todo) parent.getItemAtPosition(position);
        if (item != null) {
          // prepare data to send to TodoDetailActivity
          Bundle newBundle = new Bundle();
          newBundle.putString(Constants.TODO_ID, item.getID());
          newBundle.putString(Constants.TODO_TITLE, item.getTitle());
          /**
           * One might ask, "can I just pass in the entire object into a Bundle?"
           * Of course you can, if you want to do so, take a look into passing a Serializable object
           *   into Bundle in Android docs
           */
          ActivityUtil.switchActivity(thisContext, TodoDetailActivity.class, newBundle, true);
        } else {
          ActivityUtil.showToast(TodoActivity.this,
              "Item at position " + position + " does not exist");
        }

      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this.optionsMenu = menu; // set our `optionsMenu` variable so we can show spinner status
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.todo, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch(id) {
      case android.R.id.home:
        break;
      case R.id.action_add_new: // user clicked on add new todo
        showAddNewDialog();
        return true;
      case R.id.action_refresh: // user clicked on refresh
        fetchAllTodos();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Method to temporarily replace our refresh icon with an indeterminate spinner progress
   * @param refreshing true if we want to show spinner progress; false otherwise
   */
  private void setRefreshActionButtonState(final boolean refreshing) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (optionsMenu != null) {
          // get the refresh icon
          final MenuItem refreshItem = optionsMenu
              .findItem(R.id.action_refresh);

          if (refreshItem != null) {
            if (refreshing) {
              // replace the refresh icon with spinner progress
              refreshItem.setActionView(R.layout.ab_indeterminate_progress);
            } else {
              // hide away the spinner progress and return the refresh icon back
              refreshItem.setActionView(null);
            }
          }
        }
      }
    });
  }

  /**
   * Method to show a dialog to add a new Todo
   * We will use an AlertDialog since it has nice Yes / No buttons layout
   */
  private void showAddNewDialog() {
    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(thisContext);

    // add_new_todo is for the body of our dialog
    // antRootLayout is the main container of our body dialog (i.e. the root layout for add_new_todo)
    final View view = getLayoutInflater().inflate(R.layout.add_new_todo,
        (ViewGroup) findViewById(R.id.antRootLayout));
    final EditText mTitleET = (EditText) view.findViewById(R.id.antEditText); // title edit text

    dialogBuilder.setView(view); // set the AlertDialog to our custom view

    // set onclick event for our dialog
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
          case DialogInterface.BUTTON_POSITIVE:
            // user clicked `Create` button

            // create a new `Todo` instance
            final Todo newTodo = new Todo(mTitleET.getText().toString());

            // save the new `Todo` instance to StackMob
            newTodo.save(new StackMobCallback() {
              @Override
              public void success(String s) {
                // on success, add the new todo into our ArrayList and notify the list adapter
                mTodos.add(newTodo);

                // remember that in order to update UI in main view, it needs to be run from UI thread
                runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    mTodoAdapter.notifyDataSetChanged();
                  }
                });
              }

              @Override
              public void failure(StackMobException e) {
                // Oops, there's an error, show a Toast to user
                ActivityUtil.showToast(TodoActivity.this, "Failed saving todo: " + e.getMessage());
              }
            });

            break;

          case DialogInterface.BUTTON_NEGATIVE:
            // user clicked `Cancel` button, hence just dismiss the dialog
            dialog.dismiss();
            break;

          case DialogInterface.BUTTON_NEUTRAL:
            // we don't have neutral button, so just leave this empty
            break;
        }
      }
    };

    // create our Add New Todo dialog
    final AlertDialog dialog = dialogBuilder.setTitle("Add New Todo")
        .setPositiveButton("Create", dialogClickListener)
        .setNegativeButton("Cancel", dialogClickListener)
        .create();

    // show keyboard
    mTitleET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (b) {
          dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
      }
    });

    // finally show our dialog
    dialog.show();
  }

  /**
   * Unobtrusive method to fetch all todos and update the list view
   */
  private void fetchAllTodos() {
    setRefreshActionButtonState(true); // change our refresh btn to a spinner

    // fetch all todo objects from StackMob
    // Pass in an empty query in order to fetch all objects
    Todo.query(Todo.class, new StackMobQuery(), new StackMobQueryCallback<Todo>() {
      @Override
      public void success(List<Todo> todos) {
        setRefreshActionButtonState(false); // hide the spinner progress
        mTodos = todos; // set our todos container

        // set our list view adapter
        mTodoAdapter = new TodoAdapter(thisContext, android.R.layout.simple_list_item_1, mTodos);

        // remember that in order to update UI in main view, it needs to be run from UI thread
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            mTodoListView.setAdapter(mTodoAdapter);
          }
        });
      }

      @Override
      public void failure(StackMobException e) {
        setRefreshActionButtonState(false); // hide the spinner progress
        runOnUiThread(new Runnable() {
          @Override
          public void run() {

          }
        });
        ActivityUtil.showToast(TodoActivity.this,
            "Error occured when fetching todos: " + e.getMessage());
      }
    });
  }
}
