package com.projects.scheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.scheduler.R;
import com.projects.scheduler.UI.CourseDetails;
import com.projects.scheduler.UI.TermDetails;
import com.projects.scheduler.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{


    class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView itemName = itemView.findViewById(R.id.itemName);
        public CourseViewHolder(View view){
            super(view);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Course current = courses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    //Send over data
                    CourseDetails.currentCourse = current;
                    context.startActivity(intent);
                }

            });
        }



    }

    private List<Course> courses;
    private final Context context;
    private final LayoutInflater inflater;

    public CourseAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(courses != null){
            Course current = courses.get(position);
            String name = current.getName();
            holder.itemName.setText(name);
        }
        else{
            holder.itemName.setText("No Terms Added. Click the + Button to Create One.");
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
        notifyDataSetChanged();
    }

    public List<Course> getCourses(){
        return this.courses;
    }
}
