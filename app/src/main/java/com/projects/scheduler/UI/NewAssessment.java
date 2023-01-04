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
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewAssessment extends AppCompatActivity {

    public static Assessment assessmentToEdit = null;
    public static int courseId;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    final Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        EditText assessmentName = findViewById(R.id.assessmentName);
        Button startDate = findViewById(R.id.startDate);
        Button endDate = findViewById(R.id.endDate);

        Spinner type = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        if(assessmentToEdit == null){
            setTitle(R.string.new_assessment);
        } else{
            setTitle(R.string.edit_course);
            assessmentName.setText(assessmentToEdit.getTitle());
            type.setSelection(adapter.getPosition(assessmentToEdit.getType()));
            startDate.setText(assessmentToEdit.getStartDate());
            endDate.setText(assessmentToEdit.getEndDate());
        }

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(NewAssessment.this, startDateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
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

                new DatePickerDialog(NewAssessment.this, endDateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
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


                //Updating
                if(assessmentToEdit != null) {
                    Assessment assessmentToSave = new Assessment(assessmentToEdit.getId(),
                            assessmentName.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            type.getSelectedItem().toString(),
                            courseId);
                    repository.update(assessmentToSave);
                    assessmentToEdit = null;
                } else{
                    Assessment assessmentToSave = new Assessment(0,
                            assessmentName.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            type.getSelectedItem().toString(),
                            courseId);
                    repository.insert(assessmentToSave);
                }


                Intent intent = new Intent(NewAssessment.this, CourseDetails.class);
                startActivity(intent);
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