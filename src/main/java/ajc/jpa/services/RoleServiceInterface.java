package ajc.jpa.services;

import ajc.jpa.models.CustomRole;

public interface RoleServiceInterface {
	CustomRole getByRoleName(String roleName);

}
