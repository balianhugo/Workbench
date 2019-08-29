package dev.gavin.wb.exception;

/**
 * 自定义 WB 系统异常基类，若定义其他业务类型异常可继承扩展
 *
 * Created by Administrator on 2017/4/5.
 */
public class WbException extends Exception {

    /**
     * 异常错误编码
     */
    private String errorCode;

    public WbException(Throwable cause){
        super(cause);
    }

    public WbException(String message){
        super(message);
    }

    public WbException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public WbException(String errorCode, String message, Throwable cause){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
