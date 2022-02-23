package pgv.apivolley;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Libro implements Serializable {
    private long codigo;
    private String isbn, titulo, autor;
    private int stock;
    private float precio;

    public Libro() {}

    public Libro(String isbn, String titulo, String autor, int stock, float precio) {
        super();
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
        this.precio = precio;
    }

    public Libro(long codigo, String isbn, String titulo, String autor, int stock, float precio) {
        super();
        this.codigo = codigo;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
        this.precio = precio;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + codigo + ") - " + isbn + " - " + titulo +
                ", " + autor + " - " + stock + ", " + precio + " €";
    }
}
