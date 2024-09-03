package com.utic.agendalite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.utic.agendalite.basedatos.DbContactos;

public class AgregarActivity extends AppCompatActivity {

    EditText etNombre, etTelefono, etCorreo;
    Button btnGuardar;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etNombre.getText().toString().equals("") &&
                        !etTelefono.getText().toString().equals("")) {

                    DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                    long id = dbContactos.insertarContacto(etNombre.getText().toString(),
                            etTelefono.getText().toString(), etCorreo.getText().toString());

                    if (id > 0) {
                        Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO",
                                Toast.LENGTH_LONG).show();

                        limpiar();
                        lista();

                    } else {
                        Toast.makeText(AgregarActivity.this, "ERROR AL GUARDAR REGISTRO",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AgregarActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void limpiar() {
        etNombre.setText("");
        etTelefono.setText("");
        etCorreo.setText("");
    }
}
