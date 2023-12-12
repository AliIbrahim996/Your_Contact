package com.example.your_contact.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.your_contact.Model.Task;
import com.example.your_contact.R;
import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks;
    Context mContext;

    public TaskAdapter(List<Task> tasks, Context mContext) {
        this.tasks = tasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.taskName.setText(tasks.get(position).getTask());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskName;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            taskName = itemView.findViewById(R.id.taskName);
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(mContext)
                    .setMessage(tasks.get(getAdapterPosition()).getContent())
                    .create().show();
        }
    }
}
