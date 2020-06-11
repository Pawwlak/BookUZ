package com.example.bookuz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText title_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title_edittext = findViewById(R.id.edit_title);
    }

    public void book_search(View view) {
        String title = title_edittext.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected() && title.length() != 0) {
            new Find(this).execute(title);
        } else {
            if (title.length() == 0) {
                Toast.makeText(this, "Tytuł jest pusty", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Połączenie nie zostało nawiązane", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void ReturnThreadResult(String[] result){
        Intent intent = new Intent(this, Book.class);
        String[] book_informations = result;
        intent.putExtra("API", book_informations);
        startActivity(intent);
    }
}
