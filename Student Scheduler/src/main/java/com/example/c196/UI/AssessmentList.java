package com.example.c196.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Adapter.AssessmentAdapterFromCourse;
import com.example.c196.Adapter.AssessmentAdapterToDetail;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class AssessmentList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        List<Assessments> allAssessments = repository.getAllAssessments();
        allAssessments.sort(Comparator.comparing(Assessments::getAssessmentId));
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AssessmentAdapterToDetail assessmentAdapter = new AssessmentAdapterToDetail(this);
        recyclerView.setAdapter(assessmentAdapter);
        assessmentAdapter.setAssessments(allAssessments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.assessmentListRefresh:
                List<Assessments> allAssessments = repository.getAllAssessments();
                allAssessments.sort(Comparator.comparing(Assessments::getAssessmentId));
                repository = new Repository(getApplication());
                RecyclerView recyclerView = findViewById(R.id.assessmentListRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                final AssessmentAdapterToDetail assessmentAdapter = new AssessmentAdapterToDetail(this);
                recyclerView.setAdapter(assessmentAdapter);
                assessmentAdapter.setAssessments(allAssessments);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void enterAssessmentDetails(View view) {
        if (repository.getAllCourses().isEmpty() && repository.getAllAssessments().isEmpty()) {
            Toast.makeText(AssessmentList.this, "Please add a course before adding an assessment",
                    Toast.LENGTH_LONG).show();
        } else if (repository.getAllCourses().isEmpty()) {
            Toast.makeText(AssessmentList.this, "Please add a course before " +
                    "adding an assessment", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(AssessmentList.this, AssessmentDetail.class);
            startActivity(intent);
        }
    }
}