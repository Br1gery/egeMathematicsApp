package com.example.egemathematicsapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.egemathematicsapp.databinding.FragmentHomeBinding;
import com.example.egemathematicsapp.taskActivity;
import com.example.egemathematicsapp.task_activity2;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

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
                Intent intent = new Intent(getActivity().getApplicationContext(), task_activity2.class);
                dbHelper=new DBHelper(getActivity().getApplicationContext());
                try {
                    database=dbHelper.getWritableDatabase();
                } catch (Exception e){
                    e.printStackTrace();
                }


                ArrayList<HashMap<String,String>> tasks =new ArrayList<>();
                HashMap <String,String> task;
                Cursor cursor = database.rawQuery("SELECT task_text, task_answer FROM tasks", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    task_text =cursor.getString(0);
                    task_answer = cursor.getString(1);
                    Log.i("xd", task_text);
                    Log.i("xd", task_answer);
                    task=new HashMap<>();
                    task.put("text", cursor.getString(0));
                    task.put("answer", cursor.getString(1));
                    tasks.add(task);
                    cursor.moveToNext();
                }
                cursor.close();
                int index = (int)(Math.random() * tasks.size());
                Log.i("testing", tasks.get(index).get("text"));
                Log.i("testingAnswer", tasks.get(index).get("answer"));
                intent.putExtra("task_text",tasks.get(index).get("text"));
                intent.putExtra("task_answer", tasks.get(index).get("answer"));
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