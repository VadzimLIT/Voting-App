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
import com.mad1.myapplication.SecondFragment;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.validation_utility.ValidationUtility;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CandidateAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandidateAddFragment extends Fragment {

    EditText
            first_name_et,
            second_name_et, phone_et;
    Button
            add_button,
            back_to_list_button,
            take_picture_bt,
            cadidate_add_back_to_list_button;
    ImageView candidate_image_view;
    Bitmap thumbnail;

    public CandidateAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandidateAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CandidateAddFragment newInstance(String param1, String param2) {
        CandidateAddFragment fragment = new CandidateAddFragment();
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
        View view = inflater.inflate(R.layout.fragment_candidate_add, container, false);
        first_name_et = view.findViewById(R.id.candidate_add_first_name_et);
        second_name_et = view.findViewById(R.id.candidate_add_second_name_et);
        phone_et = view.findViewById(R.id.candidate_add_phone_et);
        // initialize image view
        candidate_image_view = view.findViewById(R.id.add_candidate_image_view);
        // take picture button initialization
        take_picture_bt = view.findViewById(R.id.take_candidate_image_bt);
        // add cadidate button initialization
        add_button = view.findViewById(R.id.candidate_add_button);
        // back to candidate list button initialization
        back_to_list_button = view.findViewById(R.id.cadidate_add_back_to_list_button);

        // request camera runtime permission
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.
                    permission.CAMERA},0);
        }
        take_picture_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        // add candidate onclick listener
        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (first_name_et.getText().toString().equals("") ||
                        second_name_et.getText().toString().equals("") ||
                        phone_et.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "All Fields Are Mandatory",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (isValidationPassed()) {
                        if(candidate_image_view.getDrawable() != null) {
                            System.out.println();
                            BitmapDrawable bitmapDrawable_or_image = ((BitmapDrawable)
                                    candidate_image_view.getDrawable());
                            Bitmap bitmap = bitmapDrawable_or_image.getBitmap();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] imageInByte = stream.toByteArray();

                            if (addCandidate(first_name_et.getText().toString(),
                                    second_name_et.getText().toString(),
                                    phone_et.getText().toString(), imageInByte) != -1) {
                                Toast.makeText(getContext(), "Candidate Added Successfully",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Error Adding Candidate",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(), "Candidate Image Is Empty",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        // Validation Failed
                    }
                }
            }
        });

        back_to_list_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CandidateAddFragment.this).
                        navigate(R.id.action_candidateAddFragment_to_SecondFragment);
            }
        });
        return view;
    }

    private Boolean isValidationPassed(){
        ValidationUtility validationUtility = new ValidationUtility();
       if(!validationUtility.word_input(first_name_et.getText().toString())){
           Toast.makeText(getContext(), "First Name Validation Failed",
                   Toast.LENGTH_LONG).show();
           return false;
       }
       if(!validationUtility.word_input(second_name_et.getText().toString())){
           Toast.makeText(getContext(), "Second Name Validation Failed",
                   Toast.LENGTH_LONG).show();
           return false;
       }
       if(!validationUtility.phone_test(phone_et.getText().toString())){
           Toast.makeText(getContext(), "Phone Number Validation Failed",
                   Toast.LENGTH_LONG).show();
           return false;
       }
       return true;
    }

    private long addCandidate(String name, String surname, String moble, byte[] imageInByte){
        DatabaseUtility du = new DatabaseUtility();
        du.setDataBaseHelper(getContext());
        return du.addCandidate(name, surname, moble, imageInByte);
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(requestCode == 100){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                candidate_image_view.setImageBitmap(bitmap);
            }
        }
    }
}