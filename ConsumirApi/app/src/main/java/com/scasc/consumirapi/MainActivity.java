package com.scasc.consumirapi;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnMostrar, btnGuardar, btnLimpiar;
    EditText etIdUsuario, etNombreApellido, etAlias, etTelefono;
    String idusuario, nombreapellido, alias, telefono;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMostrar = findViewById(R.id.btnMostrar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        etIdUsuario = findViewById(R.id.etIdUsuario);
        etNombreApellido = findViewById(R.id.etNombreApellido);
        etAlias = findViewById(R.id.etAlias);
        etTelefono = findViewById(R.id.etTelefono);

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarTextos();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idusuario = etIdUsuario.getText().toString().trim();
                String url = "https://jaahosting.com/apidata/recuperar.php?idusuario=" + idusuario;
                recuperarData(url);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreapellido = etNombreApellido.getText().toString().trim();
                alias = etAlias.getText().toString().trim();
                telefono = etTelefono.getText().toString().trim();
                String url = "https://jaahosting.com/apidata/insertar.php?nombreapellido="
                        + nombreapellido + "&alias="
                        + alias + "&telefono=" + telefono;
                guardarData(url);
            }
        });
    }

    private void recuperarData(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Recuperando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            String success = jsonObject.getString("success");
                            if (success.equalsIgnoreCase("true")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    // Aquí puedes procesar los datos recuperados
                                    etNombreApellido.setText(jsonArray.getJSONObject(i).getString("nombreapellido"));
                                    etAlias.setText(jsonArray.getJSONObject(i).getString("alias"));
                                    etTelefono.setText(jsonArray.getJSONObject(i).getString("telefono"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // Manejar el error de la solicitud
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void guardarData(String url) {
        // Implementa la lógica para guardar los datos
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando...");
        progressDialog.show();
        Toast exito=Toast.makeText(this,"Guardado exitoso",Toast.LENGTH_LONG);
        Toast error=Toast.makeText(this,"Error al guardar",Toast.LENGTH_LONG);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(response.equals("false")){
                            error.show();
                        }else{
                            exito.show();
                            limpiarTextos();
                            etIdUsuario.setText(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // Manejar el error de la solicitud
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void limpiarTextos() {
        // Implementa la lógica para limpiar los campos de texto
        etNombreApellido.setText("");
        etAlias.setText("");
        etTelefono.setText("");
        etIdUsuario.setText("");
    }
}
