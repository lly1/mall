/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mall.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 */
public class CommonUtil {
    public static final String UTF8 = "UTF-8";
    public static final String TIME_ZONE_UTC = "UTC";
    public static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 免登录时用的密码
     */
    public final static String DING_PASS = "111111";

    public static String addQuotes(String[] args) {
        StringBuffer sb = new StringBuffer();
        for (String arg : args) {
            sb.append(",'").append(arg).append("'");
        }
        return sb.substring(1);
    }

    public static void dateDiff(String startTime, String endTime, String format) throws ParseException {
//按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数long diff;try {
//获得两个时间的毫秒时间差异
        Long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
    }

    public static String dateDiff(Date startTime, Date endTime) {
//按照传入的格式生成一个simpledateformate对象
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数long diff;try {
//获得两个时间的毫秒时间差异
        Long diff = endTime.getTime() - startTime.getTime();
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒。";
    }

    public static Object getUniqueResult(List<?> objectList) {
        if (objectList == null || objectList.size() <= 0)
            return null;
        else
            return objectList.get(0);
    }

    public static String getSqlStrByList(List<String> sqhList, Class<?> c,
                                         String columnName) {
        StringBuffer sql = new StringBuffer("");
        if (CommonUtil.isNotBlank(sqhList)) {
            sql.append(" ").append(c.getSimpleName().toLowerCase()).append(".")
                    .append(columnName).append(" IN ( ");
            for (int i = 0; i < sqhList.size(); i++) {
                sql.append("'").append(sqhList.get(i) + "',");
                if ((i + 1) % 999 == 0 && (i + 1) < sqhList.size()) {
                    sql.deleteCharAt(sql.length() - 1);
                    sql.append(" ) OR ")
                            .append(c.getSimpleName().toLowerCase())
                            .append(".").append(columnName).append(" IN (");
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" )");
        } else {
            sql.append(" ").append(c.getSimpleName().toLowerCase()).append(".")
                    .append(columnName).append(" IN ( '')");
        }
        return sql.toString();
    }

    public static String getDateString(Date date, String datePattern) {
        if (isBlank(date))
            return "";
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        return df.format(date);
    }

    public static Date converStrToDate(String dateString) throws ParseException {
        String tempStr = dateString;
        if (tempStr.contains("T"))
            tempStr = tempStr.replace("T", " ");
        return converStrToDate(tempStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date converStrToDate(String dateString, String datePattern) throws ParseException {
        if (isBlank(dateString))
            return null;
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        return df.parse(dateString);
    }

    /**
     * 获取任意时间的下一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate yyyyMM格式
     * @param dataFormat 月份格式
     * @return
     */
    public static String getNextMonth(String repeatDate, String dataFormat) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat(dataFormat);
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取任意时间的上一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate yyyyMM格式
     * @param dataFormat 月份格式
     * @return
     */
    public static String getLastMonth(String repeatDate, String dataFormat) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat(dataFormat);
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(4, 6);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month - 2, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }


    public static void printAllParam(HttpServletRequest request) {
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }
    }

    public static String objToString(Object o) {
        if (null == o)
            return "0";
        return o.toString();
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isBlank(Object param) {
        if (null == param)
            return true;
        if (param instanceof String) {
            return ((String) param).trim().equals("");
        }
        if (param instanceof Collection) {
            Collection c = (Collection) param;
            return c.size() <= 0;
        }
        if (param instanceof Map) {
            Map map = (Map) param;
            return map.isEmpty() || map.size() <= 0;
        }
        return false;
    }

    public static boolean isNotBlank(Object param) {
        return !isBlank(param);
    }

    public static boolean isPositive(String str) {
        Pattern pattern = Pattern.compile("^\\d+$|-\\d+$"); // 就是判断是否为整数
        Matcher isPositive = pattern.matcher(str);
        return isPositive.matches();
    }

    public static boolean isFloat(String str) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");// 判断是否为小数
        Matcher isPositive = pattern.matcher(str);
        return isPositive.matches();
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isLetterOrNum(String str) {
        if (isBlank(str))
            return false;
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                flag = true;
            } else {
                break;
            }
        }
        return flag;
    }

    public static String convertIntToString(int value, int length) {
        StringBuilder buf = new StringBuilder("" + value);
        while (buf.toString().length() < length) {
            buf.insert(0, "0");
        }
        return buf.toString();
    }


    /**
     * @return 字符串
     * @toString :十六进制ASCII码转换为字符串
     */
    public static String toString(String str) {
        // String str = "77617463682e657865";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < str.length(); i += 2) {
            result.append((char) Integer.parseInt(str.substring(i, i + 2), 16));
        }
        return result.toString();
    }

    /**
     * @return ASCII码
     * @toHexAscii :字符串转换为十六进制ASCII码
     */
    public static String toHexAscii(String str) {
        // String s = "watch.exe";// 字符串
        StringBuffer result = new StringBuffer();
        char[] chars = str.toCharArray(); // 把字符中转换为字符数组
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
                result.append(Integer.toHexString((int) c));
            else
                result.append(c);
        }
        // result.insert(0, (result.length() / 2));
        return result.toString();
    }

    public static String toHexAscii(String str, int index) {
        String starStr = str.substring(0, index);
        String endStr = str.substring(index + 1);
        char c = str.charAt(index);
        return starStr + Integer.toHexString(c) + endStr;
    }

    public static String asciiToChar(String str, int startIndex) {
        String starStr = str.substring(0, startIndex);
        String endStr = str.substring(startIndex + 2);
        String asciiStr = str.substring(startIndex, startIndex + 2);
        // int asciiInt = Integer.parseInt(asciiStr);
        return starStr + (char) Integer.parseInt(asciiStr, 16) + endStr;
    }

    /**
     * 判断字符串是否为十六进制
     *
     * @param str 传入字符串
     * @Author Alvin
     */
    public static boolean isHexNumberRex(String str) {
        Pattern p = Pattern.compile("^[0][x][0-9a-fA-F]+$");
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断字符串是否为十进制数字
     * *@param str 传入字符串
     *
     * @Author Alvin
     */
    public static boolean isOctNumberRex(String str) {
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }

    /*
     *删除字符串末尾多余的0
     *@param str 传入字符串
     *@Author Alvin
     * */
    public static String delZero(String src) {
        if (src.endsWith("0"))
            return delZero(src.substring(0, src.length() - 1));
        else
            return src;
    }

    public static String getSuccessJson(String msg) {
        return "{\"success\":\"true\",\"msg\":\"" + msg + "\"}";
    }

    public static String getFailJson(String msg) {
        return "{\"success\":\"false\",\"msg\":\"" + msg + "\"}";
    }

    public static boolean isSuccessInfo(String msg) {
        return msg.contains("\"success\":\"true\"");
    }


    public static String produceIntToString(int i, int digit) {
        if (digit == 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder("" + i);
        while (buf.toString().length() < digit) {
            buf.insert(0, "0");
        }
        return buf.toString();
    }

    /**
     * 加密epc
     */
    public static String encryptEpc(String epc) {
        return epc;
    }

    public static String addDay(Date d, int add, String dateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(new Date(d.getTime() + add * 24 * 60 * 60 * 1000));
    }

    public static String addDay(String d, int add, String dateFormat) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        // Date date = df.parse(d);
        // return df.format(new Date(date.getTime() + add * 24 * 60 * 60 * 1000));
        Calendar c = Calendar.getInstance();
        Date myDate = df.parse(d);
        c.setTime(myDate);
        // c.add(Calendar.MONTH, 8);
        c.add(Calendar.DATE, add);
        myDate = c.getTime();
        return df.format(myDate);
    }

    public static String addDay(String d, int add) throws Exception {
        return addDay(d, add, "yyyy-MM-dd");
    }

    public static String reduceDay(String d, int reduce, String dateFormat) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        // Date date = df.parse(d);
        // return df.format(new Date(date.getTime() - reduce * 24 * 60 * 60 * 1000));
        Calendar c = Calendar.getInstance();
        Date myDate = df.parse(d);
        c.setTime(myDate);
        c.add(Calendar.DATE, -reduce);
        myDate = c.getTime();
        return df.format(myDate);
    }

    public static String reduceDay(String d, int reduce) throws Exception {
        return reduceDay(d, reduce, "yyyy-MM-dd");
    }

    public static long dateInterval(String d1, String d2) throws Exception {
        return dateInterval(d1, d2, "yyyy-MM-dd");
    }

    public static long dateInterval(String d1, String d2, String dataFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        Date date1 = sdf.parse(d1);
        Date date2 = sdf.parse(d2);
        // 从间隔毫秒变成间隔天数
        long gap = (date2.getTime() - date1.getTime()) / (1000 * 3600 * 24);
        return gap;
    }


    public static String getDecimal(double value, String decimal) {
        DecimalFormat df2 = new DecimalFormat(decimal);
        return df2.format(value);
    }

    public static String convertSkuToEpc(String uniqueCode, int[] indexChars) throws Exception {
        String epc = uniqueCode;
        for (int i = 0; i < indexChars.length; i++) {
            int charIndex = indexChars[i] + i;
            epc = CommonUtil.toHexAscii(epc, charIndex);
        }
        return epc;
    }

    public static String convertEpcToUniqueCode(String epc, int[] indexChars) throws Exception {
        String uniqueCode = epc;
        for (int i = 0; i < indexChars.length; i++) {
            int charIndex = indexChars[i];
            uniqueCode = CommonUtil.asciiToChar(uniqueCode, charIndex);
        }
        return uniqueCode;
    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    //region 16/32位MD5加密

    /***
     * MD5加码 生成32位小写md5码
     */
    public static String string2MD5(String inStr) {
        try {
            String sign = sign(inStr, "UTF-8");
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }
    }

    // MD5加码。32位小写
    public static String sign(String data, String encode) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes(encode));
        byte[] bArray = md.digest();
        StringBuilder output = new StringBuilder(32);
        for (int i = 0; i < bArray.length; i++) {
            String temp = Integer.toHexString(bArray[i] & 0xff);
            if (temp.length() < 2) {
                output.append("0");
            }
            output.append(temp);
        }
        return output.toString();
    }

    /**
     * @param num   要改变的Double
     * @param digit 保留几位
     * @return
     */
    public static Double doubleChange(Double num, int digit) {
        BigDecimal bg = new BigDecimal(num);
        double numnew = bg.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return numnew;
    }

    //四舍五入把double转化int整型，0.5进一，小于0.5不进一
    public static int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    /**
     * @Description:复制文件夹的内容
     * @author:chenzhifan
     * @time:2016年5月23日 上午11:15:33
     */
    public static void copyDir(File[] fl, File file) {
        if (!file.exists()) // 如果文件夹不存在
            file.mkdir(); // 建立新的文件夹
        for (int i = 0; i < fl.length; i++) {
            if (fl[i].isFile()) { // 如果是文件类型就复制文件
                try {
                    FileInputStream fis = new FileInputStream(fl[i]);
                    FileOutputStream out = new FileOutputStream(new File(file
                            .getPath()
                            + File.separator + fl[i].getName()));

                    int count = fis.available();
                    byte[] data = new byte[count];
                    if ((fis.read(data)) != -1) {
                        out.write(data); // 复制文件内容
                    }
                    out.close(); // 关闭输出流
                    fis.close(); // 关闭输入流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fl[i].isDirectory()) { // 如果是文件夹类型
                File des = new File(file.getPath() + File.separator
                        + fl[i].getName());
                des.mkdir(); // 在目标文件夹中创建相同的文件夹
                copyDir(fl[i].listFiles(), des); // 递归调用方法本身
            }


        }
    }

    /**
     * @Description:加密-32位小写
     * @author:chenzhifan
     * @time:2016年5月23日 上午11:15:33
     */
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }

}
