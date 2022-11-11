package com.example.myultimateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AskSecurityQuestionActivity extends AppCompatActivity {

    TextView SQ;
    EditText SA;
    Button cancel, confirm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_security_question);

        SQ = findViewById(R.id.AskSecurityQuestionActivity_SQ);
        SA = findViewById(R.id.AskSecurityQuestionActivity_SA);
        cancel = findViewById(R.id.AskSecurityQuestionActivity_Cancel);
        confirm = findViewById(R.id.AskSecurityQuestionActivity_confirm);

        String username = getIntent().getStringExtra("username");

        dbHandler handler = new dbHandler(this, "myApp", null, 1);


        UserDetails user = handler.fetchUserUsingUserName(username);

        if (user == null)
            finish();

        Log.d("Ask_Security", "onCreate: " + user.toString());
        SQ.setText(user.getSecurityquestion());


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(SA.getText());
                if (str.toLowerCase().equals(user.getSecurityanswer().toLowerCase())) {
                    Intent intent = new Intent(AskSecurityQuestionActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("username", user.getUsername());
                    startActivity(intent);
                } else {
                    Toast.makeText(AskSecurityQuestionActivity.this, "Your answer is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}