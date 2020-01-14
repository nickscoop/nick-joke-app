package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.List;

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;

    private OnTaskComplete onTaskCompleteListener;
    private EndpointsAsyncTaskListener endpointsAsyncTaskListener;

    public interface EndpointsAsyncTaskListener {
        void onComplete(String jokeResult, Exception e);
    }

    public interface OnTaskComplete {
        void onTaskComplete(String jokeResult);
        void onPreTask();
    }

    EndpointsAsyncTask setEndpointsAsyncTaskListener(EndpointsAsyncTaskListener endpointsAsyncTaskListener) {
        this.endpointsAsyncTaskListener = endpointsAsyncTaskListener;
        return this;
    }

    EndpointsAsyncTask(@NonNull OnTaskComplete onTaskCompleteListener) {
        this.onTaskCompleteListener = onTaskCompleteListener;
    }

    @Override
    protected void onPreExecute() {
        onTaskCompleteListener.onPreTask();
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        onTaskCompleteListener.onTaskComplete(result);
    }
}
