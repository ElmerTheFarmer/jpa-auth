package ajc.jpa.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ajc.jpa.models.CustomRole;
import ajc.jpa.models.CustomUser;
import ajc.jpa.services.RoleServiceInterface;
import ajc.jpa.services.UserServiceInterface;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class SecurityAPIController {
	
	private UserServiceInterface userServiceInterface;	
    private RoleServiceInterface roleServiceInterface;
    private PasswordEncoder passwordEncoder;
    private String h2ConsolePath;
    	
    public SecurityAPIController(UserServiceInterface userServiceInterface, RoleServiceInterface roleServiceInterface,
			PasswordEncoder passwordEncoder, String h2ConsolePath) {
		super();
		this.userServiceInterface = userServiceInterface;
		this.roleServiceInterface = roleServiceInterface;
		this.passwordEncoder = passwordEncoder;
		this.h2ConsolePath = h2ConsolePath;
	}

//	@Value("${spring.h2.console.path}")
//    private String h2ConsolePath;
	
	@GetMapping({"/api"})
    public void redirectToHome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
    }
	
    @GetMapping("/home")
    public Map<String, String> homePage() {
        Map<String, String> routes = new HashMap<>();
        routes.put("admin", "/admin");
        routes.put("details", "/details");
        routes.put("h2", h2ConsolePath);
        return routes;
    }
	
	@GetMapping("/users")
    public List<CustomUser> allUsersOrById(@RequestParam(required = false) Long id){
		List<CustomUser> userList = new ArrayList<>();
		if(id == null) {
			userList = userServiceInterface.getAllUsers();			
		} else {
			userList.add(userServiceInterface.getById(id));
		}	
		return userList;
	}
	
	@PostMapping("/add-user")
    public CustomUser registerUser(@RequestBody CustomUser user, String role) {
        // Hash du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Récupération du rôle USER
        CustomRole roleUser = roleServiceInterface.getByRoleName(role);

        // Attribution du rôle à l'utilisateur
        user.setRoles(List.of(roleUser));

        // Retour de l'utilisateur créé
        return userServiceInterface.addUser(user);
    }
	
//	@PostMapping("/change-role")
//	public CustomUser changeRole(@RequestBody Long id) {
//		CustomUser user = userServiceInterface.getById(id);
//		
//	}
	
	

}
