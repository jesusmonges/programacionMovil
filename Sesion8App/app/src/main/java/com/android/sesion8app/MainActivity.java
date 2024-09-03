package com.android.sesion8app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etDomicilio, etCiudad, etPais,  etTelefono, etEmail, etCPostal;
    private Button btnGuardar;

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

        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etDomicilio = (EditText) findViewById(R.id.etDireccion);
        etCiudad = (EditText) findViewById(R.id.etCiudad);
        etPais = (EditText) findViewById(R.id.etPais);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCPostal = (EditText) findViewById(R.id.etDirPostal);
        btnGuardar = (Button) findViewById(R.id.btnEnviar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar(etNombre) && validar(etApellido) && validar(etDomicilio) && validar(etCiudad) && validar(etPais) && validar(etTelefono)
                && validar(etEmail) && validar(etCPostal)){
                    Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean validar (EditText input){
        if((input.getText().toString().trim().length()) < 1){
            input.setError("Complete el campo!");
            input.requestFocus();
            return false;
        } else{
            return true;
        }
    }

}