package pgv.apivolley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LibroActivity extends AppCompatActivity {
    private String urlIp, urlApi, urlPost, urlPut, urlDel;

    private EditText etIsbn, etTitulo, etAutor, etStock, etPrecio;
    private Libro l;
    private long codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        urlIp = sp.getString("ip", "http://10.0.2.2:8080/");
        urlApi = sp.getString("api","SBJPAMysql-0.0.1-SNAPSHOT/api/");
        urlPost = sp.getString("post","createLibro");
        urlPut = sp.getString("put","updateLibro");
        urlDel = sp.getString("del","deleteLibro");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView etLibro = findViewById(R.id.etLibro);
        etIsbn = findViewById(R.id.etIsbn);
        etTitulo = findViewById(R.id.etTitulo);
        etAutor = findViewById(R.id.etAutor);
        etStock = findViewById(R.id.etStock);
        etPrecio = findViewById(R.id.etPrecio);

        Button btnElim = findViewById(R.id.btnElim);
        Space space = findViewById(R.id.space);
        Button btn = findViewById(R.id.btn);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            etLibro.setText(R.string.editL);

            Log.d("test", "LibroActivity: " + bundle.getSerializable("LIBRO"));
            l  = (Libro) bundle.getSerializable("LIBRO");

            codigo = l.getCodigo();

            etIsbn.setText(l.getIsbn());
            etTitulo.setText(l.getTitulo());
            etAutor.setText(l.getAutor());
            etStock.setText(String.valueOf(l.getStock()));
            etPrecio.setText(String.valueOf(l.getPrecio()));

            btn.setText(R.string.actu);

        } else {
            etLibro.setText(R.string.crearL);

            btnElim.setVisibility(View.GONE);
            space.setVisibility(View.GONE);

            btn.setText(R.string.crear);
        }

        btnElim.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LibroActivity.this);
            builder.setTitle(R.string.elimL);
            builder.setMessage("¿Seguro que quieres eliminar el Libro?");

            builder.setPositiveButton("Sí", (dialog, id) -> {
                dialog.dismiss();
                deleteData();
            });
            builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());


            AlertDialog alert = builder.create();
            alert.show();
        });

        btn.setOnClickListener(view -> {
            if(comprobar()) {
                if(btn.getText().equals("crear")) createData();
                else if(btn.getText().equals("actualizar")) putData();

            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(LibroActivity.this);
                alert.setCancelable(true);
                alert.setTitle("ERROR:");
                alert.setMessage("Debes Rellenar TODOS los Campos");
                alert.show();
            }
        });
    }

    private boolean comprobar() {
        return !(etIsbn.getText().toString().equals("") ||
                    etTitulo.toString().equals("") ||
                    etAutor.toString().equals("") ||
                    etStock.toString().equals("") ||
                    etPrecio.toString().equals("")
                );
    }

    private void createData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = urlIp.concat(urlApi).concat(urlPost);

        Map<String, String> params = new HashMap<>();
            params.put("isbn", etIsbn.getText().toString());
            params.put("titulo", etTitulo.getText().toString());
            params.put("autor", etAutor.getText().toString());
            params.put("stock", etStock.getText().toString());
            params.put("precio", etPrecio.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                response -> {
                    Log.d("test", "createLibro: " + response);

                    Intent intent =
                            new Intent(LibroActivity.this, MainActivity.class);
                    startActivity(intent);

                }, error -> {
                    Log.d("test", "ERROR(createLibro): " + error + ", " + error.getMessage());

                    AlertDialog.Builder alert = new AlertDialog.Builder(LibroActivity.this);
                    alert.setCancelable(true);
                    alert.setTitle("ERROR al crear Libro:");
                    alert.setMessage(error + ", " + error.getMessage());
                    alert.show();
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }

    private void putData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d("test", "URL(POST): " + urlIp.concat(urlApi).concat(urlPut).concat("/").concat(String.valueOf(codigo)));
        String url = urlIp.concat(urlApi).concat(urlPut).concat("/").concat(String.valueOf(codigo));

        Map<String, String> params = new HashMap<>();
            params.put("isbn", etIsbn.getText().toString());
            params.put("titulo", etTitulo.getText().toString());
            params.put("autor", etAutor.getText().toString());
            params.put("stock", etStock.getText().toString());
            params.put("precio", etPrecio.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, new JSONObject(params),
                response -> {
                    Log.d("test", "updateLibro: " + response);

                    Intent intent =
                            new Intent(LibroActivity.this, MainActivity.class);
                    startActivity(intent);

                },error -> {
                    Log.d("test", "ERROR(updateLibro): " + error.getMessage());

                    AlertDialog.Builder alert = new AlertDialog.Builder(LibroActivity.this);
                    alert.setCancelable(true);
                    alert.setTitle("ERROR al actualizar Libro:");
                    alert.setMessage(error + ", " + error.getMessage());
                    alert.show();
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }

    private void deleteData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = urlIp.concat(urlApi).concat(urlDel).concat("/").concat(String.valueOf(codigo));

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    Log.d("test", "deleteLibro: " + l.toString());

                    Intent intent =
                            new Intent(LibroActivity.this, MainActivity.class);
                    startActivity(intent);

                },error -> {
                    Log.d("test", "ERROR(deleteLibro): " + error.getMessage());

                    AlertDialog.Builder alert = new AlertDialog.Builder(LibroActivity.this);
                    alert.setCancelable(true);
                    alert.setTitle("ERROR al eliminar Libro:");
                    alert.setMessage(error + ", " + error.getMessage());
                    alert.show();
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return false;
    }
}