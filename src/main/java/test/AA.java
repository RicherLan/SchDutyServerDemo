package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AA {

	public static void main(String[] args) {
		String string = "2018-09-09";
		System.out.println(getWeekday(string));
	}
	
//	实现给定某日期，判断是星期几
	 public static String getWeekday(String date){//必须yyyy-MM-dd
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");  
        SimpleDateFormat sdw = new SimpleDateFormat("E");  
        Date d = null;  
        try {  
            d = sd.parse(date);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }
        return sdw.format(d);
	 }
}
