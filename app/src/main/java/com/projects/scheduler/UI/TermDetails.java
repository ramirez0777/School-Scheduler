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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.UI.Adapters.CourseAdapter;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TermDetails extends AppCompatActivity {

    public static Term currentTerm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);


        //Set Details
        TextView header = findViewById(R.id.header_title);
        TextView termName = findViewById(R.id.name);
        TextView startDate = findViewById(R.id.startDate);
        TextView endDate = findViewById(R.id.endDate);

        termName.setText("Term Name: " + currentTerm.getTermName());
        startDate.setText("Start Date: " + currentTerm.getStartDate());
        endDate.setText("End Date: " + currentTerm.getEndDate());

        //Set Recycler View
        Repository repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
        List<Course> allCourses = repository.getCoursesForTerm(currentTerm.getId());

        courseAdapter.setCourses(allCourses);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAssessment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, NewCourse.class);
                NewCourse.termId = currentTerm.getId();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term_details, menu);
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
                intent = new Intent(TermDetails.this, NewTerm.class);
                NewTerm.termToEdit = currentTerm;
                startActivity(intent);
                break;

            case R.id.deleteAssessment:
                //Can't delete a term that has
                if(!repository.getCoursesForTerm(currentTerm.getId()).isEmpty()){
                    Toast.makeText(TermDetails.this, R.string.term_deletion_error, Toast.LENGTH_LONG).show();
                    return false;
                }
                intent = new Intent(TermDetails.this, MainActivity.class);
                repository.delete(currentTerm);
                startActivity(intent);
                break;
            case R.id.notifyStart:
                dateString = currentTerm.getStartDate();
                Date myStartDate = null;
                try{
                    myStartDate = sdf.parse(dateString);
                } catch (ParseException e){e.printStackTrace();}
                trigger = myStartDate.getTime();
                intent = new Intent(TermDetails.this, MyReceiver.class);
                intent.putExtra("key", "Term: " + currentTerm.getTermName() + " starts today. " + currentTerm.getStartDate());
                PendingIntent startSender = PendingIntent.getBroadcast(TermDetails.this, MainActivity.numAlert++, intent, 0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, trigger, startSender);
                break;
            case R.id.notifyEnd:
                dateString = currentTerm.getStartDate();
                Date myEndDate = null;
                try{
                    myEndDate = sdf.parse(dateString);
                } catch (ParseException e){e.printStackTrace();}
                trigger = myEndDate.getTime();
                intent = new Intent(TermDetails.this, MyReceiver.class);
                intent.putExtra("key", "Term: " + currentTerm.getTermName() + " ends today. " + currentTerm.getEndDate());
                PendingIntent endSender = PendingIntent.getBroadcast(TermDetails.this, MainActivity.numAlert++, intent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, trigger, endSender);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
