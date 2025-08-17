package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_accounts")
public class Account extends AbstractEntity {

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "id_role", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private Set<Category> categories = new HashSet<>();

	@OneToMany(mappedBy = "account")
	private Set<Recipe> recipes = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Account account = (Account) obj;

		return getId() != null && getId().equals(account.getId());
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}

	@Override
	public String toString() {
		return String.format("username=%s", "password=[PORTECTED]", username);
	}
	// *** Getters & setters ***

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
