package com.example.bookuz;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Order_async extends AsyncTask<String, Void, String> {
    String date;
    String username;
    String title;
    String author;

    Order_async(String title_string, String author_string, String date_string, String username_string) {
        this.title = title_string;
        this.author = author_string;
        this.date = date_string;
        this.username = username_string;
    }

    @Override
    protected String doInBackground(String... strings) {
        final String URL = "http://vlab.imei.uz.zgora.pl/mybooks/api/orders?";
        final String TITLE = "title";
        final String AUTHOR = "author";
        final String DATE = "date";
        final String USERNAME = "username";
        final String LOG_TAG = NetworkUtils.class.getSimpleName();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            Uri builtURI = Uri.parse(URL).buildUpon()
                    .appendQueryParameter(TITLE, title)
                    .appendQueryParameter(AUTHOR, author)
                    .appendQueryParameter(DATE, date)
                    .appendQueryParameter(USERNAME, username)
                    .build();
            java.net.URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            bookJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}
