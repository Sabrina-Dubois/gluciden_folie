package co.simplon.glucidenfoliebusiness.validations.recipe.picture;

import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

	private String[] types;

	@Override
	public void initialize(FileType annotation) {
		types = annotation.types();
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if (file == null || file.isEmpty()) {
			return true;
		}
		String contentType = file.getContentType();
		return Arrays.stream(types).anyMatch((type) -> type.equals(MediaType.ALL_VALUE) || type.equals(contentType));
	}
}
