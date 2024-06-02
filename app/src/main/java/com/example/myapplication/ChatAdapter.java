package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.myapplication.ChatMessage;
import com.example.myapplication.R;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<ChatMessage> mMessages;

    public ChatAdapter() {
        mMessages = new ArrayList<>();
    }

    public void addMessage(ChatMessage message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }

    public void clearMessages() {
        mMessages.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = mMessages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvMessage;
        private TextView mTvSender;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvMessage = itemView.findViewById(R.id.tv_message);
            mTvSender = itemView.findViewById(R.id.tv_sender);
        }

        void bind(ChatMessage message) {
            mTvMessage.setText(message.getText());
            mTvSender.setText(message.getSender());
        }
    }
}
