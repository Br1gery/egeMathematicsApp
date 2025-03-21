package com.example.egemathematicsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import java.util.Objects;

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
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;
    private TextView timer2;
    private TextView enterAnswerText;
    
    private CountDownTimer timer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task2);
//        explanationText.setVisibility(View.INVISIBLE);
//        explanationImageView.setVisibility(View.INVISIBLE);
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
        String explanation_photo = String.valueOf(intent.getStringExtra("explanation_photo"));
        String text_photo = String.valueOf(intent.getStringExtra("text_photo"));

        Boolean visibility = false;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                visibility = false;
            } else {
                visibility = extras.getBoolean("visible");
            }
        } else {
            visibility = (Boolean) savedInstanceState.getSerializable("visible");
        }

        taskText = findViewById(R.id.task_text_view2);
        explanationText =findViewById(R.id.explanation_edit);

//        taskText.setText(taskTextfroView);
//        explanationText.setText(expl);

        submitBtn = findViewById(R.id.submitBtn);
        answerEdit = findViewById(R.id.answerEditText);
        returnBtn = findViewById(R.id.returnBtn);

        textImageView = findViewById(R.id.taskTextView);
        explanationImageView = findViewById(R.id.taskExplanationView);

        timer2 = findViewById(R.id.timerTextView2);
        enterAnswerText= findViewById(R.id.textView2);

        Glide.with(this)
                .load(((MyApplication) getApplicationContext()).getSomeVariable("url") + "tasks/textPhotos/" + text_photo)
                .into(textImageView);
        Glide.with(this)
                .load(((MyApplication) getApplicationContext()).getSomeVariable("url")+ "tasks/textPhotos/" + explanation_photo)
                .into(explanationImageView);

        if(Boolean.TRUE.equals(visibility)){
            explanationText.setVisibility(View.VISIBLE);
            explanationImageView.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.INVISIBLE);
        }

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }
        });

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.correct);

        if (savedInstanceState != null) {
            timeLeftInMillis = savedInstanceState.getLong("timeLeft");
            isTimerRunning = savedInstanceState.getBoolean("isRunning");

            if (isTimerRunning) {
                // Если таймер работал, восстанавливаем его
                startTimer(timeLeftInMillis);
            } else {
                // Если таймер завершился или не начинался, показываем последнее время
                long minutes = (timeLeftInMillis / 1000) / 60;
                long seconds = (timeLeftInMillis / 1000) % 60;
                timer2.setText(String.format("%02d:%02d", minutes, seconds));
            }
        } else {
            // Если это первый запуск, начинаем таймер
            startTimer(timeLeftInMillis);
        }

        if(timeLeftInMillis == 0){
            Toast myToast = Toast.makeText(getApplicationContext(),"Время вышло!",Toast.LENGTH_SHORT);
            explanationText.setVisibility(View.VISIBLE);
            explanationImageView.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.INVISIBLE);
            answerEdit.setVisibility(View.INVISIBLE);
            enterAnswerText.setVisibility(View.INVISIBLE);
            myToast.show();
        }

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
                    playSound();
                }
                else{
                    Toast myToast = Toast.makeText(getApplicationContext(),"Ваш ответ неверный!",Toast.LENGTH_SHORT);
                    vibrate(200);
                    myToast.show();
                }
            }
        });
    }

    private void startTimer(long startTime) {
        timer = new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished; // Обновляем оставшееся время
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                timer2.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                Toast myToast = Toast.makeText(getApplicationContext(),"Время вышло!",Toast.LENGTH_SHORT);
                explanationText.setVisibility(View.VISIBLE);
                explanationImageView.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.INVISIBLE);
                answerEdit.setVisibility(View.INVISIBLE);
                enterAnswerText.setVisibility(View.INVISIBLE);
                myToast.show();
                isTimerRunning = false;
                timeLeftInMillis = 0; // Таймер завершился
            }
        }.start();
        isTimerRunning = true;
    }

    private void vibrate(long duration) {
        vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start(); // Проигрываем звук

            // Перематываем звук в начало после завершения
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.seekTo(0);
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Освобождаем ресурсы MediaPlayer
            mediaPlayer = null;
        }
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

            String token = ((MyApplication) getApplicationContext()).getSomeVariable("token");
            Request request = builder.url(String.format(url)).post(formBody).addHeader("Authorization","Bearer " + token)
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String asnwer = String.valueOf(intent.getStringExtra("answer_task"));
        if(answerEdit.getText().toString().equals(asnwer)){
            savedInstanceState.putBoolean("visible", true);
        }

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("timeLeft", timeLeftInMillis); // Сохраняем оставшееся время
        savedInstanceState.putBoolean("isRunning", isTimerRunning);
    }

}