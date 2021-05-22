package com.mad1.myapplication.views_candidate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mad1.myapplication.R;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Candidate;
import com.mad1.myapplication.validation_utility.ValidationUtility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CandidateListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandidateListFragment extends Fragment {
    private ImageView update_delete_candidate_image_view;
    private Button update_delete_take_candidate_image_bt,
            update_candidate_button,
            delete_candidate_button,
            cadidate_back_to_candidates_button;
    private EditText update_delete_candidate_first_name_et,
            update_delete_candidate_second_name_et,
            update_delete_candidate_phone_et;
    int candidate_id = -1;
    ArrayList<Candidate> getSingleCandidateData = null;

    public CandidateListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandidateListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CandidateListFragment newInstance(String param1, String param2) {
        CandidateListFragment fragment = new CandidateListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candidate_profile_1, container, false);
        update_delete_candidate_image_view = view.findViewById(R.id.update_delete_candidate_image_view);
        update_delete_take_candidate_image_bt = view.findViewById(R.id.update_delete_take_candidate_image_bt);
        update_candidate_button = view.findViewById(R.id.update_candidate_button);
        delete_candidate_button = view.findViewById(R.id.delete_candidate_button);
        cadidate_back_to_candidates_button = view.findViewById(R.id.cadidate_back_to_candidates_button);
        update_delete_candidate_first_name_et = view.findViewById(R.id.update_delete_candidate_first_name_et);
        update_delete_candidate_second_name_et = view.findViewById(R.id.update_delete_candidate_second_name_et);
        update_delete_candidate_phone_et = view.findViewById(R.id. update_delete_candidate_phone_et);
        cadidate_back_to_candidates_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CandidateListFragment.this).navigate(R.id.action_candidateListFragment_to_SecondFragment);
            }
        });
        // get arguments
        if(getArguments() != null){
            CandidateListFragmentArgs args = CandidateListFragmentArgs.fromBundle(getArguments());
            candidate_id = args.getCandidateId();
            System.out.println("CandidateListFragment received argument candidate id: " + candidate_id);
        }
        // get candidate data
        getSingleCandidateData(candidate_id);
        // camera request permission
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},0);
        }
        update_delete_take_candidate_image_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        update_candidate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateCandidate() == 1){
                    Toast.makeText(getContext(), "Candidate Data Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Error On Update Candidade", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_candidate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteCandidae() == 1){
                    Toast.makeText(getContext(), "Candidate Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Error Occured On Deleting Candidae", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private long deleteCandidae(){
        DatabaseUtility util = new DatabaseUtility();
        util.setDataBaseHelper(getContext());
        return util.deleteCandidate(candidate_id);
    }

    private long updateCandidate(){
        ValidationUtility validationUtility = new ValidationUtility();
        System.out.println("Candidate Name Validation" + validationUtility.word_input(update_delete_candidate_first_name_et.getText().toString()));
        if(!validationUtility.word_input(update_delete_candidate_first_name_et.getText().toString())||
                !validationUtility.word_input(update_delete_candidate_second_name_et.getText().toString())||
                !validationUtility.phone_test(update_delete_candidate_phone_et.getText().toString())) {
            return -1;
        }
        else {
            // pass context to utility
            DatabaseUtility util = new DatabaseUtility();
            util.setDataBaseHelper(getContext());
            // get and compress image
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) update_delete_candidate_image_view.getDrawable());
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageInByte = stream.toByteArray();

            return util.updateCandidate(candidate_id,
                    update_delete_candidate_first_name_et.getText().toString(),
                    update_delete_candidate_second_name_et.getText().toString(),
                    update_delete_candidate_phone_et.getText().toString(),
                    imageInByte);
        }
    }

    private void getSingleCandidateData(int candidate_id){
        DatabaseUtility util = new DatabaseUtility();
        util.setDataBaseHelper(getContext());
        getSingleCandidateData = util.getSingleCandidateData(candidate_id);
        if(getSingleCandidateData == null){
            Toast.makeText(getContext(), "Error On Getting Candidate Data", Toast.LENGTH_LONG).show();
        }
        else{
            for(Candidate c: getSingleCandidateData){
                update_delete_candidate_image_view.setImageBitmap(Bitmap.createScaledBitmap(
                        c.getCandidate_image(),640,620,false
                ));
                update_delete_candidate_first_name_et.setText(c.getFirst_name());
                update_delete_candidate_second_name_et.setText(c.getSecond_name());
                update_delete_candidate_phone_et.setText(c.getPhone());
            }
        }
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }
    // to take picture and put it into image view
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            update_delete_candidate_image_view.setImageBitmap(bitmap);
        }
    }
}