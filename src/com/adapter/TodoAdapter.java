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

package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.model.Todo;
import com.stackmobstarterproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This is our adapter for todo list
 */
public class TodoAdapter extends ArrayAdapter<Todo> {

  private List<Todo> mTodos = new ArrayList<Todo>();

  public TodoAdapter(Context context,
                     int textViewResourceId,
                     List<Todo> objects) {
    super(context, textViewResourceId, objects);
    mTodos = objects;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final LayoutInflater mInflater = LayoutInflater.from(getContext());
    TodoListRowHolder holder;

    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.todo_row, parent, false);
      holder = new TodoListRowHolder();
      holder.mTodoTitle = (TextView) convertView.findViewById(R.id.todoTitleTV);
      convertView.setTag(holder);
    } else {
      holder = (TodoListRowHolder) convertView.getTag();
    }

    holder.mTodoTitle.setText(mTodos.get(position).getTitle());

    return convertView;
  }

  @Override
  public int getCount() {
    return this.mTodos.size();
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public Todo getItem(int position) {
    return this.mTodos.get(position);
  }

  /**
   * This is a class to hold our row view
   */
  static class TodoListRowHolder {
    TextView mTodoTitle;
  }

}
