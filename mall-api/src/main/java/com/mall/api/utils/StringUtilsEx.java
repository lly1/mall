/**
 * .
 */
package com.mall.api.utils;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtilsEx extends org.apache.commons.lang3.StringUtils {

    private static Logger logger = LoggerFactory.getLogger(StringUtilsEx.class);
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    public static String getColor(String str) {
        String color = "#FFF";
        if ("A".equals(str)) {
            color = "#FF9900";
        }
        if ("B".equals(str)) {
            color = "#FFFF00";
        }
        if ("C".equals(str)) {
            color = "#66FF00";
        }
        return color;
    }

    /**
     * 转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 转换为字节数组
     *
     * @param
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     *
     * @param html
     * @return
     */
    public static String replaceMobileHtml(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }


    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            logger.info("截取字符串异常！", e);
        }
        return "";
    }

    private static Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");

    public static String abbr2(String param, int length) {
        if (param == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        /**
         * 是不是HTML代码
         */
        boolean isCode = false;
        /**
         * 是不是HTML特殊字符,如&nbsp;
         */
        boolean isHTML = false;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            try {
                if (!isCode && !isHTML) {
                    n += String.valueOf(temp).getBytes("GBK").length;
                }
            } catch (UnsupportedEncodingException e) {
                logger.info("不受支持的编码异常!" ,e);
            }

            if (n <= length - 3) {
                result.append(temp);
            } else {
                result.append("...");
                break;
            }
        }
        // 取出截取字符串中的HTML标记
        String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
                "$1$2");
        // 去掉不需要结素标记的HTML标记
        temp_result = temp_result
                .replaceAll(
                        "</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
                        "");
        // 去掉成对的HTML标记
        temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
                "$2");
        // 用正则表达式取出标记

        Matcher m = p.matcher(temp_result);
        List<String> endHTML = new ArrayList<>();
        while (m.find()) {
            endHTML.add(m.group(1));
        }
        // 补全不成对的HTML标记
        for (int i = endHTML.size() - 1; i >= 0; i--) {
            result.append("</");
            result.append(endHTML.get(i));
            result.append(">");
        }
        return result.toString();
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }


    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 如果不为空，则设置值
     *
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
        if (isNotBlank(source)) {
            target = source;
        }
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     *
     * @param objectString 对象串
     *                     例如：row.user.id
     *                     返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i = 0; i < vals.length; i++) {
            val.append("." + vals[i]);
            result.append("!" + (val.substring(1)) + "?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    /**
     * 字符串截取
     */
    public static String getStartIndexStr(String sourceStr, String startStr) {
        String returnStr = "";
        int firstIndex = sourceStr.indexOf(startStr);
        returnStr = sourceStr.substring(firstIndex);
        return returnStr;
    }

    public static String transImagePath(String imagePath) {
        String str = "";
        if (imagePath == null || imagePath.length() < 7) {
            return str;
        }
        imagePath = imagePath.replace("|", ",");
        String[] arr = imagePath.split(",");
        String tStr ;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() < 7) {
                continue;
            }
            //兼容一下老图片地址
            if (!arr[i].contains("userfiles")){
                tStr= getStartIndexStr(arr[i], "files");
            }else {
                tStr = getStartIndexStr(arr[i], "userfiles");
            }
            str = str + tStr + ",";
        }
        return str;
    }


    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字       
        // String   regEx  =  "[^a-zA-Z0-9]";                     
        // 清除掉所有特殊字符  
        String regEx = "\\s*|\t|\r|\n";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     *
     * 功能描述: 字符串首字母大写
     *
     * @param: [name]
     * @return: java.lang.String
     * @auther: 20013425 刘猛
     * @date: 2018-12-14 13:56
     */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }


    /**
     * 字符串处理。如果字符串第一个包含 竖线  |
     */
   public static Boolean  path(String path){
       boolean flag=false;
       if(StringUtilsEx.isNotBlank(path)){
          if(path.startsWith("|")){
              flag=true;
          }
       }
       return flag;
   }

    /**
     * 处理
     * @param imagePath
     * @return
     */
   public static String getListImagePath(List<String> imagePath){
       //去掉null
       List<String> pathList=new ArrayList<>();
       if(CollectionUtils.isNotEmpty(imagePath)){
           for(String obj:imagePath){
               if(obj==null){
                   continue;
               }
               pathList.add(obj);
           }
       }
       String path="";
       StringBuilder sb = new StringBuilder();
       if(CollectionUtils.isNotEmpty(pathList)){
           for(String ss:pathList){
                   int firstIndex = ss.indexOf("f");
                   path = ss.substring(firstIndex, ss.length());
                   path =path+',';
                   sb.append(path);
           }
       }
       return sb.toString();
   }

    public static void main(String[] args) {
       List<String>aa=new ArrayList<>();
       aa.add("https://ls-lppz-images-test.oss-cn-shanghai.aliyuncs.com/files/2019/04/15030243/images/0c4d891843e24c338c62fca6d4974bbd.png");
        aa.add(null);
        aa.add("https://ls-lppz-images-test.oss-cn-shanghai.aliyuncs.com/files/2019/04/15030243/images/2c4d891843e24c338c62fca6d4974bbd.png");
        String pathList=getListImagePath(aa);
        System.out.println(pathList);

    }


    public static String getImagePath(String imagePath){
        String path="";
        if(StringUtilsEx.isNotBlank(imagePath)){
            int firstIndex=imagePath.indexOf("f");
            path=imagePath.substring(firstIndex,imagePath.length());
        }
        return path;
    }


   public static Boolean isFloatNumber(String number){
       boolean flag=false;
       if(StringUtilsEx.isNotBlank(number)){
           if(number.contains(".")){
               flag=true;
           }
       }
       return flag;

   }



    public static Boolean existSpecialChar(String ss){
        boolean flag=false;
        if(StringUtilsEx.isNotBlank(ss)){
            if(ss.contains(",")){
                flag=true;
            }
        }
        return flag;

    }


}
