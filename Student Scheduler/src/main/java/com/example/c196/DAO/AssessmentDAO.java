package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.Entity.Assessments;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessments assessment);

    @Update
    void update(Assessments assessment);

    @Delete
    void delete(Assessments assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    List<Assessments> getAllAssessments();
}
