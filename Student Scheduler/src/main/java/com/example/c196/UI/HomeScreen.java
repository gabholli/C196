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
//        Assessments assessment = new Assessments(1,"First Assessment",
//                "02/02/22", "02/03/22", "Objective", 1);
//        repo.insert(assessment);
//        Terms term = new Terms(1, "Term 1", "06/08/22", "06/09/22");
//        repo.insert(term);
//        Terms term2 = new Terms(3, "Term 2", "05/05/05", "05/06/06");
//        repo.insert(term2);
//        Courses course = new Courses(1, "course1", "01/01/01", "02/02/02",
//                "Open", "Gary", "555-555-5555", "gary@gary.com", 1);
//        repo.insert(course);
//        Courses course2 = new Courses(2, "course2", "01/01/01", "02/02/02",
//                "Open", "Gary", "555-555-5555", "gary@gary.com", 1);
//        repo.insert(course2);
//        Courses course3 = new Courses(3, "course3", "01/01/01", "02/02/02",
//                "Open", "Gary", "555-555-5555", "gary@gary.com", 2);
//        repo.insert(course3);
//        Courses course4 = new Courses(4, "course0", "01/02/03",
//                "05/05/05", "Open", "Joe", "222-222-2222",
//                "gmail@gmail.com", 1);
//        repo.insert(course4);

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