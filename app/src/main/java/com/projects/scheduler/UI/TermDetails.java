package com.projects.scheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.scheduler.R;
import com.projects.scheduler.UI.Adapters.CourseAdapter;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Note;
import com.projects.scheduler.entities.Term;

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
        switch(item.getItemId()){
            case R.id.editTerm:
                intent = new Intent(TermDetails.this, NewTerm.class);
                NewTerm.termToEdit = currentTerm;
                startActivity(intent);
                break;

            case R.id.deleteTerm:
                if(!repository.getCoursesForTerm(currentTerm.getId()).isEmpty()){
                    return false;
                }
                intent = new Intent(TermDetails.this, MainActivity.class);
                repository.delete(currentTerm);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
