package co.simplon.glucidenfoliebusiness.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_ingredients")
public class Ingredient extends AbstractEntity {

	@Column(name = "ingredient_name", nullable = false)
	private String ingredientName;

	@ManyToOne
	@JoinColumn(name = "id_unity", nullable = false)
	private Unity unity;

	@OneToMany(mappedBy = "ingredientId") // L'ID composite est référencé ici.
	private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;

	}

	public Unity getUnity() {
		return unity;
	}

	public void setUnity(Unity unity) {
		this.unity = unity;
	}
}
