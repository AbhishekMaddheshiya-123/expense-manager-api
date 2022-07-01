 package in.expansetrackerapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.expansetrackerapi.entity.User;
import in.expansetrackerapi.entity.UserModel;
import in.expansetrackerapi.excpetions.ItemAlreadyExitsException;
import in.expansetrackerapi.excpetions.ResourseNotFoundException;
import in.expansetrackerapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
   @Autowired
   private UserRepository userRepos;
    
   @Autowired
   private PasswordEncoder bcriptEncoder;
	@Override
	public User createUser(UserModel uModel)
	{
		if(userRepos.existsByEmail(uModel.getEmail()))
		{
			throw new ItemAlreadyExitsException("User is already registe with this Email " +  uModel.getEmail());
		}
		User newUser = new User();
		BeanUtils.copyProperties(uModel, newUser);
		newUser.setPassword(bcriptEncoder.encode(newUser.getPassword()));
		return userRepos.save(newUser);
	}
	

//	@Override
//	public User readUser(Long id) 
//	{
//		
//		return userRepos.findById(id).orElseThrow(()-> new ResourseNotFoundException("User not found for the is " + id));
//	}


	@Override
	public User readUser() 
	{
		 Long id = getLoggedInUser().getId();
		return userRepos.findById(id).orElseThrow(()-> new ResourseNotFoundException("User not found for the is " + id));
	}
	
	
	
//	@Override
//	public User UpdateUser(UserModel user, Long id)
//	{
//		User oldUser = readUser(id);
//		oldUser.setName(user.getName()!=null? user.getName():oldUser.getName());
//		oldUser.setEmail(user.getEmail()!=null? user.getEmail():oldUser.getEmail());
//		oldUser.setPassword(user.getPassword()!=null? bcriptEncoder.encode(user.getPassword()):oldUser.getPassword());
//		oldUser.setAge(user.getAge()!=null? user.getAge():oldUser.getAge());
//		return userRepos.save(oldUser);
//	}
	
	@Override
	public User UpdateUser(UserModel user)
	{
		User oldUser = readUser();
		oldUser.setName(user.getName()!=null? user.getName():oldUser.getName());
		oldUser.setEmail(user.getEmail()!=null? user.getEmail():oldUser.getEmail());
		oldUser.setPassword(user.getPassword()!=null? bcriptEncoder.encode(user.getPassword()):oldUser.getPassword());
		oldUser.setAge(user.getAge()!=null? user.getAge():oldUser.getAge());
		return userRepos.save(oldUser);
	}


//	@Override
//	public void deleteUser(Long id) 
//	{
//		User exitingUser = readUser(id);
//		 userRepos.delete(exitingUser);
//	}
	
	@Override
	public void deleteUser() 
	{
		User exitingUser = readUser();
		 userRepos.delete(exitingUser);
	}

// this method will be use for save the expenses when user logged in
	@Override
	public User getLoggedInUser() 
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepos.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found by id " + email));
	}


	

}
