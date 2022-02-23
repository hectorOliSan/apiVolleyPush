package pgv.apivolley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String urlIp, urlApi, urlGet;

    private final ArrayList<Libro> datos = new ArrayList<>();
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        urlIp = sp.getString("ip", "http://10.0.2.2:8080/");
        urlApi = sp.getString("api","SBJPAMysql-0.0.1-SNAPSHOT/api/");
        urlGet = sp.getString("get", "libros");

        rv = findViewById(R.id.rvTareas);
        rv.setHasFixedSize(true);

        FloatingActionButton fb = findViewById(R.id.fbTarea);
        fb.setColorFilter(Color.WHITE);

        fb.setOnClickListener(view -> {
            Intent intent =
                    new Intent(MainActivity.this, LibroActivity.class);
            startActivity(intent);
        });

        fcm();

        getData();
    }

    public void fcm () {
        // Obtener el id del dispositivo
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("PUSH:", "Installation ID: " + task.getResult());
                    } else {
                        Log.e("PUSH:", "Unable to get Installation ID");
                    }
                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("PUSH:", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    Log.d("PUSH Token:", token);

                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                });

    }

    private void getData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = urlIp.concat(urlApi).concat(urlGet);

        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    if (response.length() > 0) {
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Libro l = new Libro();
                                l.setCodigo(obj.getLong("codigo"));
                                l.setIsbn(obj.getString("isbn"));
                                l.setTitulo(obj.getString("titulo"));
                                l.setAutor(obj.getString("autor"));
                                l.setStock(obj.getInt("stock"));
                                l.setPrecio(Float.parseFloat(obj.getString("precio")));

                                datos.add(l);

                            } catch (JSONException e) {
                                Log.e("test", "ERROR(JSON): " + e + ", " + e.getMessage());

                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setCancelable(true);
                                alert.setTitle("ERROR al obtener Libros::");
                                alert.setMessage(e + ", " + e.getMessage());
                                alert.show();
                            }
                        }

                        AdaptadorLista al = new AdaptadorLista(datos);
                        al.setOnClickListener(view -> {
                            int v = rv.getChildAdapterPosition(view);
                            Log.d("test", datos.get(v).toString());

                            Intent intent =
                                    new Intent(MainActivity.this, LibroActivity.class);

                            Bundle b = new Bundle();
                            b.putSerializable("LIBRO", datos.get(v));
                            intent.putExtras(b);
                            startActivity(intent);
                        });

                        rv.setAdapter(al);
                        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

                    } else {
                        Log.d("test", "ERROR(Response): Libros not found");

                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setCancelable(true);
                        alert.setTitle("ERROR al obtener Libros:");
                        alert.setMessage("No se encuentran Libros");
                        alert.show();
                    }

                }, error -> {
                    Log.e("test", "ERROR: " + error);

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setCancelable(true);
                    alert.setTitle("ERROR al obtener Libros:");
                    alert.setMessage(error + ", " + error.getMessage());
                    alert.show();
                }
        );

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuConf:
                Intent intent =
                        new Intent(MainActivity.this, ConfActivity.class);
                startActivity(intent);
                return true;

            case R.id.mnuAcerca:
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setCancelable(true);
                alert.setTitle("Acerca de:");
                alert.setMessage("Héctor Olivares Sánchez - PGV 2022");
                alert.show();
                return true;

            case R.id.mnuSalir:
                finishAffinity();
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}