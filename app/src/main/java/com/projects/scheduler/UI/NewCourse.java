package com.projects.scheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Course;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewCourse extends AppCompatActivity {

    public static Course courseToEdit = null;
    public static int termId;

    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    final Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        EditText courseName = findViewById(R.id.assessmentName);
        Button startDate = findViewById(R.id.startDate);
        Button endDate = findViewById(R.id.endDate);

        EditText instructorName = findViewById(R.id.instructorName);
        EditText instructorPhoneNumber = findViewById(R.id.instructorPhone);
        EditText instructorEmail = findViewById(R.id.instructorEmail);

        Spinner courseStatus = findViewById(R.id.courseStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.course_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatus.setAdapter(adapter);

        if(courseToEdit == null){
            setTitle(R.string.new_course);
        } else{
            setTitle(R.string.edit_course);
            courseName.setText(courseToEdit.getName());
            startDate.setText(courseToEdit.getStartDate());
            endDate.setText(courseToEdit.getEndDate());
            courseStatus.setSelection(adapter.getPosition(courseToEdit.getStatus()));
            instructorName.setText(courseToEdit.getInstructor());
            instructorPhoneNumber.setText(courseToEdit.getInstructorPhoneNumber());
            instructorEmail.setText(courseToEdit.getInstructorEmail());
        }


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(NewCourse.this, startDateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate("start");
            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(NewCourse.this, endDateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate("end");

            }

        };

        FloatingActionButton fab = findViewById(R.id.fabAssessment);
        Repository repository = new Repository(getApplication());
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(startDate.getText().equals(R.string.start_date) || endDate.getText().equals(R.string.end_date) || courseName.equals("") || instructorName.equals("") || instructorPhoneNumber.equals("") || instructorEmail.equals("")){return;}
                else{
                    //Updating
                    if (courseToEdit != null) {
                        Course courseToSave = new Course(courseToEdit.getId(), courseName.getText().toString(), courseStatus.getSelectedItem().toString(), startDate.getText().toString(), endDate.getText().toString(), instructorName.getText().toString(), instructorPhoneNumber.getText().toString(), instructorEmail.getText().toString(), courseToEdit.getTermId());
                        repository.update(courseToSave);
                        courseToEdit = null;
                    } else {
                        Course courseToSave = new Course(0, courseName.getText().toString(), courseStatus.getSelectedItem().toString(), startDate.getText().toString(), endDate.getText().toString(), instructorName.getText().toString(), instructorPhoneNumber.getText().toString(), instructorEmail.getText().toString(), termId);
                        repository.insert(courseToSave);
                    }


                    Intent intent = new Intent(NewCourse.this, TermDetails.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void updateDate(String startOrEnd){
        if(startOrEnd.equals("start")){
            Button startDate = findViewById(R.id.startDate);
            startDate.setText(sdf.format(c.getTime()).toString());
        } else{
            Button endDate = findViewById(R.id.endDate);
            endDate.setText(sdf.format(c.getTime()).toString());
        }
    }
}