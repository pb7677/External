package com.example.external;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
    private final String FILENAME = "exttest.txt";
    private final String FILENAME2 = "ettest.txt";
    private static final int REQUEST_CODE_PERMISSION = 1;
    EditText ET;
    TextView TV;
    String Text2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET = findViewById(R.id.editTextTextMultiLine);
        TV = findViewById(R.id.textView);


        try{
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileReader reader = new FileReader(file);
            BufferedReader bR = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            TV.setText(sB.toString());
            bR.close();
            reader.close();
        }catch (Exception e){
            Toast.makeText(this, "Error getting old text back from TV", Toast.LENGTH_SHORT).show();
        }

        try{
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME2);
            file.getParentFile().mkdirs();
            FileReader reader = new FileReader(file);
            BufferedReader bR = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            ET.setText(sB.toString());
            bR.close();
            reader.close();
        }catch (Exception e){
            Toast.makeText(this, "Error getting old text back from ET", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void save(View view) {
        Text2 = ET.getText().toString() + Text2;
        boolean storage = isExternalStorageAvailable();
        boolean permission = checkPermission();

        if (!(storage)) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (!(permission)) {
            requestPermission();
        }
        if (permission && storage){
            try {
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write(Text2);
                writer.close();
                TV.setText(Text2);
            } catch (Exception e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Reset(View view) {
        boolean storage = isExternalStorageAvailable();
        boolean permission = checkPermission();

        if (!(storage)) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (!(permission)) {
            requestPermission();
        }
        if (permission && storage) {
            try {
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write("");
                writer.close();
                TV.setText("");
                ET.setText("");
            } catch (Exception e) {
                Toast.makeText(this, "Error TV", Toast.LENGTH_SHORT).show();
            }
            try {
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME2);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write("");
                writer.close();
                ET.setText("");
            } catch (Exception e) {
                Toast.makeText(this, "Error ET", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Exit (View view){
        boolean storage = isExternalStorageAvailable();
        boolean permission = checkPermission();

        if (!(storage)) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (!(permission)) {
            requestPermission();
        }
        if(storage && permission){
            try{
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write(TV.getText().toString());
                writer.close();
            }catch (Exception e){
                Toast.makeText(this, "Error TV", Toast.LENGTH_SHORT).show();
            }

            try{
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME2);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write(ET.getText().toString());
                writer.close();
            }catch (Exception e){
                Toast.makeText(this, "Error ET", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }





    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("credits");
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("credits")) {
            Intent Di = new Intent(this, credits.class);
            startActivity(Di);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}