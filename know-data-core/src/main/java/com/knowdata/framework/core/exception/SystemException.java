package com.knowdata.framework.core.exception;

/**
 * 系统公共异常处理
 */
public class SystemException extends Exception {

    private String errorCode;
    private String errorMessage;

    /**
     * @param systemError
     */
    public SystemException(SystemError systemError) {
        super(systemError.getErrorCode() + "," + systemError.getErrorMessage());
        this.setErrorCode(systemError.getErrorCode());
        this.setErrorMessage(systemError.getErrorMessage());
    }


    public SystemException(String errorCode, String errorMessage) {
        super(errorCode + "," + errorCode);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}