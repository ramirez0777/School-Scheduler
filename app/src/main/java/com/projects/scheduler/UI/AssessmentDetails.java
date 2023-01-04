package com.projects.scheduler.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.scheduler.R;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Assessment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentDetails extends AppCompatActivity {

    public static Assessment currentAssessment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);


        //Set Details
        TextView title = findViewById(R.id.title);
        TextView startDate = findViewById(R.id.startDate);
        TextView endDate = findViewById(R.id.endDate);
        TextView type = findViewById(R.id.type);

        title.setText("Assessment Title: " + currentAssessment.getTitle());
        type.setText("Type: " + currentAssessment.getType());
        startDate.setText("Start Date: " + currentAssessment.getStartDate());
        endDate.setText("End Date: " + currentAssessment.getEndDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_assessment_details, menu);
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
                intent = new Intent(AssessmentDetails.this, NewAssessment.class);
                NewAssessment.assessmentToEdit = currentAssessment;
                NewAssessment.courseId = currentAssessment.getCourseId();
                startActivity(intent);
                break;

            case R.id.deleteAssessment:
                intent = new Intent(AssessmentDetails.this, CourseDetails.class);
                repository.delete(currentAssessment);
                startActivity(intent);
                break;
            case R.id.notifyStart:
                dateString = currentAssessment.getStartDate();
                Date myStartDate = null;
                try{
                    myStartDate = sdf.parse(dateString);
                } catch (ParseException e){e.printStackTrace();}
                trigger = myStartDate.getTime();
                intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Assessment: " + currentAssessment.getTitle() + " is today. " + currentAssessment.getStartDate());
                PendingIntent startSender = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, intent, 0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP, trigger, startSender);
                break;
            case R.id.notifyEnd:
                dateString = currentAssessment.getStartDate();
                Date myEndDate = null;
                try{
                    myEndDate = sdf.parse(dateString);
                } catch (ParseException e){e.printStackTrace();}
                trigger = myEndDate.getTime();
                intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Term: " + currentAssessment.getTitle() + " ends today. " + currentAssessment.getEndDate());
                PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetails.this, MainActivity.numAlert++, intent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP, trigger, endSender);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
