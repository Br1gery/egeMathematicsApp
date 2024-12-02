package com.example.egemathematicsapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.egemathematicsapp.ui.home.DBHelper;

public class task_activity2 extends AppCompatActivity {

    private Button submitBtn;
    private Button returnBtn;
    private EditText answerEdit;
    private TextView taskText;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private TextView explanationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            explanationText.setVisibility(View.INVISIBLE);
            return insets;
        });
        Intent intent = getIntent();
        String taskTextfroView = String.valueOf(intent.getStringExtra("text_task"));
        String asnwer = String.valueOf(intent.getStringExtra("answer_task"));
        String expl = String.valueOf(intent.getStringExtra("explanation_task"));

        taskText = findViewById(R.id.task_text_view2);
        explanationText =findViewById(R.id.explanation_edit);

        taskText.setText(taskTextfroView);
        explanationText.setText(expl);

        submitBtn = findViewById(R.id.submitBtn);
        answerEdit = findViewById(R.id.answerEditText);
        returnBtn = findViewById(R.id.returnBtn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerEdit.getText().toString().equals(asnwer)){
//                    Intent intent2 = new Intent(getApplicationContext(), task_activity2.class);
                    Toast myToast = Toast.makeText(getApplicationContext(),"Ваш ответ верный!",Toast.LENGTH_SHORT);
                    explanationText.setVisibility(View.VISIBLE);
                    myToast.show();
//                    startActivity(intent2);
//                    Intent intent2 = new Intent(getApplicationContext(), task_activity2.class);
//                    dbHelper=new DBHelper(getApplicationContext());
//                    try {
//                        database=dbHelper.getWritableDatabase();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }


//                    ArrayList<HashMap<String,String>> tasks =new ArrayList<>();
//                    HashMap <String,String> task;
//                    Cursor cursor = database.rawQuery("SELECT task_text, task_answer FROM tasks", null);
//                    cursor.moveToFirst();
//                    while (!cursor.isAfterLast()){
//                        task=new HashMap<>();
//                        task.put("text", cursor.getString(0));
//                        task.put("answer", cursor.getString(1));
//                        tasks.add(task);
//                        cursor.moveToNext();
//                    }
//                    cursor.close();
//                    int index = (int)(Math.random() * tasks.size());
//                    Log.i("testing", tasks.get(index).get("text"));
//                    Log.i("testingAnswer", tasks.get(index).get("answer"));
//                    intent2.putExtra("task_text",tasks.get(index).get("text"));
//                    intent2.putExtra("task_answer", tasks.get(index).get("answer"));
//                    startActivity(intent2);
                }
                else{
                    Toast myToast = Toast.makeText(getApplicationContext(),"Ваш ответ неверный!",Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }
}