package com.example.myapplication.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvContact;
        private final ImageView ivPic;
        private ContactViewHolder(View itemView) {
            super(itemView);
            tvContact = itemView.findViewById(R.id.tvContact);
            ivPic = itemView.findViewById(R.id.ivPic);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;
    private ItemClickListener mItemListener;

    public ContactsListAdapter(Context context, ItemClickListener itemClickListener) {
        mInflater = LayoutInflater.from(context);
        mItemListener = itemClickListener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvContact.setText(current.getContact());
            holder.ivPic.setImageResource(R.drawable.cat);

            holder.itemView.setOnClickListener(view -> {
                mItemListener.onItemClick(contacts.get(position));
            });
        }
    }

    public void setContacts(List<Contact> s) {
        contacts = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        else return 0;
    }

    public interface ItemClickListener {
        void onItemClick(Contact contact);
    }

    public List<Contact> getContacts() { return contacts; }
}
