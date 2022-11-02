package com.mhdarslan.inspiringquote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button save_btn, load_btn;
    TextView quoteTextView;

    public static final String QUOTE_KEY = "quote";
    public static final String AUTHOR_KEY = "author";
    public static final String TAG = "inspirationQuote";

    //    private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("sampleData").document("inspiration");
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/inspiration");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save_btn = findViewById(R.id.save_btn);
        load_btn = findViewById(R.id.load_btn);
        quoteTextView = findViewById(R.id.quote_txt);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuote();
            }
        });

        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchQuote();
            }
        });

    }

    public void saveQuote (){
        EditText quoteView = findViewById(R.id.quote_edt);
        EditText authorView = findViewById(R.id.author_edt);

        String quoteText = quoteView.getText().toString();
        String authorText = authorView.getText().toString();

        if(quoteText.isEmpty() || authorText.isEmpty()) { return;}

        Map<String,Object> dataToSave = new HashMap<String,Object>();
        dataToSave.put(QUOTE_KEY,quoteText);
        dataToSave.put(AUTHOR_KEY,authorText);

        // send data into firebase
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"Document has been saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Document was not saved!",e);
            }
        });

        quoteView.setText("");
        authorView.setText("");
    }

    public void fetchQuote(){
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String quoteText = documentSnapshot.getString(QUOTE_KEY);
                    String authText = documentSnapshot.getString(AUTHOR_KEY);

                    quoteTextView.setText("\"" + quoteText + "\"--" + authText);
                }
            }
        });
    }
}