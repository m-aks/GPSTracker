package com.example.gpstracker.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpstracker.App;
import com.example.gpstracker.R;
import com.example.gpstracker.database.asynctask.GetActiveUserAsyncTask;
import com.example.gpstracker.database.asynctask.UpdateUserAsyncTask;
import com.example.gpstracker.database.entity.UserEntity;
import com.example.gpstracker.databinding.ActivityMainBinding;
import com.example.gpstracker.viewModel.MainViewModel;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = bind();
        initRecycleView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setup();
    }

    private void initRecycleView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.ride_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    @NonNull
    private View bind() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainViewModel();
        binding.setViewModel(viewModel);
        binding.startTrackFab.setOnClickListener(this);
        binding.logoutFab.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_track_fab) {
            startActivity(new Intent(this, TrackActivity.class));
        } else if (v.getId() == R.id.logout_fab) {
            try {
                UserEntity user = new GetActiveUserAsyncTask().execute().get();
                user.setActive(false);
                new UpdateUserAsyncTask().execute(user);
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("check", false);
                startActivity(intent);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}