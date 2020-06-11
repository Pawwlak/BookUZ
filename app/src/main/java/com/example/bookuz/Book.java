package com.example.bookuz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Book extends AppCompatActivity {

    TextView author_textview = null;
    TextView title_textview = null;
    TextView date_textview = null;
    TextView desc_textview = null;
    ImageView thumbnail_imageview = null;
    String read_url = null;
    String title = null;
    String author = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Intent intent = getIntent();
        String[] book_informations = intent.getStringArrayExtra("API");
        author_textview = findViewById(R.id.author);
        title_textview = findViewById(R.id.title);
        date_textview = findViewById(R.id.date);
        desc_textview = findViewById(R.id.description);
        thumbnail_imageview = findViewById(R.id.thumbnail);
        title = book_informations[0];
        author = book_informations[1];
        title_textview.setText(title_textview.getText() + title);
        author_textview.setText(author_textview.getText() + author);
        date_textview.setText(date_textview.getText() + book_informations[2]);
        desc_textview.setText(book_informations[3]);
        read_url = book_informations[5];
        Picasso.get().load(book_informations[4]).into(thumbnail_imageview);
    }

    public void Exit(View view) {
        finish();
    }

    public void Open_order_window(View view) {
        BitmapDrawable drawable = (BitmapDrawable) thumbnail_imageview.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        Intent intent = new Intent(this, Buy.class);
        intent.putExtra("Cover", b);
        intent.putExtra("Title", title);
        intent.putExtra("Author", author);
        startActivity(intent);
    }

    public void read(View view) {
        Intent browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(read_url));
        startActivity(browser_intent);
    }
}
