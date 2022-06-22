package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;


public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Repository repo = new Repository(getApplication());
    }

    public void enterTermsList(View view) {
        Intent intent = new Intent(HomeScreen.this, TermList.class);
        startActivity(intent);
    }

    public void enterCourseList(View view) {
        Intent intent = new Intent(HomeScreen.this, CourseList.class);
        startActivity(intent);
    }

    public void enterAssessmentsList(View view) {
        Intent intent = new Intent(HomeScreen.this, AssessmentList.class);
        startActivity(intent);
    }
}