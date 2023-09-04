package com.BUG.Services;
import org.springframework.stereotype.Service;

import com.BUG.Entities.IsAdminEntity;
import com.BUG.Entities.LoginCredentialsEntity;
import com.BUG.Entities.TokenEntity;
import com.BUG.Entities.UserAddedEntity;
import com.BUG.Entities.UserDataResponseEntity;
import com.BUG.Entities.UserEntity;
import com.BUG.Entities.generalRequest;

@Service
public interface UserServiceInterface {
	public UserDataResponseEntity getAllUsers( int pageNumber,int pageSize);
	public UserAddedEntity addNewUser(UserEntity user);
	public TokenEntity GenerateToken(LoginCredentialsEntity loginCredentials);
	public UserEntity findUserById(int id);
	public IsAdminEntity checkAdmin(String token);
	public generalRequest changePassword(String newPassword , String username);
	public generalRequest GenerateOTP(String email);
	public generalRequest checkOtp(String otp);
//	public void insertUserProcedure(int id ,String contact,String email,String password,String profile_picture,String role,String username);
}