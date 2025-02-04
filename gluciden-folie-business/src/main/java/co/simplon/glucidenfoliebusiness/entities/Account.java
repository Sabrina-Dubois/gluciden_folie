package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_accounts")
public class Account extends AbstractEntity {

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	// @Column(nullable = false)
	// private String role = "User";

	@OneToMany(mappedBy = "account") // Une relation One-to-Many avec Category
	private Set<Category> categories = new HashSet<>();

	@OneToMany(mappedBy = "account") // Une relation One-to-Many avec Recipe
	private Set<Recipe> recipes = new HashSet<>();

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
