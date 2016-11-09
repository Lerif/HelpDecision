package model.domain.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarioUtil {

	public static java.sql.Timestamp dateParaSqlTimestamp(Date data){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.MILLISECOND, 0);
		
		return new java.sql.Timestamp(cal.getTimeInMillis());
	}
}
