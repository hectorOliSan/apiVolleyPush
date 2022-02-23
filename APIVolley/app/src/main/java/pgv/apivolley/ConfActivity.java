package pgv.apivolley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ConfActivity extends AppCompatActivity {
    private EditText etIp, etApi, etGet, etPost, etPut, etDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String urlIp = sp.getString("ip", "http://10.0.2.2:8080/");
        String urlApi = sp.getString("api", "SBJPAMysql-0.0.1-SNAPSHOT/api/");
        String urlGet = sp.getString("get", "libros");
        String urlPost = sp.getString("post", "createLibro");
        String urlPut = sp.getString("put", "updateLibro");
        String urlDel = sp.getString("del", "deleteLibro");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        etIp = findViewById(R.id.etIp);
        etIp.setText(urlIp);

        etApi = findViewById(R.id.etApi);
        etApi.setText(urlApi);

        etGet = findViewById(R.id.etGet);
        etGet.setText(urlGet);

        etPost = findViewById(R.id.etPost);
        etPost.setText(urlPost);

        etPut = findViewById(R.id.etPut);
        etPut.setText(urlPut);

        etDel = findViewById(R.id.etDel);
        etDel.setText(urlDel);

        Button btnG = findViewById(R.id.btnG);
        btnG.setOnClickListener(view -> {
            SharedPreferences.Editor e = sp.edit();
            e.putString("ip", etIp.getText().toString());
            e.putString("api", etApi.getText().toString());
            e.putString("get", etGet.getText().toString());
            e.putString("post", etPost.getText().toString());
            e.putString("put", etPut.getText().toString());
            e.putString("del", etDel.getText().toString());
            e.apply();

            Intent intent =
                    new Intent(ConfActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return false;
    }
}