package com.utic.agendalite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.utic.agendalite.basedatos.DbContactos;
import com.utic.agendalite.entidad.Contactos;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo;
    Button btnGuardar;
    FloatingActionButton btnEditar, btnEliminar;
    boolean correcto = false;
    Contactos contacto;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.etNombre);
        txtTelefono = findViewById(R.id.etTelefono);
        txtCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnActualizar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEditar.setVisibility(View.INVISIBLE);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null) {
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreo());
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") &&
                        !txtTelefono.getText().toString().equals("")) {
                    correcto = dbContactos.editarContacto(id, txtNombre.getText().toString(),
                            txtTelefono.getText().toString(), txtCorreo.getText().toString());

                    if (correcto) {
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO!",
                                Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "CAMPOS OBLIGATORIOS VACIO!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro() {
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
