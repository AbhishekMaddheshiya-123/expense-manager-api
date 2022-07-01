package in.expansetrackerapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.expansetrackerapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

   boolean existsByEmail(String email);   // it is used for create custom exception  for email
   
   Optional<User>findByEmail(String email);
   
   
   
  
}
