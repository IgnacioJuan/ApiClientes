package com.juanguachi.apicliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.juanguachi.apicliente.model.Cliente;
import com.juanguachi.apicliente.model.ClienteListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<Cliente> datos;

    private EditText nombretxt, apellidotxt, emailtxt;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Traer las vistas al controlador
        nombretxt = findViewById(R.id.txt_nombre);
        apellidotxt = findViewById(R.id.txt_apellido);
        emailtxt = findViewById(R.id.txt_email);
        listView = findViewById(R.id.listclientes);
        //Llamado al metodo de obtener los datos
        getdatos();
    }

    private void getdatos() {

        datos = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);

        ClienteListAdapter adapter=new ClienteListAdapter(this,R.layout.listclientesmod,datos);
        listView.setAdapter(adapter);
        String url = "http://172.24.14.221:8080/api/clientes";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                //Recibir el Json y pasar a Publicacion.
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //MANEJAMOS EL ERROR.
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        //Enviamos una peticion tipo get al endpoiint
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void pasarJson(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            Cliente cliente = new Cliente();

            try {
                json = array.getJSONObject(i);

                cliente.setApellido(json.getString("apellido"));
                cliente.setEmail(json.getString("email"));
                cliente.setNombre(json.getString("nombre"));
                cliente.setId(json.getString("id"));

                datos.add(cliente);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        arrayAdapter.notifyDataSetChanged();
    }

    public void insertar(View v) {
        String url = "http://172.24.14.221:8080/api/Guardarclientes";
        rq = Volley.newRequestQueue(this);
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("nombre", nombretxt.getText().toString());
            parametros.put("apellido", apellidotxt.getText().toString());
            parametros.put("email", emailtxt.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.POST,
                url,
                parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Los datos se guardaron correctamente", Toast.LENGTH_SHORT).show();
                            nombretxt.setText("");
                            apellidotxt.setText("");
                            emailtxt.setText("");
                            Toast.makeText(getApplicationContext(), response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimiento);
    }

    public void Recargar(View view) {
        getdatos();
    }
}