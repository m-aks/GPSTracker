package com.example.gpstracker.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.gpstracker.App;
import com.example.gpstracker.R;
import com.example.gpstracker.database.asynctask.GetActiveUserAsyncTask;
import com.example.gpstracker.database.entity.UserEntity;
import com.example.gpstracker.databinding.ActivityLoginBinding;
import com.example.gpstracker.viewModel.LoginViewModel;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new LoginViewModel();
        binding.setViewModel(viewModel);
        if (getIntent().getBooleanExtra("check", true)) {
            viewModel.check(this);
        }
    }

    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void startRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}