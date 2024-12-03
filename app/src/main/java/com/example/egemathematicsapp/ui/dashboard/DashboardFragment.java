package com.example.egemathematicsapp.ui.dashboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.egemathematicsapp.databinding.FragmentDashboardBinding;
import com.example.egemathematicsapp.task_activity2;

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

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
//            RequestBody formBody = new FormBody.Builder()
//                    .add("mail", loginEditText.getText().toString())
//                    .add("password", passEditText.getText().toString())
//                    .build();

            JSONObject json = new JSONObject();
            try {
                json.put("mail", "da");
                json.put("password", "xd");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            String url = "https://mp460zr5-8000.euw.devtunnels.ms/tasks/1";

            RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));

            Request request = builder.url(String.format(url)).post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());
                Log.i("xd", object.toString());
                if (object.has("detail")) {
                    Toast.makeText(getActivity().getApplicationContext(),"Что-то пошло не так" , Toast.LENGTH_SHORT).show();
                    return null;
                }
                else if (object.has("id")) {
                    String name_task = object.getString("name");
                    String text_task =object.getString("text");
                    String answer_task =object.getString("answer");
                    Intent intent = new Intent(getActivity().getApplicationContext(), task_activity2.class);
                    intent.putExtra("name_task",name_task);
                    intent.putExtra("text_task",text_task);
                    intent.putExtra("answer_task",answer_task);
                    startActivity(intent);
                } else {
                    return null;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> array) {
            super.onPostExecute(array);
        }
    }
}