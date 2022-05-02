package com.someget.admin.common.exception;

import lombok.Data;

/**
 * 自定义异常
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Data
public class MyException extends RuntimeException {

    private String msg;
    private int code;

    public MyException() {
        this.code = 500;
    }

    public MyException(String msg,Throwable cause) {
        super(msg,cause);
        this.msg = msg;
    }

    public MyException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public MyException(String msg, int code,Throwable cause) {
        super(msg,cause);
        this.msg = msg;
        this.code = code;
    }

    public MyException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }
}
