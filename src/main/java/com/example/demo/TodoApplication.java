package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
@RequestMapping("/api/todos")
public class TodoApplication {
	@Autowired
	private TodoRepository todorepository;
	
	@GetMapping()
	public List<Todo> getTodos(){
		
		return todorepository.findAll();
	}
	
	@PostMapping()
	public Todo createTodo(@RequestBody Todo todo) {
		
		return todorepository.save(todo);
	}
	@PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        Optional<Todo> optionalTodo = todorepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo existingTodo = optionalTodo.get();
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setCompleted(todo.isCompleted());;
            Todo updatedTodo = todorepository.save(existingTodo);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteTodo(@PathVariable int id) {
	        Optional<Todo> todo = todorepository.findById(id);
	        if (todo.isPresent()) {
	               
	        todorepository.deleteById(id);
	        return new ResponseEntity<>( HttpStatus.OK);}
	        else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }}
	        
	

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
