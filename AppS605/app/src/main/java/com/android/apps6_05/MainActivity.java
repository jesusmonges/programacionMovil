package com.android.apps6_05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et1=findViewById(R.id.et1);
    }
    public void verify(View v) {
        EditText et1 = findViewById(R.id.et1); // Suponiendo que et1 es el EditText donde se ingresa la clave
        String clave = et1.getText().toString();

        if (clave.equals("abc123")) {
            Intent intent = new Intent(this, Actividad2.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Clave incorrecta", Toast.LENGTH_LONG).show();
        }
    }
}