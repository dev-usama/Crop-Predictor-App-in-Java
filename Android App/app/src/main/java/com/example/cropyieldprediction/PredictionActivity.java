package com.example.cropyieldprediction;

import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PredictionActivity extends AppCompatActivity {
    static String url;
    EditText PH;
    TextView crop;
    EditText humidity;
    EditText nitrogen;
    EditText phosphorus;
    EditText potassium;
    EditText temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prediction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.nitrogen = (EditText) findViewById(R.id.Natxt);
        this.potassium = (EditText) findViewById(R.id.KText);
        this.phosphorus = (EditText) findViewById(R.id.PTxt);
        this.PH = (EditText) findViewById(R.id.PHtxt);
        this.humidity = (EditText) findViewById(R.id.hum_text);
        this.temp = (EditText) findViewById(R.id.temptxt);
        this.crop = (TextView) findViewById(R.id.crop);
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PredictionActivity.this, LoginActivity.class));
                finish();
            }
        });
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        ((Button) findViewById(R.id.predict)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PredictionActivity.url = String.format("https://248d-103-157-89-13.ngrok-free.app/api/predict?nitrogen=%s&phosphorus=%s&potassium=%s&temperature=%s&humidity=%s&PH=%s", new Object[]{PredictionActivity.this.nitrogen.getText().toString(), PredictionActivity.this.phosphorus.getText().toString(), PredictionActivity.this.potassium.getText().toString(), PredictionActivity.this.temp.getText().toString(), PredictionActivity.this.humidity.getText().toString(), PredictionActivity.this.PH.getText().toString()});
                requestQueue.add(new StringRequest(0, PredictionActivity.url, new Response.Listener<String>() {
                    public void onResponse(String response) {
                        PredictionActivity.this.crop.setText("Recommended crop is " + response);
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PredictionActivity.this.getApplicationContext(), "Someting went wrong", Toast.LENGTH_LONG);
                    }
                }));
            }
        });
    }
}