package com.example.egemathematicsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.egemathematicsapp.ui.home.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class task_activity2 extends AppCompatActivity {

    private Button submitBtn;
    private Button returnBtn;
    private EditText answerEdit;
    private TextView taskText;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private TextView explanationText;
    private ImageView textImageView;
    private ImageView explanationImageView;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            explanationText.setVisibility(View.INVISIBLE);
            explanationImageView.setVisibility(View.INVISIBLE);
            return insets;
        });
        Intent intent = getIntent();
        String taskTextfroView = String.valueOf(intent.getStringExtra("text_task"));
        String asnwer = String.valueOf(intent.getStringExtra("answer_task"));
        String expl = String.valueOf(intent.getStringExtra("explanation_task"));

        taskText = findViewById(R.id.task_text_view2);
        explanationText =findViewById(R.id.explanation_edit);

//        taskText.setText(taskTextfroView);
//        explanationText.setText(expl);

        submitBtn = findViewById(R.id.submitBtn);
        answerEdit = findViewById(R.id.answerEditText);
        returnBtn = findViewById(R.id.returnBtn);

        textImageView = findViewById(R.id.taskTextView);
        explanationImageView = findViewById(R.id.taskExplanationView);

        Glide.with(this)
                .load("https://ll7pqrc3-8000.euw.devtunnels.ms/tasks/textPhotos/Screenshot_4.png")
                .into(textImageView);
        Glide.with(this)
                .load("https://ll7pqrc3-8000.euw.devtunnels.ms/tasks/textPhotos/Screenshot_4.png")
                .into(explanationImageView);

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
                    Toast myToast = Toast.makeText(getApplicationContext(),"Ваш ответ верный!",Toast.LENGTH_SHORT);
                    explanationText.setVisibility(View.VISIBLE);
                    explanationImageView.setVisibility(View.VISIBLE);
                    submitBtn.setVisibility(View.INVISIBLE);
                    myToast.show();
                    task_activity2.OkHttpHandler handler = new task_activity2.OkHttpHandler();
                    handler.execute();
                }
                else{
                    Toast myToast = Toast.makeText(getApplicationContext(),"Ваш ответ неверный!",Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }

    public class OkHttpHandler extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Request.Builder builder = new Request.Builder();

            String mail = ((MyApplication) getApplicationContext()).getSomeVariable("userName");

            JSONObject json = new JSONObject();
            try {
                json.put("mail", mail);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));

            String url = ((MyApplication) getApplicationContext()).getSomeVariable("url") + "tasks/solveTask";

            Request request = builder.url(String.format(url)).post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());

//                Log.i("xd", object.toString());
                if (object.has("detail")) {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(),"Что-то пошло не так" , Toast.LENGTH_SHORT).show();
                    });
                    return null;
                }
                else {
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