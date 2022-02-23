package es.pgv.jpa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.pgv.jpa.models.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

}
