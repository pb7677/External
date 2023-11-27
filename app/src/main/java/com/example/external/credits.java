package com.example.external;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("MainActivity");
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("MainActivity")) {
            Intent Di = new Intent(this, MainActivity.class);
            startActivity(Di);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}