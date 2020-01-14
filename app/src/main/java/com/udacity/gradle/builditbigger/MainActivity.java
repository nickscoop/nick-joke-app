package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jokedisplay.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.OnTaskComplete {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskComplete(String jokeResult) {
        progressBar.setVisibility(View.GONE);
        Intent jokeDisplayIntent = new Intent(MainActivity.this, JokeDisplayActivity.class);
        jokeDisplayIntent.putExtra("JOKE_EXTRA", jokeResult);
        startActivity(jokeDisplayIntent);
    }

    @Override
    public void onPreTask() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void tellJoke(View view) {
        new EndpointsAsyncTask(this).execute();
    }
}
