package se.kransellwennborg.tink.converters;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import se.kransellwennborg.tink.Logger;

public class TinkDateConverter implements TypeConverter<Date> {

	@Override
	public Date convert(String input, Class<? extends Date> arg1, Collection<ValidationError> errors) {

		Date date = null;
		if (isValid(input)) {
			Logger.debug(getClass(), "convert()");
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyMMdd");
			date = timeFormatter.parse(input, new ParsePosition(0));
		} else {
			Logger.error(getClass(), "ERROR in convert()");
			errors.add(new LocalizableError(
					"se.kransellwennborg.tink.actions.EntryEditActionBean.timeEntry.date.validationError"));
		}
		return date;
	}

	@Override
	public void setLocale(Locale arg0) {

	}

	private boolean isValid(String date) {

		boolean result = true;
		try {
		if (date.length() != 6) {
			result = false;
		} else {
			// length is correct - now we check format
//			int year = Integer.parseInt(date.substring(0, 2));
			int month = Integer.parseInt(date.substring(2, 4));
			int day = Integer.parseInt(date.substring(4, 6));
			if (month < 1 || month > 12) {
				result = false;
			}
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				if (day < 1 || day > 31) {
					result = false;
				}
			}
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day < 1 || day > 30) {
					result = false;
				}
			}
			if (month == 2) {
				if (day < 1 || day > 29) {
					result = false;
				}
			}

		}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
}
