package pgv.apivolley;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.LibroViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private final ArrayList<Libro> alLibros;

    public static class LibroViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvIsbn, tvTitulo, tvAutor, tvStock, tvPrecio;

        public LibroViewHolder(View v) {
            super(v);
            this.tvIsbn = itemView.findViewById(R.id.tvIsbn);
            this.tvTitulo = itemView.findViewById(R.id.tvTitulo);
            this.tvAutor = itemView.findViewById(R.id.tvAutor);
            this.tvStock = itemView.findViewById(R.id.tvStock);
            this.tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }

        public void bindTitular(@NonNull Libro l) {
            tvIsbn.setText(l.getIsbn());
            tvTitulo.setText(l.getTitulo());
            tvAutor.setText(l.getAutor());
            tvStock.setText(String.valueOf(l.getStock()));
            tvPrecio.setText(String.valueOf(l.getPrecio()));
        }
    }

    public AdaptadorLista(ArrayList<Libro> alLibros) {
        this.alLibros = alLibros;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_libro, viewGroup, false);

        itemView.setOnClickListener(this);
        return new LibroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int pos) {
        Libro item = alLibros.get(pos);
        holder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return alLibros.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null) listener.onClick(view);
    }
}
