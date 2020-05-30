package se.kransellwennborg.tink.converters;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import se.kransellwennborg.tink.Logger;

import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class TinkTimeConverter implements TypeConverter<Date> {

	@Override
	public Date convert(String input, Class<? extends Date> arg1, Collection<ValidationError> errors) {

		Date time = null;
		if (isValid(input)) {

			SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmm");
			time = timeFormatter.parse(input, new ParsePosition(0));

			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			Logger.debug(getClass(), "converted minutes: " + cal.get(Calendar.MINUTE));
		} else {
			Logger.error(getClass(), "ERROR in convert()");
			errors.add(new LocalizableError(
					"se.kransellwennborg.tink.actions.EntryEditActionBean.timeEntry.time.validationError"));

		}
		return time;
	}

	@Override
	public void setLocale(Locale arg0) {

	}

	private boolean isValid(String time) {
		boolean result = true;

		if (time.length() != 4) {
			result = false;
		} else {
			try {
				// length is correct - now we check format
				int hour = Integer.parseInt(time.substring(0, 2));
				int minutes = Integer.parseInt(time.substring(2, 4));
				if (hour < 0 || hour > 23) {
					result = false;
				}
				if (minutes < 0 || minutes > 59) {
					result = false;
				}
			} catch (Exception e) {
				result = false;
			}

		}
		return result;
	}
}
