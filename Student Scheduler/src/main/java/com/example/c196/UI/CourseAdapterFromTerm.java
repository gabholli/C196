package com.example.c196.UI;

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

import java.util.List;

public class CourseAdapterFromTerm extends RecyclerView.Adapter<CourseAdapterFromTerm.CourseViewHolder> {



    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseListItemTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Courses current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseList.class);
                    intent.putExtra("id", current.getCourseId());
                    intent.putExtra("name", current.getCourseTitle());
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapterFromTerm(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_detail_course_list_row, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Courses current = mCourses.get(position);
            String title = current.getCourseTitle();
            holder.courseItemView.setText(title);
        }
        else {
            holder.courseItemView.setText("No course");
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
