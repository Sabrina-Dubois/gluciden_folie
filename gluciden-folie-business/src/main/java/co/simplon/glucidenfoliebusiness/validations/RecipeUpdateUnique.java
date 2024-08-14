package co.simplon.glucidenfoliebusiness.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = RecipeUpdateUniqueValidator.class)
public @interface RecipeUpdateUnique {
	String message() default ""; // message error
	
	Class <?>[] groups() default {}; 
	
	Class<? extends Payload>[] payload() default {};

}
