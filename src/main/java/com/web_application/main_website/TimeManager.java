package com.web_application.main_website;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {
    public String getDateTimeToFormattedDateTime(LocalDateTime preDateTime, String requiredFormat) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(requiredFormat);
		String formattedDateTime = preDateTime.format(format);

		return formattedDateTime;
	}

    public LocalDateTime getNowLocalDatetime() {
        return LocalDateTime.now();
    }
    
}