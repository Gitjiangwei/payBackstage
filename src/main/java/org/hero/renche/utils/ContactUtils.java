package org.hero.renche.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactUtils {

    public static  final String telRegex = "^\\(0[0-9]{2,3}\\-\\)?\\([2-9][0-9]{6,7}\\)+\\(\\-[0-9]{1,4}\\)?$|^[1][3-9]\\d{9}$";

    /** 客户类型**/
    public static  final String  CUSTOMERTYPE = "CUSTOMETYPE";
    public static  final String  CUSTOMERTYPE_PERSONAL = "PERSONAL";//个人
    public static  final String  CUSTOMERTYPE_MIDDLEMAN = "MIDDLEMAN";//中间商
    public static  final String  CUSTOMERTYPE_MANUFACTURER = "MANUFACTURER";//制造商
    public static  final String  CUSTOMERTYPE_GOVERNMENT = "GOVERNMENT";//政府

    /** 设备状态**/
    public static  final String  ELECTSTATUS = "ELECTSTATUS";
    public static  final String  ELECTSTATUS_FREE_ = "FREE";//空闲
    public static  final String  ELECTSTATUS_INREPAIR = "INREPAIR";//维修中
    public static  final String  ELECTSTATUS_INUSE = "INUSE";//使用中
    public static  final String  ELECTSTATUS_SCRAP = "SCRAP";//报废

    /** 工程类型**/
    public static  final String  PROJECTITEMTYPE = "PROJECTITEMTYPE";
    public static  final String  PROJECTITEMTYPE_PERSONORCAR = "PERSONORCAR";//人车
    public static  final String  PROJECTITEMTYPE_FOURG = "FOURG";//4G

    /** 提醒周期类型 **/
    public static  final String  REMINDPERIODTYPE = "REMINDPERIODTYPE";
    public static  final String  REMINDPERIODTYPE_WEEK = "WEEK";//按周
    public static  final String  REMINDPERIODTYPE_MONTH = "MONTH";//按月

    //电话验证
    public static boolean checkTel(String tel)
    {
        Pattern pattern = Pattern.compile(telRegex);
        Matcher matcher = pattern.matcher(tel);
        if (matcher.matches())
        {
            return true;
        }
        return false;
    }

    //时间验证
    public static boolean checkData(String data){
        String year = data.substring(0, 4); // 获取年份
        String month = data.substring(5, 6); // 获取月份
        Boolean isLeap = leapYear(Integer.parseInt(year)); // 判断闰年
        System.out.println(isLeap);
        StringBuffer eL= new StringBuffer();
        String longMonth = "01030507081012"; // 31天的月份
//        String fix = "([2][0-3]|[0-1][0-9]|[1-9])[0-5][0-9]([0-5][0-9]|[6][0])";
        if(isLeap && month.equals("02")){  // 针对2月份的情况 【闰年】
            eL.append("\\d{4}\\-([1][0-2]|[0][0-9])\\-([1-2][0-9]|[0][1-9]|[1-9])");
        }else if(!isLeap && month.equals("02")){ // 针对2月份的情况 【非闰年】
            eL.append("\\d{4}\\-([1][0-2]|[0][0-9])\\-([1-2][0-8]|[0][1-9]|[1-9])");
        }else if(longMonth.contains(month)){ // 31天月份
            eL.append("\\d{4}\\-([1][0-2]|[0][0-9])\\-([3][0-1]|[1-2][0-9]|[0][1-9]|[1-9])");
        }else{ // 30天月份
            eL.append("\\d{4}\\-([1][0-2]|[0][0-9])\\-([3][0]|[1-2][0-9]|[0][1-9]|[1-9])");
        }
        Pattern p = Pattern.compile(eL.toString());
        Matcher m = p.matcher(data);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    public static boolean leapYear(int year) {
        Boolean isLeap = false;
        if (((year % 100 == 0) && (year % 400 == 0))
                || ((year % 100 != 0) && (year % 4 == 0)))
            isLeap = true;
        return isLeap;
    }
}
