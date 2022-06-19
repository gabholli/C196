package com.example.c196.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Adapter.AssessmentAdapterFromCourse;
import com.example.c196.Adapter.CourseAdapterToDetail;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.MyReceiver;
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


public class CourseDetail extends AppCompatActivity {

    String title;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String courseNote;

    Courses currentCourse;
    int numCourses;
    int courseId;
    int termId;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editName;
    EditText editPhone;
    EditText editEmail;
    EditText editNote;

    Repository repository;

    Spinner termSpinner;
    DatePickerDialog.OnDateSetListener startCourseDate;
    DatePickerDialog.OnDateSetListener endCourseDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        courseId = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.courseTitleEditText);
        editTitle.setText(title);
        startDate = getIntent().getStringExtra("start");
        editStart = findViewById(R.id.courseStartEditText);
        editStart.setText(startDate);
        endDate = getIntent().getStringExtra("end");
        editEnd = findViewById(R.id.courseEndEditText);
        editEnd.setText(endDate);
        status = getIntent().getStringExtra("status");
        editStatus = findViewById(R.id.courseStatusEditText);
        editStatus.setText(status);
        instructorName = getIntent().getStringExtra("name");
        editName = findViewById(R.id.instructorNameEditText);
        editName.setText(instructorName);
        instructorPhone = getIntent().getStringExtra("phone");
        editPhone = findViewById(R.id.instructorPhoneEditText);
        editPhone.setText(instructorPhone);
        instructorEmail = getIntent().getStringExtra("email");
        editEmail = findViewById(R.id.instructorEmailEditText);
        editEmail.setText(instructorEmail);
        courseNote = getIntent().getStringExtra("note");
        editNote = findViewById(R.id.courseNoteEditText);
        editNote.setText(courseNote);
        courseId = getIntent().getIntExtra("id", -1);
        termId = getIntent().getIntExtra("termId", -1);


        termSpinner = (Spinner) findViewById(R.id.termSpinner);
        RecyclerView recyclerView = findViewById(R.id.associatedAssessmentsRecycler);
        repository = new Repository(getApplication());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AssessmentAdapterFromCourse assessmentListAdapter = new AssessmentAdapterFromCourse(this);
        recyclerView.setAdapter(assessmentListAdapter);
        List<Assessments> filteredAssessments = new ArrayList<>();
        filteredAssessments.sort(Comparator.comparing(Assessments::getAssessmentName));
        for (Assessments a : repository.getAllAssessments()) {
            if (a.getCourseId() == courseId) filteredAssessments.add(a);
        }
        assessmentListAdapter.setAssessments(filteredAssessments);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Integer> termsIntegerList = new ArrayList<>();
        ArrayList<Terms> termsList = new ArrayList<>(repository.getAllTerms());

        for (int i = 0; i < termsList.size(); i++) {
            termsList.sort(Comparator.comparing(Terms::getTermId));
            Integer termIntegerValue = termsList.get(i).getTermId();
            termsIntegerList.add(termIntegerValue);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, termsIntegerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(adapter);

        termSpinner.setSelection(termsIntegerList.indexOf(termId));


        startCourseDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();

            }
        };

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStart.getText().toString();
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, startCourseDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endCourseDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEnd.getText().toString();
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, endCourseDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendarStart.getTime()));

    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendarEnd.getTime()));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.courseDetailRefresh:
                repository = new Repository(getApplication());
                RecyclerView recyclerView = findViewById(R.id.associatedAssessmentsRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                final AssessmentAdapterFromCourse assessmentListAdapter = new AssessmentAdapterFromCourse(this);
                recyclerView.setAdapter(assessmentListAdapter);
                List<Assessments> filteredAssessments = new ArrayList<>();
                filteredAssessments.sort(Comparator.comparing(Assessments::getAssessmentName));

                for (Assessments a : repository.getAllAssessments()) {
                    if (a.getCourseId() == courseId) filteredAssessments.add(a);
                }
                assessmentListAdapter.setAssessments(filteredAssessments);
                return true;
            case R.id.courseSave:
                Courses course;

                try {

                    if (editTitle.getText().toString().isEmpty() || editStart.getText().toString().isEmpty() ||
                            editEnd.getText().toString().isEmpty() || editStatus.getText().toString().isEmpty() ||
                            editName.getText().toString().isEmpty() || editPhone.getText().toString().isEmpty() ||
                            editEmail.getText().toString().isEmpty()) {
                        Toast.makeText(CourseDetail.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                    } else if (!checkCourseDates(editStart.getText().toString(), editEnd.getText().toString())) {
                        Toast.makeText(CourseDetail.this, "Start date cannot be after end date", Toast.LENGTH_LONG).show();
                    } else if (repository.getAllCourses().isEmpty()) {
                        course = new Courses(1, editTitle.getText().toString(), editStart.getText().toString(),
                                editEnd.getText().toString(), editStatus.getText().toString(),
                                editName.getText().toString(), editPhone.getText().toString(),
                                editEmail.getText().toString(), editNote.getText().toString(), (Integer) termSpinner.getSelectedItem());
                        repository.insert(course);
                        Toast.makeText(CourseDetail.this, "Course saved. Please return " +
                                "to course list and refresh", Toast.LENGTH_LONG).show();
                    } else if (courseId == -1) {
                        int newId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;
                        course = new Courses(newId, editTitle.getText().toString(), editStart.getText().toString(),
                                editEnd.getText().toString(), editStatus.getText().toString(),
                                editName.getText().toString(), editPhone.getText().toString(),
                                editEmail.getText().toString(), editNote.getText().toString(), (Integer) termSpinner.getSelectedItem());
                        repository.insert(course);
                        Toast.makeText(CourseDetail.this, "Course saved. Please return " +
                                "to course list and refresh", Toast.LENGTH_LONG).show();

                    } else {
                        course = new Courses(courseId, editTitle.getText(). toString(), editStart.getText().toString(),
                                editEnd.getText().toString(), editStatus.getText().toString(),
                                editName.getText().toString(), editPhone.getText().toString(),
                                editEmail.getText().toString(), editNote.getText().toString(), (Integer) termSpinner.getSelectedItem());
                        repository.update(course);
                        Toast.makeText(CourseDetail.this, "Course saved. Please return " +
                                "to course list and refresh", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.courseDelete:
                for (Courses c : repository.getAllCourses()) {
                    if (c.getCourseId() == courseId) currentCourse = c;
                }
                if (repository.getAllCourses().isEmpty()) {
                    Toast.makeText(CourseDetail.this, "Cannot delete. Please save, return to course list" +
                                    " and refresh",
                            Toast.LENGTH_LONG).show();
                } else if (title == null) {
                    Toast.makeText(CourseDetail.this, "Cannot delete. Please save, return to course list" +
                                    " and refresh",
                            Toast.LENGTH_LONG).show();
                } else {
                    repository.delete(currentCourse);
                    Toast.makeText(CourseDetail.this, currentCourse.getCourseTitle()
                            + " was deleted. Please return to course list and refresh", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.shareNote:
                if (title == null) {
                    Toast.makeText(CourseDetail.this, "Please save, return to course list and refresh",
                            Toast.LENGTH_LONG).show();
                }
                else if (editNote.getText().toString().isEmpty()) {
                    Toast.makeText(CourseDetail.this, "Please enter a note to share",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                    sendIntent.putExtra(Intent.EXTRA_TITLE, "Note From " + editTitle.getText().toString());
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
                return true;
            case R.id.notifyStart:
                if (title == null) {
                    Toast.makeText(CourseDetail.this, "Please save and refresh " +
                            "from course list", Toast.LENGTH_LONG).show();
                } else if (editStart.getText().toString().isEmpty()) {
                    Toast.makeText(CourseDetail.this, "Please enter starting date",
                            Toast.LENGTH_LONG).show();
                } else {
                    String dateFromScreenStart = editStart.getText().toString();
                    String myFormatStart = "MM/dd/yy";
                    SimpleDateFormat sdfStart = new SimpleDateFormat(myFormatStart, Locale.US);
                    Date myStartDate = null;
                    try {
                        myStartDate = sdfStart.parse(dateFromScreenStart);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    Long triggerStart = myStartDate.getTime();
                    Intent intentStart = new Intent(this, MyReceiver.class);
                    intentStart.putExtra("key", title + " starts today");
                    PendingIntent startSender = PendingIntent.getBroadcast(CourseDetail.this,
                            EntryScreen.numAlert++, intentStart, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManagerStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagerStart.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);
                }
                return true;
            case R.id.notifyEnd:
                if (title == null) {
                    Toast.makeText(CourseDetail.this, "Please save and refresh " +
                            "from course list", Toast.LENGTH_LONG).show();
                } else if (editEnd.getText().toString().isEmpty()) {
                    Toast.makeText(CourseDetail.this, "Please enter ending date",
                            Toast.LENGTH_LONG).show();
                } else {
                    String dateFromScreenEnd = editEnd.getText().toString();
                    String myFormatEnd = "MM/dd/yy";
                    SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
                    Date myEndDate = null;
                    try {
                        myEndDate = sdfEnd.parse(dateFromScreenEnd);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    Long triggerEnd = myEndDate.getTime();
                    Intent intentEnd = new Intent(CourseDetail.this, MyReceiver.class);
                    intentEnd.putExtra("key", title + " ends today");

                    PendingIntent endSender = PendingIntent.getBroadcast(this,
                            EntryScreen.numAlert++,
                            intentEnd, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public static boolean checkCourseDates(String d1, String d2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        return simpleDateFormat.parse(d1).before(simpleDateFormat.parse(d2)) ||
                simpleDateFormat.parse(d1).equals(simpleDateFormat.parse(d2));
    }
}