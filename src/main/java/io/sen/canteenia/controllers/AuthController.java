package io.sen.canteenia.controllers;

import io.sen.canteenia.email.EmailSenderService;
import io.sen.canteenia.email.EmailUtils;
import io.sen.canteenia.models.Canteen;
import io.sen.canteenia.models.ERole;
import io.sen.canteenia.models.Role;
import io.sen.canteenia.models.User;
import io.sen.canteenia.payload.request.LoginRequest;
import io.sen.canteenia.payload.request.ResetPasswordRequest;
import io.sen.canteenia.payload.request.SignupRequest;
import io.sen.canteenia.payload.response.JwtResponse;
import io.sen.canteenia.payload.response.MessageResponse;
import io.sen.canteenia.repository.CanteenRepository;
import io.sen.canteenia.repository.RoleRepository;
import io.sen.canteenia.repository.UserRepository;
import io.sen.canteenia.security.jwt.JwtUtils;
import io.sen.canteenia.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CanteenRepository canteenRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailUtils emailUtils;

	@Autowired
	private EmailSenderService emailSenderService;

	@Value("${io.sen.canteenia.serverURL}")
	private String serverURL;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;

				case "owner":
					Role ownerRole = roleRepository.findByName(ERole.ROLE_OWNER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(ownerRole);

					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/forget-password")
	public ResponseEntity<?> forgetPassword(@Valid @RequestParam("email") String email) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error: User is not found."));

		emailSenderService.sendEmail(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("hemanghp18@gmail.com");
				message.setTo(email);
				message.setSubject("Reset Password For McDA's");
				message.setText(emailUtils.getPasswordResetEmailTemplate(user.getUsername(),
								serverURL+"/api/auth/reset-password?token="+ jwtUtils.genrateTokenFromPasswordHash(user.getUsername(), user.getPassword()) + "&email=" + email),
						true);
			}
		});

		return ResponseEntity.ok("Mail Sent");

	}

	@GetMapping("/reset-password")
	public ResponseEntity<?> resetGetPassword(@Valid @RequestParam("token") String token, @Valid @RequestParam("email") String email) {

		return ResponseEntity.ok(emailUtils.getResetPasswordTemplate(email,token));

	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPostPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

		User user= userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new RuntimeException("Error: User is not found."));

		if(jwtUtils.validatePasswordToken(resetPasswordRequest.getToken(),user.getPassword()).equals(user.getUsername()))
		{
			user.setPassword(encoder.encode(resetPasswordRequest.getPlainUserPassword()));
			userRepository.save(user);
			return ResponseEntity.ok("{\"status\":\"ok\"}");
		}
		else
		{
			return ResponseEntity.badRequest().body("{\"status\":\"error: Link Used\"}");
		}

	}

	@GetMapping("/canteen-details")
	@PreAuthorize("hasRole('OWNER')")
	public ResponseEntity<?> getCanteenDetails() {

		UserDetailsImpl userDetails =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();
		user.setId(userDetails.getId());
		Canteen canteen = canteenRepository.findByOwner(user).orElseThrow(() -> new RuntimeException("Error: Canteen is not found."));
		return ResponseEntity.ok(canteen);
	}



}
