package com.knowdata.framework.core.exception;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/5/24 16:24
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
public class ApplicationException extends RuntimeException {
    private String errorMessage;
    private String errorCode;

    public ApplicationException(){

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause){
        super(cause);
    }

    public ApplicationException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ApplicationException(String errorCode, String errorMessage){
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ApplicationException(ApplicationError applicationErrorCode){
        this.errorCode = applicationErrorCode.getErrorCode();
        this.errorMessage = applicationErrorCode.getErrorMessage();
    }

    public ApplicationException(String errorMessageTemplate , Object ... values){
        super(String.format(errorMessageTemplate, values));
        this.errorMessage = String.format(errorMessageTemplate, values);
    }

    public ApplicationException(Throwable cause, String errorMessage, String errorCode){
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
