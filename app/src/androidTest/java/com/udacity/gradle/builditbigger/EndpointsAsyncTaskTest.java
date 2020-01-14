package com.udacity.gradle.builditbigger;

import android.util.Log;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

// Reference: "http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html"

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    private static final int COUNT = 1;
    private static final int TIME_OUT = 10;
    private String joke = null;
    private Exception exception = null;

    private EndpointsAsyncTask.OnTaskComplete onTaskCompleteListener = new EndpointsAsyncTask.OnTaskComplete() {
        @Override
        public void onTaskComplete(String jokeResult) {

        }

        @Override
        public void onPreTask() {

        }
    };

    private CountDownLatch countDownLatch = null;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        countDownLatch = new CountDownLatch(COUNT);
    }

    private EndpointsAsyncTask.EndpointsAsyncTaskListener endpointsAsyncTaskListener = new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
        @Override
        public void onComplete(String jokeResult, Exception e) {
            joke = jokeResult;
            exception = e;
            // Decrement the count of the latch, releasing all waiting threads if the count
            // reaches zero
            countDownLatch.countDown();
        }
    };

    @Test
    public void checkNonEmptyString() throws InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask(onTaskCompleteListener);
        task.setEndpointsAsyncTaskListener(endpointsAsyncTaskListener).execute();

        countDownLatch.await(TIME_OUT, TimeUnit.SECONDS);

        assertNull(exception);
        Log.d("JOKE_TAG", "joke is: " + joke);
        assertNotNull(joke);

    }

}