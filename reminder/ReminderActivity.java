package com.example.reminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    private EditText taskEditText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private ListView reminderListView;
    private ArrayAdapter<String> adapter;
    private List<String> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_screen);

        taskEditText = findViewById(R.id.task_edittext);
        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        reminderListView = findViewById(R.id.reminder_listview);

        reminders = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reminders);
        reminderListView.setAdapter(adapter);

        loadReminders();

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });
    }

    private void addReminder() {
        String task = taskEditText.getText().toString();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("task", task);
        editor.putInt("year", year);
        editor.putInt("month", month);
        editor.putInt("day", day);
        editor.putInt("hour", hour);
        editor.putInt("minute", minute);
        editor.apply();

        String reminder = task + " on " + day + "/" + (month + 1) + "/" + year + " at " + hour + ":" + minute;
        reminders.add(reminder);
        adapter.notifyDataSetChanged();
    }

    private void loadReminders() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String task = sharedPreferences.getString("task", "");
        int year = sharedPreferences.getInt("year", 0);
        int month = sharedPreferences.getInt("month", 0);
        int day = sharedPreferences.getInt("day", 0);
        int hour = sharedPreferences.getInt("hour", 0);
        int minute = sharedPreferences.getInt("minute", 0);

        if (!task.isEmpty()) {
            String reminder = task + " on " + day + "/" + (month + 1) + "/" + year + " at " + hour + ":" + minute;
            reminders.add(reminder);
            adapter.notifyDataSetChanged();
        }
    }
}

