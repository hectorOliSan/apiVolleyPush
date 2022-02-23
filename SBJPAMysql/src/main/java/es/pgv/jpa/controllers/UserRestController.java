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

import es.pgv.jpa.models.User;
import es.pgv.jpa.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	UserRepository userRepository;

	// Get All Users
	// URL: http://localhost:8080/api/users
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Create a new User
	// URL: http://localhost:8080/api/createUser
	// Object json: {"name":"jose","username":"josejesus"}
	@PostMapping("/createUser")
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	// Get a User by id
	// URL: http://localhost:8080/api/users/1
	@GetMapping("/users/{id}")
	public User getNoteById(@PathVariable(value = "id") Long id) {
		return userRepository.findById(id).orElse(null);
	}

	// Update a User
	// URL: http://localhost:8080/api/updateUser/1
	// Object json: {"name":"RosaUpdate","username":"Marfil"}
	@PutMapping("/updateUser/{id}")
	public User updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails) {

		User user = userRepository.findById(id).orElse(null);

		user.setName(userDetails.getName());
		user.setUsername(userDetails.getUsername());

		User updatedNote = userRepository.save(user);
		return updatedNote;
	}

	// Delete a User
	// URL: http://localhost:8080/api/deleteUser/6
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {

		User note = userRepository.findById(id).orElse(null);

		userRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
