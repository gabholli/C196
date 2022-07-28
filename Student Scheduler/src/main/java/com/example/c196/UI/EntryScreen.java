package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

public class EntryScreen extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_screen);

    }

    public void enterHere(View view) {
        Intent intent = new Intent(EntryScreen.this, HomeScreen.class);
        startActivity(intent);
    }

    public static boolean checkForEmptySpaces(String value) {
        return value.length() > 0 && value.trim().matches("") || value.isEmpty();
    }

}