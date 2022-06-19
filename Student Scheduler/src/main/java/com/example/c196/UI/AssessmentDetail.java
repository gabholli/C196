package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196.R;

public class AssessmentDetail extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
    }
}