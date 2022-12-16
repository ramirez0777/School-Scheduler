package com.projects.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projects.scheduler.R;
import com.projects.scheduler.UI.Adapters.TermAdapter;
import com.projects.scheduler.database.Repository;
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Note;
import com.projects.scheduler.entities.Term;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);

        repository  = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);

        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, NewTerm.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Repository repository = new Repository(getApplication());
        switch(item.getItemId()){
            case R.id.addSampleData:
                Term term = new Term(0, "Not a Term", "12/12/2022", "12/13/2022");
                repository.insert(term);

                Course course = new Course(0, "Not a Test Course", "Test Status", "12/12/2022", "12/13/2022", "Dr. John", "8015615623", "john@email.com", 2);
                repository.insert(course);

                return true;

            case R.id.addSecondarySampleData:
                Assessment assessment = new Assessment(0, "Not an Assessment",  "12/14/2022", "12/15/2022", "Objective", 1);
                repository.insert(assessment);

                Note note = new Note(0, "This is a note I would possibly write for a class I mean how long could it actually be that's the real question we have to ask ourselves", 1);
                repository.insert(note);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}