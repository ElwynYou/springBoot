package com.elwyn.bigData.modules.bigdata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @Name com.rainsoft.modules.bigdata.util.StringUtils
* @Description
* 
* @Author Xian
* @Version 2016年12月30日 上午11:00:59
* @Copyright 上海云辰信息科技有限公司
*
*
 */
public class StringUtils {

	public static boolean isNotEmpty(Object str){
		   if(str==null)
			   return false;
		   if(str.toString().length()==0)
			   return false;
		   return true;
	   }

	/**
	 * mac加-
	 * @param str
	 * @return
	 */
	public static String getMacAdr(String str) {
		if(str.length()>15){
			return str;
		}
		StringBuilder result = new StringBuilder("");
		for (int i = 1; i <= 12; i++) {
			result.append(str.charAt(i - 1));
			if (i % 2 == 0) {
				result.append("-");
			}
		}
		return result.substring(0, 17);
	}

    /**
     * 字符串时间比较大小
     * @param stringDate1
     * @param stringDate2
     * @return
     */
	public static int compareStringDate(String stringDate1,String stringDate2){
	    if (stringDate1.length()>19){
	        stringDate1=stringDate1.substring(0,19);
        }
        if (stringDate2.length()>19){
            stringDate2=stringDate2.substring(0,19);
        }
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=dfs.parse(stringDate1);
            Date date2=dfs.parse(stringDate2);
            if (date1.getTime()>date2.getTime()){
                return 1;
            }else if (date1.getTime()>date2.getTime()){
                return -1;
            }else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
