package com.cos.lhbreactor.util;

import java.time.LocalDateTime;		
import java.time.format.DateTimeFormatter;

public class DataStart {

	
	public static long urlStart = 277493;
	
	public static String standardDay() {
		LocalDateTime t = LocalDateTime.now().minusDays(1).plusHours(9);
		DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String standardday = t.format(dtFmt) + " 00:00:00";

		return standardday;
	}

	
	public static String endDay() {
		LocalDateTime t = LocalDateTime.now().plusHours(9);
		DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String endday = t.format(dtFmt) + " 00:00:00";
		return endday;
	}
}
