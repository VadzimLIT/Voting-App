package com.mad1.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mad1.myapplication.R;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Candidate;
import java.util.List;

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.ViewHolder> {
    private Context myContext;
    private List<Candidate> candidates;

    public CandidateListAdapter(Context myContext, List<Candidate> candidates) {
        this.myContext = myContext;
        this.candidates = candidates;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView full_name, voting_rating;
        ImageView candidate_image;

        public ViewHolder(@NonNull View itemView , OnClickListener listener) {
            super(itemView);
            full_name = itemView.findViewById(R.id.cand_name_tv);
            voting_rating = itemView.findViewById(R.id.cand_voting_rating_tv);
            candidate_image = itemView.findViewById(R.id.cand_image_id);
            itemView.setOnClickListener(new View.OnClickListener() {
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
    public OnClickListener candidatesOnClickListener;
    public interface OnClickListener{
        void onItemClick(int position);
    }
    public void setCandidatesOnClickListener(OnClickListener listener){
        candidatesOnClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myContext);
        view = inflater.inflate(R.layout.candidate_profile_custom_layout, parent, false);
        return new ViewHolder(view, candidatesOnClickListener );
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String full_name = candidates.get(position).getFirst_name()
                + " " + candidates.get(position).getSecond_name();
        holder.full_name.setText(full_name);
        //Resise bitmap
        holder.candidate_image.setImageBitmap(Bitmap.createScaledBitmap(
                candidates.get(position).getCandidate_image(), 620,620,false));
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(myContext);
        holder.voting_rating.setText(
                "Voting Rating: " + String.valueOf(utility.getVotesPerCandidate(candidates.
                        get(position).getCandidate_id())));
    }
    @Override
    public int getItemCount() {
        if(candidates != null){
            return candidates.size();
        }
        return 0;
    }

}
