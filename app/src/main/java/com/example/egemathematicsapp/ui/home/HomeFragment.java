package com.example.egemathematicsapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.egemathematicsapp.databinding.FragmentHomeBinding;
import com.example.egemathematicsapp.registration_activity;
import com.example.egemathematicsapp.taskActivity;
import com.example.egemathematicsapp.task_activity2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
//import okhttp3.MediaType.Companion.toMediaType;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private String task_text;
    private  String task_answer;
    private Button getTaskbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getTaskbtn = binding.generateRandomTask;

        getTaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), task_activity2.class);
//                dbHelper=new DBHelper(getActivity().getApplicationContext());
//                try {
//                    database=dbHelper.getWritableDatabase();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//                ArrayList<HashMap<String,String>> tasks =new ArrayList<>();
//                HashMap <String,String> task;
//                Cursor cursor = database.rawQuery("SELECT task_text, task_answer FROM tasks", null);
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()){
//                    task_text =cursor.getString(0);
//                    task_answer = cursor.getString(1);
//                    Log.i("xd", task_text);
//                    Log.i("xd", task_answer);
//                    task=new HashMap<>();
//                    task.put("text", cursor.getString(0));
//                    task.put("answer", cursor.getString(1));
//                    tasks.add(task);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//                int index = (int)(Math.random() * tasks.size());
//                Log.i("testing", tasks.get(index).get("text"));
//                Log.i("testingAnswer", tasks.get(index).get("answer"));
//                intent.putExtra("task_text",tasks.get(index).get("text"));
//                intent.putExtra("task_answer", tasks.get(index).get("answer"));
//                startActivity(intent);
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
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
//            RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));

            String url = "https://ll7pqrc3-8000.euw.devtunnels.ms/tasks/1";

            Request request = builder.url(String.format(url))
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