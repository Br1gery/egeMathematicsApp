package com.example.egemathematicsapp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.egemathematicsapp.MainActivity;
import com.example.egemathematicsapp.MyApplication;
import com.example.egemathematicsapp.databinding.FragmentDashboardBinding;
import com.example.egemathematicsapp.login_activity;
import com.example.egemathematicsapp.task_activity2;
import com.example.egemathematicsapp.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardFragment extends Fragment {

    private Button logOutBtn;
    private TextView userNameText;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String userName = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("userName");

        userNameText = binding.emailTextEdit;
        logOutBtn = binding.logOutBtn;
//        String token = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("token");

        userNameText.setText(userName);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), login_activity.class);
                intent.putExtra("exit", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}