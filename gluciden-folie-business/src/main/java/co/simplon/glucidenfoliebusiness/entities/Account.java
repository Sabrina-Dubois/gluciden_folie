package co.simplon.glucidenfoliebusiness.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_accounts")
public class Account extends AbstractEntity {

	@Column(name = "username")
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	// @Column(nullable = false)
	// private String role = "User";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("username=%s", "password=[PORTECTED]", username);
	}

	// public String getRole() {
	// return role;
	// }

	// public void setRole(String role) {
	// this.role = role;
	// }

}
