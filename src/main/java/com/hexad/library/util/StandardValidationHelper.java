package com.hexad.library.util;

import java.util.HashMap;
import java.util.Map;

public class StandardValidationHelper {

	private HashMap<String, String> validationErrors = new HashMap<>();

	public boolean isAlpha(String fieldName, String stringToValidate) {
		if (!stringToValidate.matches("[a-zA-Z]+")) {
			validationErrors.put(fieldName, "Contains non alphabetic characters [" + stringToValidate + "]");
			return false;
		}
		return true;
	}
	

	public boolean isNumeric(String fieldName, String stringToValidate) {
		if (!stringToValidate.matches("[0-9]+")) {
			validationErrors.put(fieldName, "Contains non numeric characters [" + stringToValidate + "]");
			return false;
		}
		return true;
	}

	public boolean objectNotNull(String fieldName, Object objectToEvaluate, String customText) {

		if (objectToEvaluate == null) {
			if (customText == null) {
				validationErrors.put(fieldName, "This field cannot be null.");
			} else {
				validationErrors.put(fieldName, customText);
			}
			return false;
		} else {
			return true;
		}
	}

	public void addValidationError(String key, String value) {
		validationErrors.put(key, value);
	}

	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}

	public boolean hasValidationErrors() {
		return this.validationErrors.size() > 0;
	}

}
