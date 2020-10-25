package edu.neu.mad_sea.lishawang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button about_button;
    private Button error_button;
    private Button dic_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Lisha Wang");

        // Start About Activity after user clicks the about button
        about_button = (Button)findViewById(R.id.about_button);
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent_about);
            }
        });

        // Start dictionary after user clicks the dictionary button
        dic_button = (Button)findViewById(R.id.dic_button);
        dic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_dic = new Intent(MainActivity.this, Dictionary.class);
                startActivity(intent_dic);
            }
        });

        // Generate an error after user clicks the error button
        error_button = (Button)findViewById(R.id.error_button);
        error_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new NullPointerException();
            }
        });
    }
}