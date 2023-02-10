package ajc.jpa.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ajc.jpa.models.CustomRole;
import ajc.jpa.repositories.RoleRepository;

public class RoleService implements RoleServiceInterface{
	
	private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public CustomRole getByRoleName(String roleName) throws UsernameNotFoundException {
        return roleRepository.findbyRoleName(roleName).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Role %s was not found!", roleName))
        );
    }

}
