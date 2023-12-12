package com.example.your_contact.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.your_contact.ChatActivity;
import com.example.your_contact.Model.User;
import com.example.your_contact.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context mContext;
    List<User> users;

    public UserAdapter(Context mContext, List<User> users) {
        this.mContext = mContext;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.user_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.name.setText(users.get(position).getUser_name());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.userName);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("userId2", users.get(getAdapterPosition()).getId());
            intent.putExtra("name", users.get(getAdapterPosition()).getUser_name());
            mContext.startActivity(intent);
        }
    }
}
