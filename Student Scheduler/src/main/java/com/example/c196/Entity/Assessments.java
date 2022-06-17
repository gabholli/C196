package com.example.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentName;
    private String startDate;
    private String endDate;
    private String assessmentType;
    private int courseId;

    public Assessments(int assessmentId, String assessmentName, String startDate,
                       String endDate, String assessmentType, int courseId) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Assessments{" +
                "assessmentId=" + assessmentId +
                ", assessmentName='" + assessmentName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", assessmentType='" + assessmentType + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
