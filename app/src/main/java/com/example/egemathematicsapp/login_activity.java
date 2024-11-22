package com.example.egemathematicsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.egemathematicsapp.ui.home.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login_activity extends AppCompatActivity {

    private Button loginBtn;
    private String loginStatus;
    private EditText loginEditText;
    private EditText passEditText;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.pwdEditText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEditText.getText().toString();
                String pwd = passEditText.getText().toString();
                dbHelper=new DBHelper(getApplicationContext());
                try {
                    database=dbHelper.getWritableDatabase();
                } catch (Exception e){
                    e.printStackTrace();
                }

                String real_pwd = "";
                ArrayList<HashMap<String,String>> tasks =new ArrayList<>();
                HashMap <String,String> task;
                Cursor cursor = database.rawQuery("SELECT email, pwd FROM users WHERE pwd = $1 AND email = $2", new String[]{pwd, email});
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    task=new HashMap<>();
                    task.put("email", cursor.getString(0));
                    task.put("pwd", cursor.getString(1));
                    tasks.add(task);
                    real_pwd = cursor.getString(1);
                    cursor.moveToNext();
                }
                cursor.close();
                Log.i("real",real_pwd);
                Log.i("given",pwd);
                if(!pwd.equals(real_pwd) || pwd.isEmpty() || email.isEmpty()){
                    Toast myToast = Toast.makeText(getApplicationContext(),"Неверные данные",Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast myToast = Toast.makeText(getApplicationContext(),"Успешный вход",Toast.LENGTH_SHORT);
                myToast.show();
                startActivity(intent);
//                if(loginEditText.getText().toString().equals("") && passEditText.getText().toString().equals("")){
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    Toast myToast = Toast.makeText(getApplicationContext(),"Успешный вход",Toast.LENGTH_SHORT);
//                    myToast.show();
//                    startActivity(intent);
//                }
//                else{
//                    Toast myToast = Toast.makeText(getApplicationContext(),"Неверные данные",Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//                OkHttpHandler handler = new OkHttpHandler();
//                handler.execute();
            }
        });
    }

    public class OkHttpHandler extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Request.Builder builder = new Request.Builder();

            RequestBody formBody = new FormBody.Builder()
                    .add("username", loginEditText.getText().toString())
                    .add("password", passEditText.getText().toString())
                    .build();

            String url = "http://127.0.0.1:8000/user/login";

            Request request = builder.url(url).post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());
                try {
                    object.getString("token");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
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