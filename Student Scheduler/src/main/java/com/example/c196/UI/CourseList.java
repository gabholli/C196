package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.c196.Adapter.CourseAdapterToDetail;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.R;

import java.util.Comparator;
import java.util.List;


public class CourseList extends AppCompatActivity {

    public static RecyclerView courseListRecyclerView;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        List<Courses> allCourses = repository.getAllCourses();
        allCourses.sort(Comparator.comparing(Courses::getCourseTitle));
        courseListRecyclerView = findViewById(R.id.recyclerViewCourseList);
        courseListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CourseAdapterToDetail courseDetailAdapter = new CourseAdapterToDetail(this);
        courseListRecyclerView.setAdapter(courseDetailAdapter);
        courseDetailAdapter.setCourses(allCourses);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.courseListRefresh:
                repository = new Repository(getApplication());
                List<Courses> allCourses = repository.getAllCourses();
                allCourses.sort(Comparator.comparing(Courses::getCourseTitle));
                RecyclerView recyclerView = findViewById(R.id.recyclerViewCourseList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                final CourseAdapterToDetail courseAdapter = new CourseAdapterToDetail(this);
                recyclerView.setAdapter(courseAdapter);
                courseAdapter.setCourses(allCourses);
        }
        return super.onOptionsItemSelected(item);
    }

    public void enterCourseDetail(View view) {
        if (repository.getAllCourses().isEmpty() && repository.getAllTerms().isEmpty()) {
            Toast.makeText(CourseList.this, "Please add a term before adding a course", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(CourseList.this, CourseDetail.class);
            startActivity(intent);
        }
    }
}