package com.BUG.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.BUG.Entities.UserEntity;

@EnableTransactionManagement
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findByusername(String username);
	
	
	@Query(value = "SELECT * FROM user_entity e WHERE e.username = :username", 
			  nativeQuery = true)
	public UserEntity findUsername(String username);
	
	
	@Procedure(procedureName = "FIND_USER_BY_USERNAME")
	UserEntity getUserData(String user_in);
	

	@Modifying
	@Transactional
	@Query(value = "UPDATE user_entity SET password=:p WHERE username=:u" , nativeQuery = true)
	void updatePassword(@Param("p") String password , @Param("u") String username);
	
	@Procedure(procedureName = "updatePasswordProcedure")
	void updatePasswordProcedure(String password_in, String username_in);
	
//	@Transactional
//	@Modifying
//	@Query(value = "INSERT INTO user_entity(:u.getId(),:u.getContact(),:u.getEmail(),:u.getPassword(),:u.getProfilePicture(),:u.getRole(),:u.getUsername())",nativeQuery = true)
//	void insertNewUser(@Param("u") UserEntity user);
	
//	@Transactional
//	@Modifying
//	@Query(value = "INSERT INTO user_entity  VALUES (:i,:c,:e,:p,:pp,:r,:u)",nativeQuery = true)
//	void insertNewUser(@Param("i")int id ,@Param("c")String contact,@Param("e")String email,@Param("p")String password,@Param("pp")String profile_picture,@Param("r")String role,@Param("u")String username );
//	
//	@Procedure(procedureName = "inserNewUserProcedure")
//	void insertNewUser(int id ,String contact,String email,String password,String profile_picture,String role,String username );

//	@Modifying
//	@Transactional
//	@Query(value = "CALL inserNewUserProcedure(:i,:c,:e,:p,:pp,:r,:u) " , nativeQuery = true)
//	void ins(@Param("i")int id ,@Param("c")String contact,@Param("e")String email,@Param("p")String password,@Param("pp")String profile_picture,@Param("r")String role,@Param("u")String username );
//	
	
}	
