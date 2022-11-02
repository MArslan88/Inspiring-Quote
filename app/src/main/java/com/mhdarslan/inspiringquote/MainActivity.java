package com.mhdarslan.inspiringquote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String QUOTE_KEY = "quote";
    public static final String AUTHOR_KEY = "author";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveQuote (View view){
        EditText quoteView = findViewById(R.id.quote_edt);
        EditText authorView = findViewById(R.id.author_edt);

        String quoteText = quoteView.getText().toString();
        String authorText = authorView.getText().toString();

        if(quoteText.isEmpty() || authorText.isEmpty()) { return;}

        Map<String,Object> dataToSave = new HashMap<String,Object>();
        dataToSave.put(QUOTE_KEY,quoteText);
        dataToSave.put(AUTHOR_KEY,authorText);
    }
}