package com.mad1.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad1.myapplication.R;
import com.mad1.myapplication.utility.Voter;
import com.mad1.myapplication.validation_utility.ValidationUtility;

import java.util.List;

public class VoterListAdapter extends RecyclerView.Adapter<VoterListAdapter.ViewHolder>{

    private Context context;
    private List<Voter> voterList;

    public VoterListAdapter(Context context, List<Voter> voters) {
        this.context = context;
        this.voterList = voters;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView voter_first_name,
                voter_second_name,
                voter_phone,
                voter_class_code;

        public ViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            voter_first_name = itemView.findViewById(R.id.voter_list_first_name_tv);
            voter_second_name = itemView.findViewById(R.id.voter_list_second_name_and_class_tv);
            voter_phone = itemView.findViewById(R.id.voter_list_phone_tv);
            voter_class_code = itemView.findViewById(R.id.voter_list_class_code_tv);

            // set custom onClickListener
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OnClickListener voterOnClickListener;
    public interface  OnClickListener{
        void onItemClick(int position);

    }

    public void setVoterListOnClickListener(OnClickListener listener){
        voterOnClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_voter_list_custom_layout,parent,false);
        return new ViewHolder(view, voterOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.voter_first_name.setText(voterList.get(position).getVoter_first_name());
        holder.voter_second_name.setText(voterList.get(position).getVoter_second_name());
        ValidationUtility validationUtility = new ValidationUtility();
        if(!validationUtility.phone_test(voterList.get(position).getVoter_phone())){
            holder.voter_phone.setText(voterList.get(position).getVoter_phone());
        }
        else{
            holder.voter_phone.setText(voterList.get(position).getVoter_phone());
        }
        holder.voter_class_code.setText(voterList.get(position).getClass_code());
    }

    @Override
    public int getItemCount() {
        if(voterList != null){
            return voterList.size();
        }
        return 0;
    }
}
