package com.example.pexo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EndActivity extends AppCompatActivity {

    private TextView resultsTextView;
    private Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        resultsTextView = findViewById(R.id.resultsTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        int[] scores = getIntent().getIntArrayExtra("scores");
        displayResults(scores);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayResults(int[] scores) {
        StringBuilder result = new StringBuilder();
        int maxScore = -1;
        for (int score : scores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }

        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            result.append("Player ").append(i + 1).append(": ").append(scores[i]).append(" pairs\n");
            if (scores[i] == maxScore) {
                winners.add(i + 1);
            }
        }

        result.append("\nWinner(s): ");
        for (int i = 0; i < winners.size(); i++) {
            result.append("Player ").append(winners.get(i));
            if (i < winners.size() - 1) {
                result.append(", ");
            }
        }

        resultsTextView.setText(result.toString());
    }
}
