package com.example.pexo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(4);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfPlayers = numberPicker.getValue();
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("numberOfPlayers", numberOfPlayers);
                startActivity(intent);
            }
        });
    }
}
