package in.expansetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.expansetrackerapi.entity.User;
import in.expansetrackerapi.entity.UserModel;
import in.expansetrackerapi.service.UserService;

@RestController
public class UserController
{
	@Autowired
	private UserService userService;
	
	
	
//	@GetMapping("user/{id}")
	@GetMapping("/profile")
	public ResponseEntity<User> readUser()
	{
//		return new ResponseEntity<User>(userService.readUser(id),HttpStatus.OK);
		return new ResponseEntity<User>(userService.readUser(),HttpStatus.OK);
	}
	
//	@PutMapping("user/{id}")
	@PutMapping("/profile")
	public ResponseEntity<User> Update(@RequestBody UserModel user)
	{
//		return new ResponseEntity<User>(userService.UpdateUser(user, id), HttpStatus.OK);
		return new ResponseEntity<User>(userService.UpdateUser(user), HttpStatus.OK);
		
	}
	
//	@DeleteMapping("user/{id}")
	@DeleteMapping("/deactivate")
	public ResponseEntity<Void> delete()
	{
//		userService.deleteUser(id);
		userService.deleteUser();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}
