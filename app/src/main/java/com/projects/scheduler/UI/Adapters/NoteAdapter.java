package com.projects.scheduler.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.scheduler.R;
import com.projects.scheduler.entities.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{


    class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView itemName = itemView.findViewById(R.id.itemName);
        public NoteViewHolder(View view){
            super(view);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Note current = notes.get(position);

                }

            });
        }



    }

    private List<Note> notes;
    private final Context context;
    private final LayoutInflater inflater;

    public NoteAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if(notes != null){
            Note current = notes.get(position);
            String name = current.getText();
            holder.itemName.setText(name);
        }
        else{
            holder.itemName.setText("No Terms Added. Click the + Button to Create One.");
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes(){
        return this.notes;
    }
}
