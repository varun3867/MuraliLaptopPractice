package in.nareshit.raghu.springsocial.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import in.nareshit.raghu.springsocial.exception.BadRequestException;
import in.nareshit.raghu.springsocial.model.AuthProvider;
import in.nareshit.raghu.springsocial.model.User;
import in.nareshit.raghu.springsocial.payload.ApiResponse;
import in.nareshit.raghu.springsocial.payload.AuthResponse;
import in.nareshit.raghu.springsocial.payload.LoginRequest;
import in.nareshit.raghu.springsocial.payload.SignUpRequest;
import in.nareshit.raghu.springsocial.repository.UserRepository;
import in.nareshit.raghu.springsocial.security.TokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("[AuthController] LoginRequset --> Email"+loginRequest.getEmail());
		System.out.println("[AuthController] LoginRequset --> Password"+loginRequest.getPassword());
		
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(),
						loginRequest.getPassword()
						)
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = tokenProvider.createToken(authentication);
		System.out.println("[AuthController] --> token is "+token);
		
	
		AuthResponse ar = new AuthResponse(token);//[varun added] --> to be removed
		System.out.println("[AuthController] --> AuthResponse is --> "+ar);//[varun added] --> to be removed
		
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use.");
		}

		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "User registered successfully@"));
	}

}
