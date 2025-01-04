//objets BDD sous forme de classes Java => structure data
package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity // mapper à une table BDD
@Table(name = "t_recipes")
public class Recipe extends AbstractEntity {

	@Column(name = "recipe_name")
	private String name;

	@Column(name = "recipe_picture")
	private String picture;

	@ManyToMany
	@JoinTable(name = "t_recipes_categories", joinColumns = @JoinColumn(name = "id_recipe"), inverseJoinColumns = @JoinColumn(name = "id_category"))
	private Set<Category> categories = new HashSet<>();

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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}
