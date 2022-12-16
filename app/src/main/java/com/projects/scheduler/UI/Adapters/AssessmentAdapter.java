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
import com.projects.scheduler.UI.AssessmentDetails;
import com.projects.scheduler.entities.Assessment;


import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{


    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        TextView itemName = itemView.findViewById(R.id.itemName);
        public AssessmentViewHolder(View view){
            super(view);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Assessment current = assessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    //Send over data
                    AssessmentDetails.currentAssessment = current;
                    context.startActivity(intent);
                }

            });
        }



    }

    private List<Assessment> assessments;
    private final Context context;
    private final LayoutInflater inflater;

    public AssessmentAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(assessments != null){
            Assessment current = assessments.get(position);
            String name = current.getTitle();
            holder.itemName.setText(name);
        }
        else{
            holder.itemName.setText("No Terms Added. Click the + Button to Create One.");
        }
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments){
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    public List<Assessment> getAssessments(){
        return this.assessments;
    }
}
