package co.simplon.glucidenfoliebusiness.validations.step;

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
@Constraint(validatedBy = RecipeStepsValidator.class)
public @interface ValidSteps {
	String message()

	default "Les étapes doivent être numérotées de manière unique et consécutive";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
