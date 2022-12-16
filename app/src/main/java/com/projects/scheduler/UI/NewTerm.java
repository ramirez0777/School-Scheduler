package com.projects.scheduler.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.UI.fragments.DatePickerFragment;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Term;

public class NewTerm extends AppCompatActivity {

    public static Term termToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);

        EditText termName = findViewById(R.id.termName);
        Button startDate = findViewById(R.id.startDate);
        Button endDate = findViewById(R.id.endDate);

        if(termToEdit == null){
            setTitle(R.string.new_term);
            startDate.setText("12/15/2022");
            endDate.setText("12/16/2022");
        } else{
            setTitle(R.string.edit_term);
            termName.setText(termToEdit.getTermName());
            startDate.setText(termToEdit.getStartDate());
            endDate.setText(termToEdit.getEndDate());
        }

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "startDatePicker");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        Repository repository = new Repository(getApplication());
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(termName.getText() == null || startDate.getText() == null || endDate.getText() == null){
                    System.out.println("Fill it out");
                    return;
                }

                //Updating
                if(termToEdit != null) {
                    Term termToSave = new Term(termToEdit.getId(), termName.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
                    repository.update(termToSave);
                    termToEdit = null;
                } else{
                    Term termToSave = new Term(0, termName.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
                    repository.insert(termToSave);
                }


                Intent intent = new Intent(NewTerm.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}