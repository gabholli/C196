package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196.Adapter.TermAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.Comparator;
import java.util.List;


public class TermList extends AppCompatActivity {
    private Repository repository;

    public static RecyclerView termListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        List<Terms> allTerms = repository.getAllTerms();
        allTerms.sort(Comparator.comparing(Terms::getTermId));
        termListRecyclerView = findViewById(R.id.recyclerView10);
        termListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TermAdapter termAdapter = new TermAdapter(this);
        termListRecyclerView.setAdapter(termAdapter);
        termAdapter.setTerms(allTerms);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.termRefresh:
                repository = new Repository(getApplication());
                List<Terms> allTerms = repository.getAllTerms();
                allTerms.sort(Comparator.comparing(Terms::getTermId));
                RecyclerView recyclerView = findViewById(R.id.recyclerView10);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                final TermAdapter termAdapter = new TermAdapter(this);
                recyclerView.setAdapter(termAdapter);
                termAdapter.setTerms(allTerms);

        }
        return super.onOptionsItemSelected(item);
    }

    public void enterTermDetail(View view) {
        Intent intent = new Intent(TermList.this, TermDetail.class);
        startActivity(intent);
    }
}