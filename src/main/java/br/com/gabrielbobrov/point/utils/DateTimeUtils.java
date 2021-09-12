package br.com.gabrielbobrov.point.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DateTimeUtils {
	
	public boolean isValidDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
	}
	
	public LocalDateTime convertStringIntoLocalDateTime(String date) {
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date convertedDate = new Date();
		LocalDateTime dateTime = null;
		
		try {
			//convertendo string em data
			convertedDate = fromUser.parse(date);
			String reformattedStr = fromUser.format(convertedDate);
			return  LocalDateTime.parse(reformattedStr);

		} catch (Exception e) {
			 return dateTime;
		}
	}

	public boolean isWeekDay(LocalDateTime dt) {

		switch (dt.getDayOfWeek()) {

		case SATURDAY:
			return false;

		case SUNDAY:
			return false;

		default:
			return true;
		}

	}

}
