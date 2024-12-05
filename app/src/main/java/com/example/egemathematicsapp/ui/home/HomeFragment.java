package com.example.egemathematicsapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.egemathematicsapp.MyApplication;
import com.example.egemathematicsapp.databinding.FragmentHomeBinding;
import com.example.egemathematicsapp.registration_activity;
import com.example.egemathematicsapp.taskActivity;
import com.example.egemathematicsapp.task_activity2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    private String task_answer;
    private Button getTaskbtn;
    private Button getTaskbtn2;
    private Button getTaskbtn3;
    private Button getTaskbtn4;
    private Button getTaskbtn5;
    private Button getTaskbtn6;
    private Button getTaskbtn7;
    private Button getTaskbtn8;
    private Button getTaskbtn9;
    private Button getTaskbtn10;
    private Button getTaskbtn11;
    private Button getTaskbtn12;
    private String task_number;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getTaskbtn = binding.task1Btn;
        getTaskbtn2 = binding.task1Btn2;
        getTaskbtn3 = binding.task1Btn3;
        getTaskbtn4 = binding.task1Btn4;
        getTaskbtn5 = binding.task1Btn5;
        getTaskbtn6 = binding.task1Btn6;
        getTaskbtn7 = binding.task1Btn7;
        getTaskbtn8 = binding.task1Btn8;
        getTaskbtn9 = binding.task1Btn9;
        getTaskbtn10 = binding.task1Btn10;
        getTaskbtn11 = binding.task1Btn11;
        getTaskbtn12 = binding.task1Btn12;


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
                task_number = "1";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "2";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "3";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "4";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "5";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "6";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "7";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "8";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "9";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "10";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "11";
                HomeFragment.OkHttpHandler handler = new HomeFragment.OkHttpHandler();
                handler.execute();
            }
        });
        getTaskbtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_number = "12";
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

            String url = "https://ll7pqrc3-8000.euw.devtunnels.ms/tasks/number/" + task_number;

            Request request = builder.url(String.format(url))
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());
//                Log.i("xd", object.toString());
                if (object.has("detail")) {
//                    Toast.makeText(requireActivity().getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                    return null;
                } else if (object.has("id")) {
                    String name_task = object.getString("name");
                    String text_task = object.getString("text");
                    String answer_task = object.getString("answer");
                    String explanation_task = object.getString("explanation");
                    Intent intent = new Intent(getActivity().getApplicationContext(), task_activity2.class);
                    intent.putExtra("name_task", name_task);
                    intent.putExtra("text_task", text_task);
                    intent.putExtra("answer_task", answer_task);
                    intent.putExtra("explanation_task", explanation_task);
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