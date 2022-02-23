package es.pgv.jpa.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.pgv.jpa.models.Libro;
import es.pgv.jpa.repository.LibroRepository;

@RestController
@RequestMapping("/api")
public class LibroRestController {

	@Autowired
	LibroRepository libroRepository;

	// Obtiene todos los Libros
	// URL: http://localhost:8080/api/libros
	@GetMapping("/libros")
	public List<Libro> getAllLibros() {
		return libroRepository.findAll();
	}

	// Crea un nuevo Libro
	// URL: http://localhost:8080/api/createLibro
	// Object json: {"isbn":"isbn","titulo":"El Quijote", "autor":"Cervantes", "stock":5, "precio":15.50}
	@PostMapping("/createLibro")
	public Libro createLibro(@Valid @RequestBody Libro libro) {
		return libroRepository.save(libro);
	}

	// Obtiene un Libro por codigo
	// URL: http://localhost:8080/api/libros/1
	@GetMapping("/libros/{codigo}")
	public Libro getNoteById(@PathVariable(value = "codigo") Long codigo) {
		return libroRepository.findById(codigo).orElse(null);
	}

	// Actualizar un Libro
	// URL: http://localhost:8080/api/updateLibro/1
	// Object json: {"isbn":"isbn","titulo":"El Quijote", "autor":"Miguel de Cervantes", "stock":20, "precio":15.00}
	@PutMapping("/updateLibro/{codigo}")
	public Libro updateUser(@PathVariable(value = "codigo") Long codigo, @Valid @RequestBody Libro libroDetails) {

		Libro libro = libroRepository.findById(codigo).orElse(null);

		libro.setIsbn(libroDetails.getIsbn());
		libro.setTitulo(libroDetails.getTitulo());
		libro.setIsbn(libroDetails.getIsbn());
		libro.setAutor(libroDetails.getAutor());
		libro.setStock(libroDetails.getStock());
		libro.setPrecio(libroDetails.getPrecio());

		Libro updatedNote = libroRepository.save(libro);
		return updatedNote;
	}

	// Borrar un Libro
	// URL: http://localhost:8080/api/deleteLibro/6
	@DeleteMapping("/deleteLibro/{codigo}")
	public ResponseEntity<?> deleteLibro(@PathVariable(value = "codigo") Long codigo) {

		Libro note = libroRepository.findById(codigo).orElse(null);

		libroRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
