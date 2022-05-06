package com.example.gpstracker.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.gpstracker.R;
import com.example.gpstracker.databinding.ActivityRegisterBinding;
import com.example.gpstracker.viewModel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new RegisterViewModel();
        binding.setViewModel(viewModel);
    }

    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}