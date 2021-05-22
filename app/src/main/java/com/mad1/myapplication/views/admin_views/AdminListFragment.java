package com.mad1.myapplication.views.admin_views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mad1.myapplication.R;
import com.mad1.myapplication.adapters.AdminListAdapter;
import com.mad1.myapplication.database_utility.DatabaseUtility;
import com.mad1.myapplication.utility.Admin;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminListFragment extends Fragment {

    Button to_add_admin_bt, admin_list_home_bt;
    RecyclerView recyclerView_id;
    ArrayList<Admin> adminList;
    AdminListAdapter adminListAdapter;

    public AdminListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminListFragment newInstance(String param1, String param2) {
        AdminListFragment fragment = new AdminListFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_list, container, false);
        to_add_admin_bt = view.findViewById(R.id.admin_list_to_admin_add_bt);
        admin_list_home_bt = view.findViewById(R.id.admin_list_home_bt);
        admin_list_home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminListFragment.this)
                        .navigate(R.id.action_adminListFragment_to_SecondFragment);
            }
        });
        to_add_admin_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminListFragment.this)
                        .navigate(R.id.action_adminListFragment_to_adminAddFragment);
            }
        });
        adminList = getAdminsForList();
        recyclerView_id = view.findViewById(R.id.admin_list_recycle_view);
            adminListAdapter = new AdminListAdapter(getContext(), adminList);
            recyclerView_id.setLayoutManager(new GridLayoutManager(getContext(), 1));
            recyclerView_id.setAdapter(adminListAdapter);
            adminListAdapter.setAdminOnClickListener(new AdminListAdapter.OnClickListener() {
                @Override
                public void OnItemClick(int position) {
                    AdminListFragmentDirections.ActionAdminListFragmentToAdminProfileFragment action =
                            AdminListFragmentDirections.actionAdminListFragmentToAdminProfileFragment();
                    action.setAdminId(adminList.get(position).getAdmin_id());
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);
                }
            });

        return view;
    }

    private ArrayList<Admin> getAdminsForList(){
        DatabaseUtility utility = new DatabaseUtility();
        utility.setDataBaseHelper(getContext());
        ArrayList<Admin> adminData = utility.getAdminsForList();
        if(adminData != null)
            return adminData;
        else{
            Toast.makeText(getContext(), "Error On Getting Admins", Toast.LENGTH_LONG).show();
        }
        return null;
    }
}