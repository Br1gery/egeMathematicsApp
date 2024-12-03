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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.egemathematicsapp.ui.home.DBHelper;

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

public class registration_activity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private Button regBtn;
    private TextView logViewBtn;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private EditText loginEditText;
    private EditText passEditText;
    private EditText passConfEditText;
    private String email;
    private String pwd;
    private String pwd_conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        regBtn = findViewById(R.id.regBtn);
        loginEditText = findViewById(R.id.emailEditText);
        passEditText = findViewById(R.id.pwdEditText);
        passConfEditText = findViewById(R.id.repeatPwdTextEdit);
        logViewBtn = findViewById(R.id.textView5);



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dbHelper=new DBHelper(getApplicationContext());
//                try {
//                    database=dbHelper.getWritableDatabase();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                Boolean userExists = true;
//                String existingEmail = "";
//                Cursor cursor = database.rawQuery("SELECT email FROM users WHERE email = $1", new String[]{email});
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()){
//                    existingEmail = cursor.getString(0);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//                Log.i("emailEx", existingEmail);
//                Log.i("REalEmail",email);
                String email = loginEditText.getText().toString();
                String pwd = passEditText.getText().toString();
                String pwd_conf = passConfEditText.getText().toString();
                Log.i("xd",pwd_conf);
                Log.i("xd",pwd);
                Log.i("xd",email);
                if (pwd_conf.isEmpty() || !pwd.equals(pwd_conf) || email.isEmpty()) {
                    Toast myToast = Toast.makeText(getApplicationContext(), "Проверьте введенные данные", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
//                String real_pwd = "";
//                ArrayList<HashMap<String,String>> tasks =new ArrayList<>();
//                HashMap <String,String> task;
//                database.execSQL("INSERT INTO users(email, pwd) VALUES($1,$2)", new String[]{pwd, email});
////                cursor.moveToFirst();
////                while (!cursor.isAfterLast()){
////                    task=new HashMap<>();
////                    task.put("email", cursor.getString(0));
////                    task.put("pwd", cursor.getString(1));
////                    tasks.add(task);
////                    real_pwd = cursor.getString(1);
////                    cursor.moveToNext();
////                }
////                cursor.close();
//                Intent intent = new Intent(getApplicationContext(), login_activity.class);
//                Toast myToast = Toast.makeText(getApplicationContext(),"Успешная регистрация",Toast.LENGTH_SHORT);
//                myToast.show();
//                startActivity(intent);
                registration_activity.OkHttpHandler handler = new registration_activity.OkHttpHandler();
                handler.execute();
            }
        });

        logViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                startActivity(intent);
            }
        });
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
                json.put("mail", loginEditText.getText().toString());
                json.put("password", passEditText.getText().toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            RequestBody formBody = RequestBody.create(JSON, String.valueOf(json));

            String url = "https://mp460zr5-8000.euw.devtunnels.ms/user/reg";

            Request request = builder.url(String.format(url)).post(formBody)
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());
                Log.i("xd", object.toString());
                if (object.has("detail")) {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "Неверные данные", Toast.LENGTH_SHORT).show();
                    });
                    return null;
                } else if (object.has("id")) {
                    object.getString("id");
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