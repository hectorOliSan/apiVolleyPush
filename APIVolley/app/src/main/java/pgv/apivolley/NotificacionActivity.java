package pgv.apivolley;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificacionActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);
        setTitle("Datos de la notificación");
        TextView textview = findViewById(R.id.textview);
        Bundle b = getIntent().getExtras();
        textview.setText(b.getString("title") + "\n" + b.getString("message"));
    }
}