package co.simplon.glucidenfoliebusiness.validations.category;

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
@Constraint(validatedBy = CategoryCreateUniqueValidator.class)
public @interface CategoryCreateUnique {

	String message() default "Il existe déjà une catégorie du même nom";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
