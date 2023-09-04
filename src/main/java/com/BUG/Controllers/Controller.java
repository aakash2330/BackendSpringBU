package com.BUG.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BUG.Entities.EventsEntity;
import com.BUG.Entities.IsAdminEntity;
import com.BUG.Entities.LoginCredentialsEntity;
import com.BUG.Entities.TokenEntity;
import com.BUG.Entities.UserAddedEntity;
import com.BUG.Entities.UserDataResponseEntity;
import com.BUG.Entities.UserEntity;
import com.BUG.Entities.generalRequest;
import com.BUG.JWT.JWT_Util;
import com.BUG.Repositories.EventsRepo;
import com.BUG.Repositories.UserRepo;
import com.BUG.Services.EventsService;
import com.BUG.Services.UserServiceInterface;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class Controller {
	
	@Autowired
	UserServiceInterface userService;
	
	@Autowired
	EventsService eventsService;
	
	@Autowired
	JWT_Util jwtUtil;
	
	@Autowired
	UserRepo userRepo;
	
	
	@GetMapping ("/token/check")
	public ResponseEntity<String> CHECK_TOKEN(@ModelAttribute TokenEntity token){
		return new ResponseEntity<String>(jwtUtil.extractUsername(token.getToken()),HttpStatus.OK);
	}
	
	@PostMapping ("/token")
	public ResponseEntity<TokenEntity> GENERATE_TOKEN(@ModelAttribute LoginCredentialsEntity loginCredentials){
		System.out.println(loginCredentials);
		return new ResponseEntity<TokenEntity>((userService.GenerateToken(loginCredentials)),HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<UserDataResponseEntity> GET_ALL_USERS(@RequestParam(value = "page_number" , defaultValue = "0" , required = false) int pageNumber, @RequestParam(value = "page_size" , defaultValue = "3" , required = false) int pageSize ){
		return new ResponseEntity<UserDataResponseEntity>(userService.getAllUsers(pageNumber,pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserEntity> GET_USER(@PathVariable("id") int id){
		return new ResponseEntity<UserEntity>(userService.findUserById(id),HttpStatus.OK);
	}
	
	@PostMapping("/users/add")
	@Transactional
	public ResponseEntity<UserAddedEntity> ADD_NEW_USER(@ModelAttribute UserEntity user){
		return new ResponseEntity<UserAddedEntity>(userService.addNewUser(user),HttpStatus.OK);
	//	userService.insertUserProcedure(user.getId(),user.getContact(),user.getEmail(),user.getPassword(),user.getProfilePicture(),user.getRole(),user.getUsername());
	//	userRepo.ins(user.getId(),user.getContact(),user.getEmail(),user.getPassword(),user.getProfilePicture(),user.getRole(),user.getUsername());
	//	userRepo.insertNewUser(user);

	
	}
	
	@GetMapping("/event/get")
	public ResponseEntity<List<EventsEntity>> GET_PAGINATED_EVENTS(@RequestParam(value = "page_num" , defaultValue = "0" , required = false) int pageNum , @RequestParam(value = "page_size" , defaultValue = "0" , required = false)int pageSize){
		return new ResponseEntity<List<EventsEntity>>(eventsService.getAllEvents(pageNum, pageSize),HttpStatus.OK);
	}
	
	@PostMapping("/events/add")
	public ResponseEntity<String> ADD_NEW_EVENT(@ModelAttribute EventsEntity e){
		return new ResponseEntity<String>(eventsService.addNewEvent(e),HttpStatus.OK);
	}
	
	@GetMapping("/username/{id}")
	@Transactional
	public ResponseEntity<UserEntity> FINDBYUSERNAME(@PathVariable("id") String username){
		return new ResponseEntity<UserEntity>(userRepo.findByusername("username"),HttpStatus.OK);
	}
	
	@PostMapping("/checkAdmin")
	public ResponseEntity<IsAdminEntity> CHECK_ADMIN(@ModelAttribute TokenEntity token){
		System.out.println(token);
		return new ResponseEntity<IsAdminEntity>(userService.checkAdmin(token.getToken()),HttpStatus.OK);
	}
	
	@PutMapping("/updatePassword")
	@Transactional
	public ResponseEntity<generalRequest> CHANGE_PASSWORD(@RequestHeader("Authorization") String header ,@ModelAttribute("password") String password){
	return new ResponseEntity<generalRequest>(userService.changePassword(password,header),HttpStatus.OK);
	
	}
	
	@PostMapping("/generateOtp")
	public ResponseEntity<generalRequest> GENERATE_OTP(@ModelAttribute("email") String email){
		return new ResponseEntity<generalRequest>(userService.GenerateOTP(email),HttpStatus.OK);
	}
	
	@PostMapping("/checkOtp")
	public ResponseEntity<generalRequest> CHECK_OTP(@ModelAttribute("otp") String otp){
		return new ResponseEntity<generalRequest>(userService.checkOtp(otp),HttpStatus.OK);
	}
}
