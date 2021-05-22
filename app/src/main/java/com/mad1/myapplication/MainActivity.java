package com.mad1.myapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements SecondFragment.OnFragmentInteractionListener {

    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    private static final int PERMISSION_TO_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get permissions to receive SMS
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
        }

        // get permissions to SEND SMS
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_TO_SEND_SMS);
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.voter_functionality) {
//            PendingIntent pendingIntent = new NavDeepLinkBuilder(this.getApplicationContext())
//                    .setGraph(R.navigation.nav_graph)
//                    .setDestination(R.id.SecondFragment)
//                    .createPendingIntent();
//
//            try {
//                pendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        if(id == R.id.candidate_functionality){
//            PendingIntent pendingIntent = new NavDeepLinkBuilder(this.getApplicationContext())
//                    .setGraph(R.navigation.nav_graph)
//                    .setDestination(R.id.action_SecondFragment_to_candidateAddFragment)
//                    .createPendingIntent();
//
//            try {
//                pendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        if(id == R.id.admin_functionality){
//            PendingIntent pendingIntent = new NavDeepLinkBuilder(this.getApplicationContext())
//                    .setGraph(R.navigation.nav_graph)
//                    .setDestination(R.id.action_SecondFragment_to_adminListFragment)
//                    .createPendingIntent();
//
//            try {
//                pendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        if(id == R.id.support){
//
//        }
//        if(id == R.id.start_voting){
//            PendingIntent pendingIntent = new NavDeepLinkBuilder(this.getApplicationContext())
//                    .setGraph(R.navigation.nav_graph)
//                    .setDestination(R.id.action_SecondFragment_to_sendVotingStartSmsToVotersFragment)
//                    .createPendingIntent();
//
//            try {
//                pendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}