package com.example.bookuz;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class Find extends AsyncTask<String, Void, String> {

    MainActivity parent;

    Find(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            String[] bookinfo = {"0", "1", "2", "3", "4", "5"};
            JSONObject book = itemsArray.getJSONObject(0);
            JSONObject volumeInfo = book.getJSONObject("volumeInfo");
            JSONObject accessInfo = book.getJSONObject("accessInfo");
            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");try {
                bookinfo[0] = volumeInfo.getString("title");
                bookinfo[1] = volumeInfo.getString("authors");
                bookinfo[2] = volumeInfo.getString("publishedDate");
                bookinfo[3] = volumeInfo.getString("description");
                bookinfo[4] = imageLinks.getString("smallThumbnail");
                bookinfo[5] = accessInfo.getString("webReaderLink");
            } catch (Exception e) {
                e.printStackTrace();
            }
            parent.ReturnThreadResult(bookinfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
