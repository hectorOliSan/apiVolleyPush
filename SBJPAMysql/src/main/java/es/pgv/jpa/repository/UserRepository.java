package es.pgv.jpa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.pgv.jpa.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
