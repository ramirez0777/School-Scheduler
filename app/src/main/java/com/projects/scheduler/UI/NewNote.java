package com.projects.scheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewNote extends AppCompatActivity {

    public static int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        EditText text = findViewById(R.id.text);


        FloatingActionButton fab = findViewById(R.id.fabAssessment);
        Repository repository = new Repository(getApplication());
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Note noteToSave = new Note(0, text.getText().toString(), courseId);
                repository.insert(noteToSave);
                Intent intent = new Intent(NewNote.this, CourseDetails.class);
                startActivity(intent);
            }
        });
    }
}