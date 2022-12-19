package hr.klobucaric.webshop.security;

import hr.klobucaric.webshop.role.Role;
import org.springframework.security.core.GrantedAuthority;

public record SecurityAuthority(Role role) implements GrantedAuthority {
	@Override
	public String getAuthority() {
		return role.getName();
	}
}
