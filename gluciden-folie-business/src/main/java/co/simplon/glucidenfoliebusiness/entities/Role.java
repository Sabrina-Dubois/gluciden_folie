package co.simplon.glucidenfoliebusiness.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_roles")
public class Role extends AbstractEntity {

	@Column(name = "role_name", unique = true, nullable = false)
	private String roleName;

	@Column(name = "role_default", nullable = false)
	private boolean roleDefault;

	// Constructeur vide obligatoire pour JPA
	public Role() {
		// rien à faire ici, JPA se charge de l'initialisation
	}

	// Constructeur pour initialiser un rôle
	public Role(String roleName, boolean roleDefault) {
		super();
		this.roleName = roleName;
		this.roleDefault = roleDefault;
	}

	// Getters & Setters
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isRoleDefault() {
		return roleDefault;
	}

	public void setRoleDefault(boolean roleDefault) {
		this.roleDefault = roleDefault;
	}

	// equals basé sur roleName car il est unique
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Role other))
			return false;
		return Objects.equals(roleName, other.roleName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleName);
	}

	@Override
	public String toString() {
		return String.format("Role{name='%s', default=%s}", roleName, roleDefault);
	}
}
