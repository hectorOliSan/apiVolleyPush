package es.pgv.jpa.models;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "autor")
	private String autor;
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "precio")
	private float precio;

	public Libro() {
		super();
	}

	public Libro(Long codigo, String isbn, String titulo, String autor, int stock, float precio) {
		super();
		this.codigo = codigo;
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.stock = stock;
		this.precio = precio;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
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

}
