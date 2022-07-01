package in.expansetrackerapi.service;

import in.expansetrackerapi.entity.User;
import in.expansetrackerapi.entity.UserModel;

public interface UserService 
{

	public User createUser(UserModel uModel);
	
//	public  User readUser(Long id);  // here we will not get the all user information beacause user do not have access to read all user data
	public  User readUser();
	
//	public User UpdateUser(UserModel user, Long id);
	public User UpdateUser(UserModel user);
	
//	public void deleteUser(Long id);
	public void deleteUser();
	
    public User getLoggedInUser();
}
