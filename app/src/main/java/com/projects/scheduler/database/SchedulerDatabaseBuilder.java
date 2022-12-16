package com.projects.scheduler.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.projects.scheduler.dao.AssessmentDAO;
import com.projects.scheduler.dao.CourseDAO;
import com.projects.scheduler.dao.NoteDAO;
import com.projects.scheduler.dao.TermDAO;
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Note;
import com.projects.scheduler.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version=4)
public abstract class SchedulerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract NoteDAO noteDAO();

    private static volatile SchedulerDatabaseBuilder INSTANCE;

    static SchedulerDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SchedulerDatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerDatabaseBuilder.class, "SchedulerDatabase.db").fallbackToDestructiveMigration().build();
                }
            }
        }

        return INSTANCE;
    }

}
