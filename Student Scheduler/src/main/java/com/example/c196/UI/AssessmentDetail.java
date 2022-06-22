package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

public class AssessmentDetail extends AppCompatActivity {

    Repository repository;

    int assessmentId;

    String name;
    String startDate;
    String endDate;
    String type;

    int courseId;

    EditText editName;
    EditText editStart;
    EditText editEnd;

    Spinner typeSpinner;
    Spinner courseSpinner;

    Assessments currentAssessment;

    DatePickerDialog.OnDateSetListener startAssessmentDate;
    DatePickerDialog.OnDateSetListener endAssessmentDate;
    final Calendar myCalendarAssessmentStart = Calendar.getInstance();
    final Calendar myCalendarAssessmentEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assessmentId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.assessmentNameEditText);
        editName.setText(name);
        startDate = getIntent().getStringExtra("start");
        editStart = findViewById(R.id.assessmentStartEditText);
        editStart.setText(startDate);
        endDate = getIntent().getStringExtra("end");
        editEnd = findViewById(R.id.assessmentEndEditText);
        editEnd.setText(endDate);
        type = getIntent().getStringExtra("type");
        courseId = getIntent().getIntExtra("courseId", -1);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        courseSpinner = (Spinner) findViewById(R.id.courseSpinner);

        repository = new Repository(getApplication());
        List<String> typeList = new ArrayList<>();
        typeList.add("Objective");
        typeList.add("Performance");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setSelection(typeList.indexOf(type));

        ArrayList<Integer> courseIntegerList = new ArrayList<>();
        ArrayList<Courses> courseList = new ArrayList<>(repository.getAllCourses());

        for (int i = 0; i < courseList.size(); i++) {
            courseList.sort(Comparator.comparing(Courses::getCourseId));
            Integer courseIntegerValue = courseList.get(i).getCourseId();
            courseIntegerList.add(courseIntegerValue);
        }

        ArrayAdapter<Integer> adapterCourseList = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, courseIntegerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseSpinner.setAdapter(adapterCourseList);

        courseSpinner.setSelection(courseIntegerList.indexOf(courseId));

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startAssessmentDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarAssessmentStart.set(Calendar.YEAR, year);
                myCalendarAssessmentStart.set(Calendar.MONTH, month);
                myCalendarAssessmentStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStart.getText().toString();
                try {
                    myCalendarAssessmentStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startAssessmentDate, myCalendarAssessmentStart
                        .get(Calendar.YEAR), myCalendarAssessmentStart.get(Calendar.MONTH),
                        myCalendarAssessmentStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endAssessmentDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarAssessmentEnd.set(Calendar.YEAR, year);
                myCalendarAssessmentEnd.set(Calendar.MONTH, month);
                myCalendarAssessmentEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEnd.getText().toString();
                try {
                    myCalendarAssessmentEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endAssessmentDate, myCalendarAssessmentEnd
                        .get(Calendar.YEAR), myCalendarAssessmentEnd.get(Calendar.MONTH),
                        myCalendarAssessmentEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendarAssessmentStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendarAssessmentEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.assessmentSave:
                Assessments assessment;

                if (editName.getText().toString().isEmpty() || editStart.getText().toString().isEmpty() ||
                        editEnd.getText().toString().isEmpty()) {
                    Toast.makeText(AssessmentDetail.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        if (!checkCourseDates(editStart.getText().toString(), editEnd.getText().toString())) {
                            Toast.makeText(AssessmentDetail.this, "Start date cannot be after end date", Toast.LENGTH_LONG).show();
                        } else if (repository.getAllAssessments().isEmpty()) {
                            assessment = new Assessments(1, editName.getText().toString(), editStart
                                    .getText().toString(), editEnd.getText().toString(), (String) typeSpinner.getSelectedItem(),
                                    (Integer) courseSpinner.getSelectedItem());
                            repository.insert(assessment);
                            Toast.makeText(AssessmentDetail.this, "Assessment saved. Please return " +
                                    "to assessment list and refresh", Toast.LENGTH_LONG).show();
                        } else if (assessmentId == -1) {
                            int newId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() + 1;
                            assessment = new Assessments(newId, editName.getText().toString(), editStart.getText().toString(),
                                    editEnd.getText().toString(), (String) typeSpinner.getSelectedItem(),
                                    (Integer) courseSpinner.getSelectedItem());
                            repository.insert(assessment);
                            Toast.makeText(AssessmentDetail.this, "Assessment saved. Please return " +
                                    "to assessment list and refresh", Toast.LENGTH_LONG).show();
                        } else {
                            assessment = new Assessments(assessmentId, editName.getText().toString(), editStart.getText().toString(),
                                    editEnd.getText().toString(), (String) typeSpinner.getSelectedItem(),
                                    (Integer) courseSpinner.getSelectedItem());
                            repository.update(assessment);
                            Toast.makeText(AssessmentDetail.this, "Assessment saved. Please return " +
                                    "to assessment list and refresh", Toast.LENGTH_LONG).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            case R.id.assessmentDelete:
                for (Assessments a : repository.getAllAssessments()) {
                    if (a.getAssessmentId() == assessmentId) currentAssessment = a;
                }

                if (repository.getAllAssessments().isEmpty()) {
                    Toast.makeText(AssessmentDetail.this, "Cannot delete. " +
                                    "Please save, return to assessment list" +
                                    " and refresh",
                            Toast.LENGTH_LONG).show();
                } else if (name == null) {
                    Toast.makeText(AssessmentDetail.this, "Cannot delete. Please save, return to course list" +
                                    " and refresh",
                            Toast.LENGTH_LONG).show();
                } else {
                    repository.delete(currentAssessment);
                    Toast.makeText(AssessmentDetail.this, currentAssessment.getAssessmentName() +
                            " was deleted. Please save, return to assessment list" +
                                    " and refresh",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.assessmentNotifyStart:
                if (name == null) {
                    Toast.makeText(AssessmentDetail.this, "Please save and refresh " +
                            "from assessment list", Toast.LENGTH_LONG).show();
                } else if (editStart.getText().toString().isEmpty()) {
                    Toast.makeText(AssessmentDetail.this, "Please enter starting date",
                            Toast.LENGTH_LONG).show();
                } else {
                    String dateFromScreenStart = editStart.getText().toString();
                    String myFormatStart = "MM/dd/yy";
                    SimpleDateFormat sdfStart = new SimpleDateFormat(myFormatStart, Locale.US);
                    Date myStartDate = null;
                    try {
                        myStartDate = sdfStart.parse(dateFromScreenStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long triggerStart = myStartDate.getTime();
                    Intent intentStart = new Intent(this, MyReceiver.class);
                    intentStart.putExtra("key", name + " starts on " + sdfStart.format(myStartDate));
                    PendingIntent startSender = PendingIntent.getBroadcast(AssessmentDetail.this,
                            EntryScreen.numAlert++, intentStart, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManagerStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagerStart.set(AlarmManager.RTC_WAKEUP, triggerStart, startSender);
                }
                return true;
            case R.id.assessmentNotifyEnd:
                if (name == null) {
                    Toast.makeText(AssessmentDetail.this, "Please save and refresh " +
                            "from assessment list", Toast.LENGTH_LONG).show();
                } else if (editStart.getText().toString().isEmpty()) {
                    Toast.makeText(AssessmentDetail.this, "Please enter starting date",
                            Toast.LENGTH_LONG).show();
                } else {
                    String dateFromScreenEnd = editEnd.getText().toString();
                    String myFormatEnd = "MM/dd/yy";
                    SimpleDateFormat sdfEnd = new SimpleDateFormat(myFormatEnd, Locale.US);
                    Date myEndDate = null;
                    try {
                        myEndDate = sdfEnd.parse(dateFromScreenEnd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long triggerEnd = myEndDate.getTime();
                    Intent intentEnd = new Intent(this, MyReceiver.class);
                    intentEnd.putExtra("key", name + " starts on " + sdfEnd.format(myEndDate));
                    PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetail.this,
                            EntryScreen.numAlert++, intentEnd, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, triggerEnd, endSender);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static boolean checkCourseDates(String d1, String d2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        return simpleDateFormat.parse(d1).before(simpleDateFormat.parse(d2)) ||
                simpleDateFormat.parse(d1).equals(simpleDateFormat.parse(d2));
    }
}