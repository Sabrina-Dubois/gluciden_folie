//objets BDD sous forme de classes Java => structure data
package co.simplon.glucidenfoliebusiness.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
	
@Entity //mapper à une table BDD
@Table(name = "t_recipes")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recipe")
	private Long id;
	
	@Column(name = "recipe_name")
	private String name;
	
	@Column(name = "recipe_picture")
	private String picture;

	

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

	

	
}
