package com.projects.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Term;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewTerm extends AppCompatActivity {

    public static Term termToEdit = null;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    final Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        EditText termName = findViewById(R.id.assessmentName);
        Button startDate = findViewById(R.id.startDate);
        Button endDate = findViewById(R.id.endDate);

        if(termToEdit == null){
            setTitle(R.string.new_term);
        } else{
            setTitle(R.string.edit_term);
            termName.setText(termToEdit.getTermName());
            startDate.setText(termToEdit.getStartDate());
            endDate.setText(termToEdit.getEndDate());
        }

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(NewTerm.this, startDateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
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

                new DatePickerDialog(NewTerm.this, endDateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
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
                if(termToEdit != null) {
                    Term termToSave = new Term(termToEdit.getId(), termName.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
                    if(termToSave.getTermName().isEmpty() || termToSave.getStartDate().equals(R.string.start_date) || termToSave.getEndDate().equals(R.string.end_date)){
                        Toast.makeText(NewTerm.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;}
                    repository.update(termToSave);
                    termToEdit = null;
                } else{
                    Term termToSave = new Term(0, termName.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
                    if(termToSave.getTermName().isEmpty() || termToSave.getStartDate().equals(R.string.start_date) || termToSave.getEndDate().equals(R.string.end_date)){
                        Toast.makeText(NewTerm.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;}
                    repository.insert(termToSave);
                }


                Intent intent = new Intent(NewTerm.this, MainActivity.class);
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