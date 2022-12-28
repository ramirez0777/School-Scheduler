package com.projects.scheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.UI.Adapters.AssessmentAdapter;
import com.projects.scheduler.UI.Adapters.NoteAdapter;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Note;

import java.util.List;

public class CourseDetails extends AppCompatActivity {

    public static Course currentCourse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);


        //Set Details
        TextView termName = findViewById(R.id.name);
        TextView startDate = findViewById(R.id.startDate);
        TextView endDate = findViewById(R.id.endDate);
        TextView status = findViewById(R.id.status);
        TextView instructorName = findViewById(R.id.instructorName);
        TextView instructorPhone = findViewById(R.id.instructorPhone);
        TextView instructorEmail = findViewById(R.id.instructorEmail);

        termName.setText("Term Name: " + currentCourse.getName());
        startDate.setText("Start Date: " + currentCourse.getStartDate());
        status.setText("Status: " + currentCourse.getStatus());
        endDate.setText("End Date: " + currentCourse.getEndDate());
        instructorName.setText("Instructor: " + currentCourse.getInstructor());
        instructorPhone.setText("Phone Number: " + currentCourse.getInstructorPhoneNumber());
        instructorEmail.setText("Email: " + currentCourse.getInstructorEmail());

        //Set Recycler View for Assessments and notes
        Repository repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        final NoteAdapter noteAdapter = new NoteAdapter(this);
        RecyclerView assessmentsRecyclerView = findViewById(R.id.assessmentsRecyclerView);
        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        List<Assessment> allAssessments = repository.getAllAssessmentsForCourse(currentCourse.getId());
        List<Note> allNotes = repository.getAllNotesForCourse(currentCourse.getId());

        assessmentAdapter.setAssessments(allAssessments);
        assessmentsRecyclerView.setAdapter(assessmentAdapter);
        assessmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter.setNotes(allNotes);
        notesRecyclerView.setAdapter(noteAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //This will be for the menu when editing
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, NewCourse.class);
                NewCourse.courseToEdit = currentCourse;
                NewCourse.termId = currentCourse.getTermId();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        Repository repository = new Repository(getApplication());
        switch(item.getItemId()){
            case R.id.editCourse:
                intent = new Intent(CourseDetails.this, NewCourse.class);
                NewCourse.courseToEdit = currentCourse;
                startActivity(intent);
                break;

            case R.id.deleteCourse:
                intent = new Intent(CourseDetails.this, TermDetails.class);
                repository.delete(currentCourse);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
