package com.example.egemathematicsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.HashMap;

public class registration_activity extends AppCompatActivity {

    private Button regBtn;
    private TextView logViewBtn;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private EditText loginEditText;
    private EditText passEditText;
    private EditText passConfEditText;

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
                dbHelper=new DBHelper(getApplicationContext());
                try {
                    database=dbHelper.getWritableDatabase();
                } catch (Exception e){
                    e.printStackTrace();
                }
                String email = loginEditText.getText().toString();
                String pwd = passEditText.getText().toString();
                String pwd_conf = passConfEditText.getText().toString();
                Boolean userExists = true;
                String existingEmail = "";
                Cursor cursor = database.rawQuery("SELECT email FROM users WHERE email = $1", new String[]{email});
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    existingEmail = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                Log.i("emailEx", existingEmail);
                Log.i("REalEmail",email);
                if(pwd_conf.isEmpty() || !pwd.equals(pwd_conf) || email.isEmpty() || existingEmail.equals(email)){
                    Toast myToast = Toast.makeText(getApplicationContext(),"Проверьте введенные данные",Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }

                String real_pwd = "";
                ArrayList<HashMap<String,String>> tasks =new ArrayList<>();
                HashMap <String,String> task;
                database.execSQL("INSERT INTO users(email, pwd) VALUES($1,$2)", new String[]{pwd, email});
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()){
//                    task=new HashMap<>();
//                    task.put("email", cursor.getString(0));
//                    task.put("pwd", cursor.getString(1));
//                    tasks.add(task);
//                    real_pwd = cursor.getString(1);
//                    cursor.moveToNext();
//                }
//                cursor.close();
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                Toast myToast = Toast.makeText(getApplicationContext(),"Успешная регистрация",Toast.LENGTH_SHORT);
                myToast.show();
                startActivity(intent);
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
}