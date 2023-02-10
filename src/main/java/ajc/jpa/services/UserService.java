package ajc.jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ajc.jpa.models.CustomUser;
import ajc.jpa.repositories.UserRepository;

public class UserService implements UserServiceInterface{
	
	UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUser getByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found!", username))
//                new UsernameNotFoundException(String.format("User " + username + " not found!"))
        );
    }

    @Override
    public CustomUser addUser(CustomUser user) {
        return userRepository.save(user);
    }

	@Override
	public List<CustomUser> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public CustomUser getById(Long id) {
		return userRepository.findById(id).get();
	}

}
