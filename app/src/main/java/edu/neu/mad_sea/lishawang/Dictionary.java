package edu.neu.mad_sea.lishawang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dictionary extends AppCompatActivity {
    DictionaryTrie dt = new DictionaryTrie();
    private String characters, pattern, value;
    private int length;
    private EditText letter_input, pattern_input, number_input;
    private TextView textView;
    private Button lookup_button, clear_button, acknowledge_button;
    private ListView listView;
    private long startTime, endTime, totalTime;
    private int showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        this.setTitle("Test Dictionary");
        loadDic();

        letter_input = (EditText) findViewById(R.id.letter_input);
        pattern_input = (EditText) findViewById(R.id.pattern_input);
        number_input = (EditText) findViewById(R.id.number_input);
        textView = (TextView) findViewById(R.id.dic_time);
        textView.setText("000ms");
        listView = (ListView) findViewById(R.id.word_list);

        lookup_button = (Button) findViewById(R.id.lookup_button);
        lookup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                characters = letter_input.getText().toString();
                pattern = pattern_input.getText().toString();
                value = number_input.getText().toString();
                if (characters.equals("") || pattern.equals("") || value.equals("")) {
                    invalidInput();
                }
                else {
                    length = Integer.valueOf(value);
                    if (length != pattern.length()) {
                        invalidNumber();
                    }
                    else {
                        displayWords();
                    }
                }
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                textView.setText(totalTime + "ms");
            }
        });

        clear_button = (Button) findViewById(R.id.clear_button);
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letter_input.getText().clear();
                pattern_input.getText().clear();
                number_input.getText().clear();
                textView.setText("000ms");
                listView.setAdapter(null);
            }
        });

        acknowledge_button = (Button) findViewById(R.id.acknowledge_button);
        acknowledge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_ack = new Intent(Dictionary.this, AckActivity.class);
                startActivity(intent_ack);
            }
        });
    }

    private void displayWords() {
        ArrayList<String> list = dt.predictUnderscore(characters, pattern);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    private void invalidInput() {
        Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
    }

    private void invalidNumber() {
        Toast.makeText(this, "Input pattern length does not match input number",
                Toast.LENGTH_SHORT).show();
    }

    private void loadDic() {
        try{
            InputStream is = this.getResources().openRawResource(R.raw.wordlist);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while(line != null){
                dt.insert(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}