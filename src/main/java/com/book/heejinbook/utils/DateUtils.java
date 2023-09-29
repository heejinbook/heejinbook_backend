package com.book.heejinbook.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static Timestamp convertToTimestamp(String inputDate) throws ParseException {
        // 입력받은 형식과 맞는 패턴을 지정합니다.
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");

        // 입력된 날짜 문자열을 Date 객체로 파싱합니다.
        java.util.Date date = inputFormat.parse(inputDate);

        // java.util.Date를 java.sql.Timestamp로 변환합니다.
        return new Timestamp(date.getTime());
    }

    public static String convertToString(Instant inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                .withZone(ZoneId.systemDefault());
        return formatter.format(inputDate);
    }

    public static String convertToStringSecond(Timestamp inputDate) {
        String pattern = "yyyy.MM.dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(inputDate);
    }

}
