package co.simplon.glucidenfoliebusiness.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_ingredients")
public class Ingredient extends AbstractEntity {

	@Column(name = "ingredient_name", nullable = false)
	private String ingredientName;

	@Column(name = "quantity", nullable = false)
	private BigDecimal quantity;

	// @ManyToOne
	// @JoinColumn(name = "id_unity", nullable = false)
	// private Unity unity;

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/*
	 * public Unity getUnity() { return unity; }
	 * 
	 * public void setUnity(Unity unity) { this.unity = unity; }
	 */

}
