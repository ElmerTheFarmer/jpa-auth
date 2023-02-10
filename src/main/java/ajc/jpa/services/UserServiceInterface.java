package ajc.jpa.services;

import java.util.List;

import ajc.jpa.models.CustomUser;

public interface UserServiceInterface {
	
	CustomUser getByUsername(String username);
    CustomUser addUser(CustomUser user);
    List<CustomUser> getAllUsers();
    CustomUser getById(Long id);

}
