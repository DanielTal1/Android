package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Message;

import java.util.List;


public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMessage;
        private final TextView tvDate;

        private MessageViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 0;

    private final LayoutInflater mInflater;
    private List<Message> messages;

    public MessagesListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            View itemView = mInflater.inflate(R.layout.message_item_left, parent, false);
            return new MessageViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            View itemView = mInflater.inflate(R.layout.message_item_right, parent, false);
            return new MessageViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.tvMessage.setText(current.getContent());
            holder.tvDate.setText(current.getCreated());
        }
    }

    public void setMessages(List<Message> m) {
        messages = m;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.isSent()) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    public List<Message> getMessages() { return messages; }
}
