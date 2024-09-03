package com.utic.agendalite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.utic.agendalite.adaptador.ListaContactosAdapter;
import com.utic.agendalite.basedatos.DbContactos;
import com.utic.agendalite.entidad.Contactos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    FloatingActionButton btnAgregar;
    ListaContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.etBuscar);
        listaContactos = findViewById(R.id.listaContactos);
        btnAgregar = findViewById(R.id.btnAgregar);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        DbContactos dbContactos = new DbContactos(MainActivity.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarRegistro();
            }
        });



        txtBuscar.setOnQueryTextListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarContactos();
    }

    public void cargarContactos() {
        DbContactos dbContactos = new DbContactos(MainActivity.this);
        listaArrayContactos = dbContactos.mostrarContactos();
        adapter = new ListaContactosAdapter(listaArrayContactos);
        listaContactos.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.menuAgregar){
            agregarRegistro();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void agregarRegistro(){
        Intent intent = new Intent(this, AgregarActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}
