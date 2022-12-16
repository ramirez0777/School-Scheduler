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
import com.projects.scheduler.UI.TermDetails;
import com.projects.scheduler.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{


    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termName;
        public TermViewHolder(View view){
            super(view);


            termName = itemView.findViewById(R.id.itemName);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Term current = terms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    //Send over data
                    TermDetails.currentTerm = current;
                    context.startActivity(intent);
                }

            });
        }

        public TextView getTextView(){
            return termName;
        }

    }

    private List<Term> terms;
    private final Context context;
    private final LayoutInflater inflater;

    public TermAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(terms != null){
            Term current = terms.get(position);
            String name = current.getTermName();
            holder.termName.setText(name);
        }
        else{
            holder.termName.setText("No Terms Added. Click the + Button to Create One.");
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("Item count: " + terms.size());
        return terms.size();
    }

    public void setTerms(List<Term> terms){
        this.terms = terms;
        notifyDataSetChanged();
    }

    public List<Term> getTerms(){
        return this.terms;
    }
}
