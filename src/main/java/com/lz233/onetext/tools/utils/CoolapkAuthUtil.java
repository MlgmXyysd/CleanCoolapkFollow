package com.lz233.onetext.tools.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CoolapkAuthUtil {
    public static String getAS() {
        String uuid = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        int time = Integer.parseInt(dateToStamp(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND)).substring(0, 10));
        return DigestUtils.md5Hex(Base64.encodeBase64(("token://com.coolapk.market/c67ef5943784d09750dcfbb31020f0ab?" + DigestUtils.md5Hex(String.valueOf(time)) + "$" + uuid + "&com.coolapk.market").getBytes(StandardCharsets.UTF_8))) + uuid + "0x" + Integer.toHexString(time);
    }

    public static String dateToStamp(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date != null ? date.getTime() : null);
    }

}
