package com.tanka.auctionapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tanka.auctionapp.R;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        setTitle("Admin Dashboard");
    }
}
