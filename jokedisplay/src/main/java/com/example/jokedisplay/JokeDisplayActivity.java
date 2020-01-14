package com.example.jokedisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        TextView jokeText = findViewById(R.id.joke_text);

        String joke;

        Intent parentIntent = getIntent();

        if (parentIntent.hasExtra("JOKE_EXTRA")) {
            joke = parentIntent.getStringExtra("JOKE_EXTRA");
            jokeText.setText(joke);
        }
    }
}
