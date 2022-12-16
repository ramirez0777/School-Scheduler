package com.projects.scheduler.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.scheduler.R;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Assessment;

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

}
