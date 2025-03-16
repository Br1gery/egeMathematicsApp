package com.example.egemathematicsapp.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardFragment extends Fragment {

    private Button logOutBtn;
    private TextView userNameText;
    private TextView tasksSolvedText;
    private Button upodateTaskBrn;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String userName = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("userName");

        Log.i("UsernameProfile",userName);

        userNameText = binding.emailTextEdit;
        logOutBtn = binding.logOutBtn;
//        String token = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("token");

        upodateTaskBrn = binding.updateTasksBtn;

        userNameText.setText(userName);
        tasksSolvedText = binding.tasksSolvedText;

        DashboardFragment.OkHttpHandler handler = new DashboardFragment.OkHttpHandler();
        handler.execute();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("localStorage", Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "empty");
                editor.putString("userName", "empty");
                editor.apply();

                Intent intent = new Intent(requireActivity().getApplicationContext(), login_activity.class);
                intent.putExtra("exit", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        upodateTaskBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardFragment.OkHttpHandler handler = new DashboardFragment.OkHttpHandler();
                handler.execute();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class OkHttpHandler extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Request.Builder builder = new Request.Builder();
            String mail = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("userName");

            JSONObject json = new JSONObject();
            try {
                json.put("mail", mail);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));

            String url = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("url") + "user/getTasksSolved";

            String token = ((MyApplication) getActivity().getApplicationContext()).getSomeVariable("token");
            Request request = builder.url(String.format(url)).post(formBody).addHeader("Authorization","Bearer " + token)
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());

                if (object.has("tasks")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String tasks = null;
                            try {
                                tasks = object.getString("tasks");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            tasksSolvedText.setText(tasks);
                        }
                    });
                } else {
                    return null;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}