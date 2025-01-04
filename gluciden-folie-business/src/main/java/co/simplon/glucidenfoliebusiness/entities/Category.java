package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_categories")
public class Category extends AbstractEntity {

	@Column(name = "category_name")
	private String name;

	@ManyToMany(mappedBy = "categories") // Relation inverse vers Recipe
	private Set<Recipe> recipes = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
