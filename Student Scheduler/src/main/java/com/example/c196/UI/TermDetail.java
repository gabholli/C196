package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class TermDetail extends AppCompatActivity {

    int termId;
    String title;
    String startDate;
    String endDate;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    EditText editTermId;

    Repository repository;
    DatePickerDialog.OnDateSetListener startPickerDate;
    DatePickerDialog.OnDateSetListener endPickerDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    Terms currentTerm;
    int numCourses;

    public TermDetail() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.titleEditText);
        editTitle.setText(title);
        startDate = getIntent().getStringExtra("start");
        editStartDate = findViewById(R.id.startEditText);
        editStartDate.setText(startDate);
        endDate = getIntent().getStringExtra("end");
        editEndDate = findViewById(R.id.endEditText);
        editEndDate.setText(endDate);
        termId = getIntent().getIntExtra("id", -1);
        editTermId = findViewById(R.id.termIdEditText);
        editTermId.setInputType(0);
        editTermId.setText(Integer.toString(termId));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        final CourseAdapterFromTerm courseAdapter = new CourseAdapterFromTerm(this);
        recyclerView.setAdapter(courseAdapter);
        List<Courses> filteredCourses = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermId() == termId) {
                filteredCourses.add(c);
            }
        }
        filteredCourses.sort(Comparator.comparing(Courses::getCourseTitle));
        courseAdapter.setCourses(filteredCourses);


        startPickerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStartDate.getText().toString();
                try {
                    myCalendarStart.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, startPickerDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endPickerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEndDate.getText().toString();
                try {
                    myCalendarEnd.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, endPickerDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));

    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.termRefresh:
                RecyclerView recyclerView = findViewById(R.id.termDetailRecyclerView);
                repository = new Repository(getApplication());
                final CourseAdapterFromTerm courseAdapter = new CourseAdapterFromTerm(this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(courseAdapter);
                List<Courses> filteredCourses = new ArrayList<>();
                for (Courses c : repository.getAllCourses()) {
                    if (c.getTermId() == termId) filteredCourses.add(c);
                }
                filteredCourses.sort(Comparator.comparing(Courses::getCourseTitle));
                courseAdapter.setCourses(filteredCourses);
                return true;
            case R.id.termSave:
                Terms term;

                if (editTitle.getText().toString().isEmpty() || editStartDate.getText().toString().isEmpty()
                        || editEndDate.getText().toString().isEmpty()) {
                    Toast.makeText(TermDetail.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                } else if (repository.getAllTerms().isEmpty()) {
                    term = new Terms(1, editTitle.getText().toString(), editStartDate.getText().toString(),
                            editEndDate.getText().toString());
                    repository.insert(term);
                    Toast.makeText(TermDetail.this, "Term saved", Toast.LENGTH_LONG).show();
                } else if (termId == -1) {
                    int newId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermId() + 1;
                    term = new Terms(newId, editTitle.getText().toString(), editStartDate.getText().toString(),
                            editEndDate.getText().toString());
                    repository.insert(term);
                    Toast.makeText(TermDetail.this, "Term saved", Toast.LENGTH_LONG).show();
                } else {
                    term = new Terms(termId, editTitle.getText().toString(), editStartDate.getText().toString(),
                            editEndDate.getText().toString());
                    repository.update(term);
                    Toast.makeText(TermDetail.this, "Term saved", Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.termDelete:
                for (Terms t : repository.getAllTerms()) {
                    if (t.getTermId() == termId) currentTerm = t;
                }

                numCourses = 0;
                for (Courses c : repository.getAllCourses()) {
                    if (c.getTermId() == termId) ++numCourses;
                }

                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetail.this, currentTerm.getTermTitle() +
                            " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetail.this, "Can't delete a term with associated courses",
                            Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return true;
    }
}