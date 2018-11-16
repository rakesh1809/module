package org.cms.hios.common.validation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {

	protected IErrorMessageType _iErrorMessageType;

	public ValidationService(IErrorMessageType iErrorMessageType) {
		this._iErrorMessageType = iErrorMessageType;
	}
	public ValidationService() {
		
	}

	/* Private Helper functions */
	public boolean hasValue(String input) {
		if (input != null && !input.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasLengthExact(String input, int length) {
		if (hasValue(input) && input.length() == length) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasLengthWithinMax(String input, int maxLength) {
		if (hasValue(input) && input.length() <= maxLength) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasLengthAboveMin(String input, int minLength) {
		if (hasValue(input) && input.length() >= minLength) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isInteger(String input) {
		if (hasValue(input)) {
			try {
				Integer.parseInt(input);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isIntegerPositive(String input) {
		if (isInteger(input) && (Integer.parseInt(input) > 0)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isIntegerPositiveOrWholeNumber(String input) {
		if (isInteger(input) && (Integer.parseInt(input) >= 0)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasOnlyValidCharacters(String input) {
		if (hasValue(input)) {
		    	String defaultInvalidChar =  ".*[<>;=\"].*";
		    	String invalidChar = System.getProperty("HIOS_INVALID_CHARS",defaultInvalidChar);
			Pattern patternInvalidCharRegex = Pattern.compile(invalidChar);
			Matcher matcher = patternInvalidCharRegex.matcher(input);

			if (matcher.find()) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public boolean hasOnlyValidCharsForRegex(String input, String regEx,
			String regExType) {
		boolean result = false;
		if (hasValue(input)) {
			Pattern patternValidCharRegex = Pattern.compile(regEx);
			Matcher matcher = patternValidCharRegex.matcher(input);

			if (regExType.equals("whitelist")) {
				if (matcher.find()) {
					result = true;
				}
			} else if (regExType.equals("blacklist")) {
				if (!matcher.find()) {
					result = true;
				}
			}
		}

		return result;
	}

	public boolean hasOnlyValidCharForOrgName(String input) {
		if (hasValue(input)) {
			Pattern patternValidCharRegex = Pattern
					.compile("^([A-z]|[0-9]|[&()-/\\.])+([A-z]|[0-9]|[&()-/\\.]|\\s)*$");
			Matcher matcher = patternValidCharRegex.matcher(input);

			if (matcher.find()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isValidDate(String input) {
		if (hasValue(input)) {
			String formatString = "MM/dd/yyyy";

			try {
				SimpleDateFormat format = new SimpleDateFormat(formatString);
				format.setLenient(false);
				format.parse(input);
				return true;
			} catch (java.text.ParseException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/* Validation functions */

	/**
	 * Validates a non-text selection field (Dropdown, radio button, checkbox).
	 * If there is an error, returns the appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param selectedValue
	 *            value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateSelection(boolean isRequired, String selectedValue) {
		if (isRequired
				&& (!hasValue(selectedValue) || selectedValue.equals("-1"))) {
			return _iErrorMessageType.getErrorREQUIRED_NONTEXT_FIELD_MISSING();

		}

		if (hasValue(selectedValue)) {
			if (!hasOnlyValidCharacters(selectedValue)) {
				return _iErrorMessageType.getErrorINVALID_CHARACTERS();
			}
			if (!hasLengthWithinMax(selectedValue, 250)) {
				return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
			}
		}

		return null;
	}

	/**
	 * Validates a plan name input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @param maxLength
	 *            maximum number of characters allowed for the field, or
	 *            <code>null</code> if there is no limit
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validatePlanName(boolean isRequired, String input,
			Integer maxLength) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			Pattern patternPlanName = Pattern
					.compile("^([a-zA-Z]|[0-9]|[&'()-])+([a-zA-Z]|[0-9]|[&'()-]|\\s)*$");
			Matcher matcher = patternPlanName.matcher(input);
			if (!matcher.find()) {
				return _iErrorMessageType.getErrorINVALID_PLAN_NAME();
			}

			if (maxLength != null && !hasLengthWithinMax(input, maxLength)) {
				return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
			}
		}

		return null;
	}

	/**
	 * Validates a text input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @param maxLength
	 *            maximum number of characters allowed for the field, or
	 *            <code>null</code> if there is no limit
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateText(boolean isRequired, String input,
			Integer maxLength) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!hasOnlyValidCharacters(input)) {
				return _iErrorMessageType.getErrorINVALID_CHARACTERS();
			}

			if (maxLength != null && !hasLengthWithinMax(input, maxLength)) {
				return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
			}
		}

		return null;
	}

	/**
	 * Validates a text input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @param maxLength
	 *            maximum number of characters allowed for the field, or
	 *            <code>null</code> if there is no limit
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateText(boolean isRequired, String input,
			Integer maxLength, String regEx, String regExType,
			Map<String, String> customErrorMsgs) {
		if (isRequired && !hasValue(input)) {
			if (customErrorMsgs != null
					&& customErrorMsgs
							.containsKey("REQUIRED_TEXT_FIELD_MISSING")) {
				return customErrorMsgs.get("REQUIRED_TEXT_FIELD_MISSING");
			}
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!hasOnlyValidCharsForRegex(input, regEx, regExType)) {
				if (customErrorMsgs != null
						&& customErrorMsgs.containsKey("INVALID_CHARACTERS")) {
					return customErrorMsgs.get("INVALID_CHARACTERS");
				}
				return _iErrorMessageType.getErrorINVALID_CHARACTERS();
			}

			if (maxLength != null && !hasLengthWithinMax(input, maxLength)) {
				if (customErrorMsgs != null
						&& customErrorMsgs
								.containsKey("MAX_TEXT_LENGTH_EXCEEDED")) {
					return customErrorMsgs.get("MAX_TEXT_LENGTH_EXCEEDED");
				}
				return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
			}
		}

		return null;
	}

	/**
	 * Validates a text input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @param minLength
	 *            minimum number of characters allowed for the field, or
	 *            <code>null</code> if there is no limit
	 * @param maxLength
	 *            maximum number of characters allowed for the field, or
	 *            <code>null</code> if there is no limit
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateText(boolean isRequired, String input,
			Integer minLength, Integer maxLength) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!hasOnlyValidCharForOrgName(input)) {
				return _iErrorMessageType.getError_ORGANAME_CHARACTERS();
			}

			if (minLength != null && !hasLengthAboveMin(input, minLength)) {
				return _iErrorMessageType.getErrorMIN_TEXT_LENGTH_NOT_MET();
			}

			if (maxLength != null && !hasLengthWithinMax(input, maxLength)) {
				return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
			}
		}

		return null;
	}

	/**
	 * Validates a text input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateText(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!hasOnlyValidCharacters(input)) {
				return _iErrorMessageType.getErrorINVALID_CHARACTERS();
			}
		}

		return null;
	}

	/**
	 * Validates a date input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatDate(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!validateDatePattern(input) || !isValidDate(input)) {
				return _iErrorMessageType.getErrorINVALID_DATE_FORMAT();
			}
		}

		return null;
	}

	private boolean validateDatePattern(String input) {
		Pattern patternPlanName = Pattern
				.compile("^\\d\\d/\\d\\d/\\d\\d\\d\\d$");
		Matcher matcher = patternPlanName.matcher(input);
		if (matcher.find())
			return true;
		return false;
	}

	/**
	 * Validates a ZIP code input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatZipCode(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int exactLength = 5;
			if (!(hasLengthExact(input, exactLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
			}
		}

		return null;
	}

	/**
	 * Validates a ZIP Plus 4 input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatZipPlus4(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int exactLength = 4;
			if (!(hasLengthExact(input, exactLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
			}
		}

		return null;
	}

	/**
	 * Validates a phone number input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatPhoneNumber(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			Pattern patternPhoneRegex = Pattern
					.compile("^\\(?([0-9]{3})\\)?[-]?([0-9]{3})[-]([0-9]{4})$");
			Matcher matcher = patternPhoneRegex.matcher(input);
			if (!matcher.find()) {
				return _iErrorMessageType.getErrorINVALID_PHONE_FORMAT();
			}
		}

		return null;
	}

	
	
	public String validatePhoneNumber(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}
		if (hasValue(input)) {
			if (!(isIntegerPositiveOrWholeNumber(input))) {
				return _iErrorMessageType.getErrorINVALID_PHONE_FORMAT();
			}else{
				if(input.trim().length()!=10){
					return _iErrorMessageType.getErrorINVALID_PHONE_FORMAT();
				}
			}
		}	

		return null;
	}

    public String validatePhoneNumberOnly(boolean isRequired, String input) {
        if (isRequired && !hasValue(input)) {
                       return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
        }

        if (hasValue(input)) {
                       Pattern patternPhoneRegex = Pattern
                                                     .compile("^[0-9]{10}$");
                       Matcher matcher = patternPhoneRegex.matcher(input);
                       if (!matcher.find()) {
                                      return _iErrorMessageType.getErrorINVALID_PHONE_FORMAT();
                       }
        }

        return null;
}
    public String validatePhoneNum(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}
		if (hasValue(input)) {
			Pattern patternPhoneRegex = Pattern.compile("^[0-9]{10,15}$");
			Matcher matcher = patternPhoneRegex.matcher(input);
			if (!matcher.find()) {
				return _iErrorMessageType.getErrorINVALID_PHONE_FORMAT();
			}
		}	

		return null;
	}
	
	/**
	 * Validates a phone extension field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatPhoneExt(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int maxLength = 6;
			if (!(hasLengthWithinMax(input, maxLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_MAX_LENGTH();
			}
		}

		return null;
	}

	/**
	 * Validates a non us phone number input field. If there is an error,
	 * returns the appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatNonUSPhoneNumber(boolean isRequired,
			String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			Pattern patternPhoneRegex = Pattern
					.compile("^([0-9]|[+()-]|\\s)*$");
			Matcher matcher = patternPhoneRegex.matcher(input);
			if (!matcher.find()) {
				return _iErrorMessageType.getErrorINVALID_NONUS_PHONE_FORMAT();
			}
		}

		return null;
	}

	/**
	 * Validates an email address input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatEmail(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!input.contains("@")) {
				return _iErrorMessageType
						.getErrorINVALID_EMAIL_ADDRESS_FORMAT();
			} else {
				String localPart = input.substring(0, input.lastIndexOf("@"));
				String domainWithAt = input.substring(input.lastIndexOf("@"));

				// Remove apostrophe from local part for validation (for names
				// like john.o'keefe...)
				input = localPart.replace("'", "") + domainWithAt;

				Pattern patternEmailRegex = Pattern
						.compile("^(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\\-+)|([A-Za-z0-9]+\\.+)|([A-Za-z0-9]+\\++))*[A-Za-z0-9]+@((\\w+\\-+)|(\\w+\\.))*\\w{1,63}\\.[a-zA-Z]{2,6}$");
				Matcher matcher = patternEmailRegex.matcher(input);
				if (!matcher.find()) {
					return _iErrorMessageType
							.getErrorINVALID_EMAIL_ADDRESS_FORMAT();
				}

				if (!hasOnlyValidCharacters(input)) {
					return _iErrorMessageType.getErrorINVALID_CHARACTERS();
				}
			}
		}

		return null;
	}

	/**
	 * Validates a Web URL input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatURL(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			input = input.toLowerCase().trim();

			if (!(input.startsWith("http://") || input.startsWith("https://")
					|| input.startsWith("ftp://") || input
						.startsWith("ftps://"))) {
				// Add http default prefix for validation if input does not
				// start with protocol
				input = "http://" + input;
			}

			Pattern patternURLRegex = Pattern
					.compile("^(((ht|f)tp(s?))\\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\\-\\.]+\\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\\\+&amp;%\\$#\\=~_\\-]+))*$");
			Matcher matcher = patternURLRegex.matcher(input);
			if (!matcher.find()) {
				return _iErrorMessageType.getErrorINVALID_URL_FORMAT();
			}

			if (!hasOnlyValidCharsForRegex(input, ".*[<>;\"].*","blacklist")) {
				return _iErrorMessageType.getErrorINVALID_CHARACTERS();
			}
		}

		return null;
	}

	/**
	 * Validates a SERFF number input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatSERFF(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			Pattern patternSERFFRegex = Pattern
					.compile("^[A-Za-z]{4}[-][0-9a-zA-Z]{9}(([/][0-9]{2})|([/][0-9]{2}[-][0-9]{2}))*");
			Matcher matcher = patternSERFFRegex.matcher(input);
			if (!matcher.find()) {
				return _iErrorMessageType.getErrorINVALID_SERFF_FORMAT();
			}

			if (!hasOnlyValidCharacters(input)) {
				return _iErrorMessageType.getErrorINVALID_CHARACTERS();
			}
		}

		return null;
	}

	/**
	 * Validates a Federal EIN input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatFEIN(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int exactLength = 9;
			if (!(hasLengthExact(input, exactLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
			}
		}

		return null;
	}
	
	/**
	 * Validates an issuer id input field. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatIssuerId(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int exactLength = 5;
			if (!(hasLengthExact(input, exactLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
			}
			//Ensure first digit is not 0
			if (Integer.parseInt(input)<10000) {
				return _iErrorMessageType.getErrorINVALID_ISSUER_ID();
			}
		}

		return null;
	}


	/**
	 * Validates a NAIC Company Code input field. If there is an error, returns
	 * the appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatNAICCode(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int exactLength = 5;
			if (!(hasLengthExact(input, exactLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
			}
		}

		return null;
	}

	/**
	 * Validates a NAIC Group Code input field. If there is an error, returns
	 * the appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatNAICGroupCode(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int maxLength = 5;
			if (!(hasLengthWithinMax(input, maxLength) && isIntegerPositive(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_MAX_LENGTH();
			}
		}

		return null;
	}

	/**
	 * Validates an AM Best Number input field. If there is an error, returns
	 * the appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateFormatAMBestNumber(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			int exactLength = 6;
			if (!(hasLengthExact(input, exactLength) && isIntegerPositiveOrWholeNumber(input))) {
				return _iErrorMessageType
						.getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
			}
		}

		return null;
	}

	// Validate at least one value in the list is true
	public String validateBooleanList(List<Boolean> booleanList) {
		boolean valid = false;
		for (Boolean value : booleanList) {
			if (value) {
				valid = true;
			}
		}
		if (!valid) {
			return _iErrorMessageType.getErrorREQUIRED_NONTEXT_FIELD_MISSING();
		}
		return null;
	}

	public String validatePositiveInteger(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!(isIntegerPositive(input))) {
				return _iErrorMessageType.getErrorINVALID_NUMERIC_FIELD();
			}
		}

		return null;
	}

	public String validatePositiveIntegerFormatWithLength(boolean isRequired,
			String input, Integer maxLength) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!(isIntegerPositive(input))) {
				return _iErrorMessageType.getErrorINVALID_NUMERIC_FIELD();
			}
		}

		if (maxLength != null && !hasLengthWithinMax(input, maxLength)) {
			return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
		}

		return null;
	}

	public String validateIntegerWithMinusOne(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {

			if (isInteger(input) && (Integer.parseInt(input) >= -1)) {
				return null;
			} else {
				return _iErrorMessageType.getErrorINVALID_NUMERIC_FIELD();
			}

		}

		return null;
	}

	public String validateWholeNumberFormatWithLength(boolean isRequired,
			String input, Integer maxLength) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!(isIntegerPositiveOrWholeNumber(input))) {
				return _iErrorMessageType.getErrorINVALID_NUMERIC_FIELD();
			}
		}

		if (maxLength == null && !hasLengthWithinMax(input, maxLength)) {
			return _iErrorMessageType.getErrorMAX_TEXT_LENGTH_EXCEEDED();
		}

		return null;
	}

	public String validateWholeNumber(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!(isIntegerPositiveOrWholeNumber(input))) {
				return _iErrorMessageType.getErrorINVALID_NUMERIC_FIELD();
			}
		}

		return null;
	}

	/*
	 * public String validateFormatFile(boolean isRequired, File inputFile,
	 * String... acceptedExtensions) { if (isRequired && (inputFile == null ||
	 * inputFile.length() == 0)) { return
	 * _iErrorMessageType.getErrorREQUIRED_FILE_UPLOAD_MISSING(); }
	 * 
	 * if (!(inputFile == null || inputFile.length() == 0)) { //Maximum file
	 * size that can be uploaded is 30MB (30MB * 1048576 Bytes/MB = 31457280
	 * Bytes) if (inputFile.length() > 31457280) { return
	 * _iErrorMessageType.getErrorMAX_FILE_SIZE_EXCEEDED(); }
	 * 
	 * //Validate file extension if (acceptedExtensions != null) { String
	 * actualExtension =
	 * inputFile.getName().substring(inputFile.getName().lastIndexOf("."));
	 * boolean isValidExtension = false; String strExtensions = "";
	 * 
	 * for (int i = 0; i < acceptedExtensions.length; i++ ) { if (i ==
	 * (acceptedExtensions.length - 1)) { if (i == 0) {
	 * strExtensions.concat(acceptedExtensions[i]); } else {
	 * strExtensions.concat(acceptedExtensions[i] + ", or "); } } else {
	 * strExtensions.concat(acceptedExtensions[i] + ", "); }
	 * 
	 * if (actualExtension.equalsIgnoreCase(acceptedExtensions[i])) {
	 * isValidExtension = true; } }
	 * 
	 * if (!isValidExtension) { return
	 * _iErrorMessageType.getErrorINVALID_FILE_EXTENSION(); } } }
	 * 
	 * return null; }
	 */

	public String validateMoneyFieldWithMaxLimit(boolean isRequired, String input, int maxLimit) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}
		if (hasValue(input)) {
			if(!(isMoneyField(input))){
				return _iErrorMessageType.getErrorINVALID_NUMERIC_FIELD();
			}
			if(input.contains(".")){
				if(!(input.substring(0, input.indexOf(".")).length() <= maxLimit)){
					return _iErrorMessageType.getErrorNUMBER_FIELD_INVALID_MAX_LENGTH();
				}
			}else if(!(input.length() <= maxLimit)){
				return _iErrorMessageType.getErrorNUMBER_FIELD_INVALID_MAX_LENGTH();
			}
		}
		return null;
	}

	public boolean isMoneyField(String input) {
		try {
			BigDecimal value = new BigDecimal(input);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validates a date input field in Zulu Format. If there is an error, returns the
	 * appropriate error message.
	 * 
	 * @param isRequired
	 *            true if validating a required field, otherwise false
	 * @param input
	 *            text value to validate
	 * @return string set with error message, or <code>null</code> if no error
	 */
	public String validateZuluFormatDate(boolean isRequired, String input) {
		if (isRequired && !hasValue(input)) {
			return _iErrorMessageType.getErrorREQUIRED_TEXT_FIELD_MISSING();
		}

		if (hasValue(input)) {
			if (!validateZuleDatePattern(input)) {
				return _iErrorMessageType.getErrorINVALID_DATE_FORMAT();
			}
		}

		return null;
	}

	private boolean validateZuleDatePattern(String input) {
		Pattern patternPlanName = Pattern
				.compile("^\\d\\d\\d\\d-[0-1][0-9]-[0-3][0-9]T\\d\\d:\\d\\d:\\d\\d.\\d\\d\\dZ");
		Matcher matcher = patternPlanName.matcher(input);
		if (matcher.find())
			return true;
		return false;
	}
}
