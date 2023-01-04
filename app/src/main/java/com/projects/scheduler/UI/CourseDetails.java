package com.projects.scheduler.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        TextView status = findViewById(R.id.type);
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
        FloatingActionButton fabAssessment = findViewById(R.id.fabAssessment);
        FloatingActionButton fabNote = findViewById(R.id.fabNote);
        fabAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, NewAssessment.class);
                NewAssessment.courseId = currentCourse.getId();
                startActivity(intent);
            }
        });
        fabNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, NewNote.class);
                NewNote.courseId = currentCourse.getId();
                startActivity(intent);
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
        String dateString;
        Long trigger;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        switch(item.getItemId()){
            case R.id.editAssessment:
                intent = new Intent(CourseDetails.this, NewCourse.class);
                NewCourse.courseToEdit = currentCourse;
                startActivity(intent);
                break;

            case R.id.deleteAssessment:
                intent = new Intent(CourseDetails.this, TermDetails.class);
                repository.delete(currentCourse);
                startActivity(intent);
                break;

            case R.id.shareNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                List<Note> notesList = repository.getAllNotesForCourse(currentCourse.getId());
                String notes = "";
                for(int i = 0; i < notesList.size(); i++)
                {
                    notes += (i + 1) + ": " + notesList.get(i).getText() + "\n";
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
            case R.id.notifyStart:
                dateString = currentCourse.getStartDate();
                Date myStartDate = null;
                try{
                    myStartDate = sdf.parse(dateString);
                } catch (ParseException e){e.printStackTrace();}
                trigger = myStartDate.getTime();
                intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", "Course: " + currentCourse.getName() + " starts today. " + currentCourse.getStartDate());
                PendingIntent startSender = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, intent, 0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, trigger, startSender);
                break;
            case R.id.notifyEnd:
                dateString = currentCourse.getStartDate();
                Date myEndDate = null;
                try{
                    myEndDate = sdf.parse(dateString);
                } catch (ParseException e){e.printStackTrace();}
                trigger = myEndDate.getTime();
                intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", "Course: " + currentCourse.getName() + " ends today. " + currentCourse.getEndDate());
                PendingIntent endSender = PendingIntent.getBroadcast(CourseDetails.this, MainActivity.numAlert++, intent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, trigger, endSender);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
