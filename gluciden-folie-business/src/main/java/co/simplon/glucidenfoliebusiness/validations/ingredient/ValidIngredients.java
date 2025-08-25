package co.simplon.glucidenfoliebusiness.validations.ingredient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.auth0.jwt.interfaces.Payload;

import jakarta.validation.Constraint;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RecipeIngredientUnityValidator.class)
public @interface ValidIngredients {
	String message() default "Soit ingredientId soit ingredient.name doit être renseigné";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}