package com.example.bookuz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Buy extends AppCompatActivity {

    EditText date_edit = null;
    EditText username_edit = null;
    String title = null;
    String author = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("Cover");
        title = extras.getString("Title");
        author = extras.getString("Author");
        date_edit = findViewById(R.id.date_input);
        username_edit = findViewById(R.id.username_input);
        ImageView image = findViewById(R.id.cover);
        TextView title_view = findViewById(R.id.title_order);
        title_view.setText(title);
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        image.setImageBitmap(bmp);
    }

    public void Exit(View view) {
        finish();
    }

    public void Order(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Czy na pewno chcesz zamówić?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String date = date_edit.getText().toString();
                        String username = username_edit.getText().toString();
                        new Order_async(title, author, date, username).execute();
                    }
                })
                .setNegativeButton("Nie", null)
                .show();

    }
}
