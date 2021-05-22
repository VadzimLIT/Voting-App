package com.mad1.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad1.myapplication.R;
import com.mad1.myapplication.utility.Admin;

import java.util.List;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.ViewHolder> {

    private Context context;
    private List<Admin> adminList;

    public AdminListAdapter(Context context, List<Admin> adminList) {
        this.context = context;
        this.adminList = adminList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView
                admin_list_first_name_tv,
                admin_list_second_name_and_class_tv,
                admin_list_phone_tv;

        public ViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            admin_list_first_name_tv = itemView.findViewById(R.id.admin_list_first_name_tv);
            admin_list_second_name_and_class_tv = itemView.findViewById(R.id.admin_list_second_name_and_class_tv);
            admin_list_phone_tv = itemView.findViewById(R.id.admin_list_phone_tv);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OnClickListener adminOnClickListener;
    public interface OnClickListener{
        void OnItemClick(int position);
    }

    public void setAdminOnClickListener(OnClickListener listener){
        adminOnClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.admin_list_custom_layout, parent, false);
        return new ViewHolder(view, adminOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.admin_list_first_name_tv.setText(adminList.get(position).getAdmin_first_name());
        holder.admin_list_second_name_and_class_tv.setText(adminList.get(position).getAdmin_second_name());
        holder.admin_list_phone_tv.setText(adminList.get(position).getAdmin_phone());
    }

    @Override
    public int getItemCount() {
        if(adminList != null){
            return adminList.size();
        }
        return 0;
    }




}
