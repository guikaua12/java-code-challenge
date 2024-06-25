package me.approximations.javacodechallenge.security;

import me.approximations.javacodechallenge.enums.Cargo;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserAdminRoleChecker {
    private final RoleHierarchy roleHierarchy;

    public UserAdminRoleChecker(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    /**
     * Verifica se um usuário do `authentication` tiver cargo de ADMIN, o `id` pode ser qualquer valor, caso contrário o id deverá ser igual ao id do `authentication`.
     *
     * @param id             O id do usúario que terá o cargo verificado.
     * @param authentication O objeto JwtAuthenticationToken do usuário autenticado.
     * @throws AccessDeniedException Se o usuário não for ADMIN e o `id` for diferente do `authentication#id`.
     */
    public void checkUserPermission(Long id, JwtAuthenticationToken authentication) {
        final CustomUserDetails userDetails = authentication.getUserDetails();
        final GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_" + Cargo.ADMIN.name());

        final Collection<? extends GrantedAuthority> reachableRoles = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());

        if (!reachableRoles.contains(adminAuthority) && !userDetails.getId().equals(id)) {
            throw new AccessDeniedException("Insufficient permission");
        }
    }
}