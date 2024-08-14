package co.simplon.glucidenfoliebusiness.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)//dispo à l'éxécution
@Target(ElementType.TYPE)//cible le type
@Documented
@Constraint(validatedBy = RecipeCreateUniqueValidator.class)
public @interface RecipeCreateUnique {
	
	String message() default "Il existe déjà une recette du même nom"; // message error
	
	Class <?>[] groups() default {}; 
	
	Class<? extends Payload>[] payload() default {};

}
