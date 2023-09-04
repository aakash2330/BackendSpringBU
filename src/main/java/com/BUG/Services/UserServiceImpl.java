package com.BUG.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.BUG.Entities.IsAdminEntity;
import com.BUG.Entities.LoginCredentialsEntity;
import com.BUG.Entities.TokenEntity;
import com.BUG.Entities.UserAddedEntity;
import com.BUG.Entities.UserDataResponseEntity;
import com.BUG.Entities.UserEntity;
import com.BUG.Entities.generalRequest;
import com.BUG.Helpers.EmailHelper;
import com.BUG.JWT.JWT_Util;
import com.BUG.Repositories.UserRepo;
import com.github.javafaker.Faker;

@Component
public class UserServiceImpl implements UserServiceInterface {
	
	@Autowired
	EmailHelper emailHelper;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	JWT_Util jwtUtil;
	
	@Autowired
	ConvertUserEntityToCustomUser userDetailsService;
	
	@Autowired
	DaoAuthenticationProvider daoAuthenticationProvider;
	
	Faker faker=new Faker();
	
	@Override
	public UserDataResponseEntity getAllUsers( int pageNumber,int pageSize ) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<UserEntity> userEntityPage = userRepo.findAll(page);
		System.out.println(userEntityPage);
		List<UserEntity> userEntitiesList=userEntityPage.getContent();
		UserDataResponseEntity userDataResponseEntity= new UserDataResponseEntity(userEntitiesList,userEntityPage.getNumber(),userEntityPage.getSize(),userEntityPage.getTotalElements(),userEntityPage.getTotalPages(),userEntityPage.isLast(),userEntityPage.isFirst());
		return userDataResponseEntity ;
		
	}

	@Override
	public UserAddedEntity addNewUser(UserEntity user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_ADMIN");
		userRepo.save(user);
		return new UserAddedEntity("USER ADDED");
	
	}

	@Override
	public TokenEntity GenerateToken(LoginCredentialsEntity loginCredentials) {
		daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword()));
		TokenEntity tokenObj= new TokenEntity(jwtUtil.generateToken(userDetailsService.loadUserByUsername(loginCredentials.getUsername())),loginCredentials.getUsername());
		System.out.println("GENERATED TOKEN"+"------"+tokenObj);
		return tokenObj;
	}

	@Override
	public UserEntity findUserById(int id) {
		return userRepo.findById(id).get();
	}

	@Override
	public IsAdminEntity checkAdmin(String token) {
		String username = jwtUtil.extractUsername(token);
		UserEntity user = userRepo.findUsername(username);
		System.out.println(user.getRole());
		boolean isAdmin;
		if(user.getRole().equals("ROLE_ADMIN")){isAdmin=true;}
		else {isAdmin=false;};
		IsAdminEntity admin = new IsAdminEntity(isAdmin);
		System.out.println(admin);
		return admin;
	}

	@Override
	public generalRequest changePassword(String newPassword , String header) {
	String token = header.substring(7);
	String username = jwtUtil.extractUsername(token);
	UserEntity user=userRepo.findByusername(username);
	if(user.getUsername()!=null) {
//	userRepo.updatePasswordProcedure(passwordEncoder.encode(newPassword), username);
	user.setPassword(passwordEncoder.encode(newPassword));
	userRepo.save(user);
	//general request:password changed=true
	return new generalRequest(true) ;}
	else {
		return new generalRequest(false);
	}
	}

	@Override
	public generalRequest GenerateOTP(String email) {
		return emailHelper.generateOtpEmail(email);
	}

	@Override
	public generalRequest checkOtp(String otp) {
		if(otp.equals("OTP")) {
			return new generalRequest(true);
		}
		else {return new generalRequest(false);}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	
//	@Autowired
//	EntityManager em;
//	
//	@Autowired
//	SessionFactory sessionFactory;
//	
//	
//	public void insertUserProcedure(int id ,String contact,String email,String password,String profile_picture,String role,String username) {
//		password = passwordEncoder.encode(password);
//		Session sess = sessionFactory.openSession();
//		sess.beginTransaction();
//		ProcedureCall query = sess.createStoredProcedureCall("inserNewUserProcedure");
//		query.registerParameter(1, Integer.class, ParameterMode.IN).enablePassingNulls(true);
//		query.registerParameter(2, String.class, ParameterMode.IN).enablePassingNulls(true);
//		query.registerParameter(3, String.class, ParameterMode.IN).enablePassingNulls(true);
//		query.registerParameter(4, String.class, ParameterMode.IN).enablePassingNulls(true);
//		query.registerParameter(5, String.class, ParameterMode.IN).enablePassingNulls(true);
//		query.registerParameter(6, String.class, ParameterMode.IN).enablePassingNulls(true);
//		query.registerParameter(7, String.class, ParameterMode.IN).enablePassingNulls(true);
//		query.setParameter(1, id);
//		query.setParameter(2, contact);
//		query.setParameter(3, email);
//		query.setParameter(4, password);
//		query.setParameter(5, profile_picture);
//		query.setParameter(6, role);
//		query.setParameter(7, username);
//		query.execute();
//		sess.getTransaction().commit();
//	    sess.close();

//		em.createNamedStoredProcedureQuery("insertProcedure").setParameter("id_in",id )
//															.setParameter("contact_in",contact )
//															.setParameter("email_in",email )
//															.setParameter("password_in",password )
//															.setParameter("profile_picture_in",profile_picture )
//															.setParameter("role_in",role )
//															.setParameter("username_in",username ).execute();
	
//	}
//	public String SaveBulkUsers()
//	{
//				
//		for(int i=1;i<=1000;i++)
//		{
//			
//			UserEntity user = new UserEntity(i,faker.name().fullName(),faker.name().lastName(),(faker.name().firstName()+"@gmail.com"),faker.phoneNumber().cellPhone(),faker.address().city(),"ROLE_USER");
//			userRepo.save(user);
//		}
//		return "100 USERS SAVED";
//		
//	}
//	
	
	

