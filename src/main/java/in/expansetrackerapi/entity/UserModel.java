package in.expansetrackerapi.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserModel
{
	@NotBlank(message = "Name shold not be empty")
     private String name;
    
	@NotNull(message = "Email should not be empty")
	@Email(message = "Enter a valid Email")
     private String email;
     
	@NotNull(message = "Password should not be null")
	@Size(min = 5,  message = "Password Should be atleast 5 Character")
     private String password;
     
     private Long age=0L;
}
