package in.expansetrackerapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.expansetrackerapi.entity.AuthModel;
import in.expansetrackerapi.entity.JwtResponse;
import in.expansetrackerapi.entity.User;
import in.expansetrackerapi.entity.UserModel;
import in.expansetrackerapi.security.CustomUserDetailsService;
import in.expansetrackerapi.service.UserService;
import in.expansetrackerapi.util.JwtTokenUtil;

@RestController
public class AuthController 
{
	@Autowired
	public AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDeatilsService;
	
	@Autowired
	private JwtTokenUtil  jwtTokenUtil;
	
	@Autowired
	private UserService userService;
   
//	@PostMapping("/login")
//	public ResponseEntity<HttpStatus> login(@RequestBody AuthModel authModel)
//	{
//		
//		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authenticate);
//		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
//		
//	}
	
	
	/*
	 * ones we authenticate the user the next step will be generate the token and refractor the login method and her  we do 
	 * not going to return the httpStatus here we aregoing to return JWT Model Response class because it will return the JWT Tkoken
	 * 
	 * 
	 * 
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception
	{
		
	
		authenticate(authModel.getEmail(),authModel.getPassword());
		// we need to generate the jwt token
//		in order to get the jwt taken so first we need user details so we need to pass the user details to the jwt Token Payload
//		for that we need a custom user details service. so once we get the user details mo next we pass the user detils to 
//		generate the jwt token
//		so we will use util class to generate and validate the token and last we will send the tokent to client through JwtResponse
		UserDetails userDetails = userDeatilsService.loadUserByUsername(authModel.getEmail());
		String token = jwtTokenUtil.generateToken(userDetails);
	
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
		
	}
	
	
	private void authenticate(String email, String password) throws Exception 
	{  
		try 
		{
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} 
		catch (DisabledException e) 
		{
			throw new Exception("User Disabled");
		}
		catch (BadCredentialsException e) 
		{
			throw new Exception("Bad Credential");
		}
	
     }

	@PostMapping("/register")
	public ResponseEntity<User> saveUser(@Valid @RequestBody UserModel uModel)
	{
		return new ResponseEntity<User>(userService.createUser(uModel), HttpStatus.CREATED);
	}
	
	
}
