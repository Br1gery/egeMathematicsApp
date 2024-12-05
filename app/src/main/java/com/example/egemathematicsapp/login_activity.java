package com.example.egemathematicsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.egemathematicsapp.MyApplication;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.egemathematicsapp.ui.dashboard.DashboardViewModel;
import com.example.egemathematicsapp.ui.home.DBHelper;
import com.example.egemathematicsapp.ui.home.HomeFragment;
import com.example.egemathematicsapp.ui.home.HomeViewModel;

import org.json.JSONArray;
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

public class login_activity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private Button loginBtn;
    private String loginStatus;
    private EditText loginEditText;
    private EditText passEditText;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private TextView regViewBtn;
    private String token2;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Intent intent = getIntent();
            boolean exitToken = intent.getBooleanExtra("exit", false);
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            if(!exitToken){
                String restoredText = prefs.getString("token", "");
//                    Log.i("tok",restoredText);
                if(!(restoredText.isEmpty())){
                    String restoredUserName = prefs.getString("userName", "");
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);

                    //https://stackoverflow.com/questions/15392261/android-pass-dataextras-to-a-fragment

                    ((MyApplication)getApplicationContext()).setSomeVariable("userName",restoredUserName);
                    ((MyApplication)getApplicationContext()).setSomeVariable("token",restoredText);

                    startActivity(intent2);
                }
            }
            else{
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putString("token", "");
                editor.putString("userName", "");
                editor.apply();
            }
            return insets;
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.pwdEditText);
        regViewBtn = findViewById(R.id.textView5);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEditText.getText().toString();
                String pwd = passEditText.getText().toString();

                OkHttpHandler handler = new OkHttpHandler();
                handler.execute();

            }
        });

        regViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registration_activity.class);
                startActivity(intent);
            }
        });
    }

    public class OkHttpHandler extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Request.Builder builder = new Request.Builder();

            JSONObject json = new JSONObject();
            try {
                json.put("mail", loginEditText.getText().toString());
                json.put("password", passEditText.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));

            String url = ((MyApplication) getApplicationContext()).getSomeVariable("url") + "user/login";

            Request request = builder.url(String.format(url)).post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());

//                Log.i("xd", object.toString());
                if (object.has("detail")) {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(),"Неверные данные" , Toast.LENGTH_SHORT).show();
                    });
                    return null;
                }
                else if (object.has("token")) {
                    SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    String restoredText = prefs.getString("token", "");
//                    Log.i("tok",restoredText);
                    if(restoredText.equals("")){
                        token2 = object.getString("token");
                        userName = loginEditText.getText().toString();
                        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                        editor.putString("token", token2);
                        editor.putString("userName", userName);
                        editor.apply();
                    }
                    else{
                        Log.i("tok",restoredText);
                    }
                    Log.i("tok2",restoredText);
//                Log.i("xd",token2);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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