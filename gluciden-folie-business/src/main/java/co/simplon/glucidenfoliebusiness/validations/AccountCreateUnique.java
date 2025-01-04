package co.simplon.glucidenfoliebusiness.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME) // moment exécution
@Target(ElementType.TYPE) // pour Dto
@Documented
@Constraint(validatedBy = AccountCreateUniqueValidator.class)
public @interface AccountCreateUnique {

	String message() default "Le nom d'utilisateur existe déjà";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
