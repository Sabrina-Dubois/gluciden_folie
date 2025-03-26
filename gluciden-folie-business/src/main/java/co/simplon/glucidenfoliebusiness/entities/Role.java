package co.simplon.glucidenfoliebusiness.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_roles")
public class Role extends AbstractEntity {

	@Column(name = "role_name", unique = true, nullable = false)
	private String roleName;

	@Column(name = "role_default")
	private boolean roleDefault;

	public Role() {
		this.roleName = null;
		this.roleDefault = false;// Le constructeur par défaut est nécessaire pour Hibernate
	}

	@Override
	public String toString() {
		return String.format("Role{name='%s'}", roleName);
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

	public void setRoleName(String name) {
		this.roleName = name;
	}

	public boolean getRoleDefault() {
		return roleDefault;
	}

	public void setRoleDefault(boolean roleDefault) {
		this.roleDefault = roleDefault;
	}
}
