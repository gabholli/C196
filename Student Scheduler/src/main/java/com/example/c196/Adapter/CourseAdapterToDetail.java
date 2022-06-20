package com.example.c196.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Courses;
import com.example.c196.R;
import com.example.c196.UI.CourseDetail;

import java.util.List;

public class CourseAdapterToDetail extends RecyclerView.Adapter<CourseAdapterToDetail.CourseDetailViewHolder> {



    class CourseDetailViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;
        private final TextView courseItemView2;
        private CourseDetailViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.termDetailCourseListTitleRow);
            courseItemView2 = itemView.findViewById(R.id.termDetailCourseIdRow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Courses current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("id", current.getCourseId());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("start", current.getCourseStartDate());
                    intent.putExtra("end", current.getCourseEndDate());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("name", current.getInstructorName());
                    intent.putExtra("phone", current.getInstructorPhone());
                    intent.putExtra("email", current.getInstructorEmail());
                    intent.putExtra("note", current.getCourseNote());
                    intent.putExtra("termId", current.getTermId());
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapterToDetail(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_detail_course_list_row, parent, false);
        return new CourseDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailViewHolder holder, int position) {
        if (mCourses != null) {
            Courses current = mCourses.get(position);
            String title = current.getCourseTitle();
            int courseId = current.getCourseId();
            holder.courseItemView.setText(title);
            holder.courseItemView2.setText(Integer.toString(courseId));
        }
        else {
            holder.courseItemView.setText("No Course Title");
            holder.courseItemView2.setText("No Course ID");
        }
    }

    public void setCourses(List<Courses> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mCourses.size();
    }
}