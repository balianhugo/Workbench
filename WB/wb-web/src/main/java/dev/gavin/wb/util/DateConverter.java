package dev.gavin.wb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

    private static final List<String> formarts = new ArrayList<String>(4);
    static{
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
        formarts.add("yyyy-MM-dd HH:mmZ");
        formarts.add("yyyy-MM-dd HH:mmXXX");
        formarts.add("yyyy-MM-dd HH:mm:ssZ");
        formarts.add("yyyy-MM-dd HH:mm:ssXXX");
        formarts.add("yyyy");
    }
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        //把T换成空格
        if(value.indexOf('T')>0){
            value = value.replace('T', ' ');
        }
        value = value.replaceAll(" +", " ");

        int offset = value.lastIndexOf('.');

        if(offset>0){
            value = value.substring(0, offset);
        }

        if(value.matches("^\\d{4}-\\d{1,2}$")){
            return parseDate(value, formarts.get(0));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return parseDate(value, formarts.get(1));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return parseDate(value, formarts.get(2));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return parseDate(value, formarts.get(3));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}(\\+|-)\\d+$")){
            return parseDate(value, formarts.get(4));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}(\\+|-)\\d{1,2}:\\d{1,2}$")){
            return parseDate(value, formarts.get(5));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}(\\+|-)\\d+$")){
            return parseDate(value, formarts.get(6));
        }else if(value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}(\\+|-)\\d{1,2}:\\d{1,2}$")){
            return parseDate(value, formarts.get(7));
        }else if(value.matches("^\\d{4}$")){
            return parseDate(value, formarts.get(8));
        }else {
            throw new IllegalArgumentException("Invalid datetime value '" + source + "'");
        }
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr
     *            String 字符型日期
     * @param format
     *            String 格式
     * @return Date 日期
     */
    public  Date parseDate(String dateStr, String format) {
        Date date=null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = (Date) dateFormat.parse(dateStr);
        } catch (Exception e) {
        }
        return date;
    }
    public static void main(String[] args) throws ParseException {
        System.out.println(new DateConverter().convert("2014-04"));
        System.out.println(new DateConverter().convert("2015-12-31T08:12+08:00"));

    }
}