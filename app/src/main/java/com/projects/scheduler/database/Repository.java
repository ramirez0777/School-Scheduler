package com.projects.scheduler.database;

import android.app.Application;

import com.projects.scheduler.dao.AssessmentDAO;
import com.projects.scheduler.dao.CourseDAO;
import com.projects.scheduler.dao.NoteDAO;
import com.projects.scheduler.dao.TermDAO;
import com.projects.scheduler.entities.Assessment;
import com.projects.scheduler.entities.Course;
import com.projects.scheduler.entities.Note;
import com.projects.scheduler.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private CourseDAO courseDAO;
    private TermDAO termDAO;
    private AssessmentDAO assessmentDAO;
    private NoteDAO noteDAO;
    private List<Term> allTerms;
    private List<Course> allCourses;
    private List<Assessment> allAssessments;
    private List<Note> allNotes;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        SchedulerDatabaseBuilder db = SchedulerDatabaseBuilder.getDatabase(application);
        courseDAO = db.courseDAO();
        termDAO = db.termDAO();
        assessmentDAO = db.assessmentDAO();
        noteDAO = db.noteDAO();
    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            allTerms = termDAO.getAllTerms();
        });


        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){}

        return allTerms;
    }

    public List<Course> getCoursesForTerm(int termId){
        databaseExecutor.execute(()->{
            allCourses = courseDAO.getCoursesForTerm(termId);
        });

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}

        return allCourses;
    }

    public List<Assessment> getAllAssessmentsForCourse(int courseId){
        databaseExecutor.execute(()->{
            allAssessments = assessmentDAO.getAllAssessmentsForCourse(courseId);
        });

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){}

        return allAssessments;
    }

    public List<Note> getAllNotesForCourse(int courseId){
        databaseExecutor.execute(()->{
            allNotes = noteDAO.getAllNotesForCourse(courseId);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}

        return allNotes;
    }

    public void insert(Term term){
        databaseExecutor.execute(()->{
            termDAO.insert(term);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void insert(Course course){
        databaseExecutor.execute(()->{
            courseDAO.insert(course);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.insert(assessment);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void insert(Note note){
        databaseExecutor.execute(()->{
            noteDAO.insert(note);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            termDAO.update(term);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            courseDAO.update(course);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.update(assessment);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void update(Note note){
        databaseExecutor.execute(()->{
            noteDAO.update(note);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            termDAO.delete(term);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            courseDAO.delete(course);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            assessmentDAO.delete(assessment);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }

    public void delete(Note note){
        databaseExecutor.execute(()->{
            noteDAO.delete(note);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
    }
}
