package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_recipes")
public class Recipe extends AbstractEntity {

	@Column(name = "recipe_name")
	private String name;

	@Column(name = "recipe_picture")
	private String picture;

	@ManyToMany
	@JoinTable(name = "t_recipes_categories", joinColumns = @JoinColumn(name = "id_recipe"), inverseJoinColumns = @JoinColumn(name = "id_category"))
	private Set<Category> categories = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "t_recipes_ingredients", joinColumns = @JoinColumn(name = "id_recipe"), inverseJoinColumns = @JoinColumn(name = "id_ingredient"))
	private Set<Ingredient> ingredients = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false)
	private Account account;

	@Override
	public String toString() {
		return String.format("Recipe{name='%s', picture='%s', categories=%s, username='%s'}", name, picture, categories,
				account);
	}

	// Getters & setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
