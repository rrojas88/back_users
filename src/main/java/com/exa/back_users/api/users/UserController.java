
package com.exa.back_users.api.users;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exa.back_users.api.users.utils.Response;

import io.micrometer.core.annotation.Timed;


@RestController
@RequestMapping("/users")
public class UserController {

    private static List<User> list = new ArrayList<>();
	private Response resp;

	public UserController(){
		list.add(new User(1, "Pepe", "1010", true ));
		list.add(new User(2, "Lucas", "2020", true ));
		list.add(new User(3, "Lupe", "3030", true ));
		list.add(new User(4, "Sara", "4040", true ));

		resp = new Response();
	}
    
    @GetMapping("/test")
    public ResponseEntity<?> test(){
		resp.setMessage("Todo Ok !");
        return new ResponseEntity<>( resp, HttpStatus.OK );
    }


	@GetMapping("/by-name")
	public ResponseEntity<?> getByName( @RequestParam("name") String name ){
		User user = list.stream()
			.filter( u -> u.getName().equals(name) )
			.findFirst()
			.get();

		String message = "Encontrado usuario con nombre: " + name;
		if( user != null )
			resp.setData(user);
		else message = "No " + message;
		resp.setMessage(message);
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}


	@GetMapping("")
	public ResponseEntity<?> getAll(){
		resp.setData(list);
		resp.setMessage("Lista de usuarios");
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}


	@GetMapping("/{id}")
	@Timed("user.by.id.timer")
	public ResponseEntity<?> getById( @PathVariable("id") Integer id ){
		try {
			User user = list.stream()
				.filter( u -> u.getId().equals(id) )
				.findFirst()
				.get();
		
			String message = "Encontrado usuario con Id: " + id;
			if( user != null )
				resp.setData(user);
			else message = "No " + message;
			resp.setMessage(message);
			return new ResponseEntity<>( resp, HttpStatus.OK );
		}
		catch (Exception e) {
			String message = "Error buscando por ID: " + id;
			System.out.println( message );
			System.out.println( e.getMessage() );
			resp.setMessage(message);
			return new ResponseEntity<>( resp, HttpStatus.OK );
		}
		
	}


	@PostMapping()
	public ResponseEntity<?> save( @RequestBody User user){
		list.add( user );
		resp.setData(list);
		resp.setMessage("Usuario agregado");
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> update( @PathVariable("id") Integer id, @RequestBody User user ){
		List<User> list2 = list.stream()
			.filter( u -> u.getId().equals(id) )
			.map( u -> {
				u.setId(id);
				u.setName(user.getName());
				u.setCc(user.getCc());
				u.setStatus(user.isStatus());
				return u;
			})
			.collect(Collectors.toList());

		resp.setData(list);
		resp.setMessage("Usuario actualizado");
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById( @PathVariable("id") Integer id ){
		User userDelete = list.stream()
			.filter( u -> u.getId().equals(id) )
			.findFirst()
			.get();
		list.remove( userDelete );
		
		resp.setData(list);
		resp.setMessage("Usuario eliminado");
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}


}
