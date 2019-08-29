package dev.gavin.wb.tools;

/**
 * 表单验证通用方法，有不合法就返回 true，否则返回 false；
 * Created by Administrator on 2016-11-21.
 */
public class FormValid {

    /**
     * 验证是否为空
     */
    public static final boolean validEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String && obj.toString().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符长度
     * @param str 验证的字符
     * @param min 最小值
     * @param max 最大值
     */
    public static final boolean validLength(String str, int min, int max){
        int len = str.length();
        if(min > len || len > max){
            return true;
        }
        return false;
    }

}
