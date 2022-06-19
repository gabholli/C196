package com.example.c196.Helper;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.c196.Adapter.CourseAdapterToDetail;
import com.example.c196.Adapter.TermAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.UI.CourseList;
import com.example.c196.UI.TermList;

import java.util.Comparator;
import java.util.List;

public class HelperMethods {

    public static void addOrDeleteDateFromTermList(Repository repository, Context context) {
        List<Terms> allTerms = repository.getAllTerms();
        allTerms.sort(Comparator.comparing(Terms::getTermTitle));
        TermList.termListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        final TermAdapter termAdapter = new TermAdapter(context);
        TermList.termListRecyclerView.setAdapter(termAdapter);
        termAdapter.setTerms(allTerms);
    }


    public static void addOrDeleteDataFromCourseList(Repository repository, Context context) {
        List<Courses> allCourses = repository.getAllCourses();
        allCourses.sort(Comparator.comparing(Courses::getCourseTitle));
        CourseList.courseListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        final CourseAdapterToDetail courseAdapter = new CourseAdapterToDetail(context);
        CourseList.courseListRecyclerView.setAdapter(courseAdapter);
        courseAdapter.setCourses(allCourses);
    }
}
