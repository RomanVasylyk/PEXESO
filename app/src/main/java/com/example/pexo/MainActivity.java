package com.example.pexo;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private TextView currentPlayerTextView;
    private List<String> words;
    private List<Card> cards;
    private int currentPlayer;
    private int[] scores;
    private int pairsFound;
    private int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        currentPlayerTextView = findViewById(R.id.currentPlayerTextView);

        numberOfPlayers = getIntent().getIntExtra("numberOfPlayers", 2);

        int[] gridSizes = {2, 4, 5, 6};
        Random random = new Random();
        int gridSizeIndex = random.nextInt(gridSizes.length);
        int gridSize = gridSizes[gridSizeIndex];
        int gridRows = gridSize == 2 ? 2 : 4; // 2x2, 4x4, 5x4, 6x4

        WordsRepository repository = new WordsRepository(this);
        words = repository.getAllWords();
        int maxPairs = words.size() / 2;
        while (gridSize * gridRows / 2 > maxPairs) {
            gridSizeIndex = random.nextInt(gridSizes.length);
            gridSize = gridSizes[gridSizeIndex];
            gridRows = gridSize == 2 ? 2 : 4;
        }

        initializeGame(gridSize, gridRows, numberOfPlayers);
    }

    private void initializeGame(int gridSize, int gridRows, int numberOfPlayers) {
        Collections.shuffle(words);

        cards = new ArrayList<>();
        for (int i = 0; i < gridSize * gridRows / 2; i++) {
            cards.add(new Card(words.get(i)));
            cards.add(new Card(words.get(i)));
        }
        Collections.shuffle(cards);

        gridLayout.setColumnCount(gridSize);
        gridLayout.setRowCount(gridRows);

        for (int i = 0; i < cards.size(); i++) {
            View cardView = createCardView(cards.get(i), gridSize, gridRows);
            gridLayout.addView(cardView);
        }

        scores = new int[numberOfPlayers];
        currentPlayer = 0;
        pairsFound = 0;
        updateCurrentPlayerTextView();
    }

    private View createCardView(Card card, int gridSize, int gridRows) {
        TextView cardView = new TextView(this);
        int cardSize = calculateCardSize(gridSize, gridRows);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = cardSize;
        params.height = cardSize;
        cardView.setLayoutParams(params);
        cardView.setText("XXXXX");
        cardView.setTextSize(24);
        cardView.setPadding(20, 20, 20, 20);
        cardView.setBackgroundResource(android.R.drawable.btn_default);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCardClick(card, cardView);
            }
        });

        card.setView(cardView);
        return cardView;
    }

    private int calculateCardSize(int gridSize, int gridRows) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int maxCardWidth = screenWidth / gridSize;
        int maxCardHeight = screenHeight / gridRows;
        return Math.min(maxCardWidth, maxCardHeight) - 20; // Зменшення розміру для меж та відступів
    }

    private void handleCardClick(Card card, TextView cardView) {
        if (card.isMatched() || card.isShowing()) {
            return;
        }

        cardView.setText(card.getWord());
        card.setShowing(true);

        List<Card> showingCards = new ArrayList<>();
        for (Card c : cards) {
            if (c.isShowing() && !c.isMatched()) {
                showingCards.add(c);
            }
        }

        if (showingCards.size() == 2) {
            Card firstCard = showingCards.get(0);
            Card secondCard = showingCards.get(1);

            if (firstCard.getWord().equals(secondCard.getWord())) {
                firstCard.setMatched(true);
                secondCard.setMatched(true);
                scores[currentPlayer]++;
                pairsFound++;
            } else {
                firstCard.setShowing(false);
                secondCard.setShowing(false);
                firstCard.getView().postDelayed(() -> {
                    ((TextView) firstCard.getView()).setText("XXXXX");
                    ((TextView) secondCard.getView()).setText("XXXXX");
                }, 1000);
                currentPlayer = (currentPlayer + 1) % scores.length;
            }

            if (pairsFound == cards.size() / 2) {
                endGame();
            }
        }

        updateCurrentPlayerTextView();
    }

    private void updateCurrentPlayerTextView() {
        StringBuilder builder = new StringBuilder();
        builder.append("Current Player: ").append(currentPlayer + 1).append("\n");
        for (int i = 0; i < scores.length; i++) {
            builder.append("Player ").append(i + 1).append(": ").append(scores[i]).append(" pairs\n");
        }
        currentPlayerTextView.setText(builder.toString());
    }

    private void endGame() {
        Intent intent = new Intent(MainActivity.this, EndActivity.class);
        intent.putExtra("scores", scores);
        startActivity(intent);
        finish();
    }
}
