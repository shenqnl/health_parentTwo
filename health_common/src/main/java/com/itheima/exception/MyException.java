package com.itheima.exception;

/**
 * 自定义异常：
 * 业务异常：
 * 1. 友好提示
 * 2. 区分系统与业务异常
 * 3. 终止已知不符合业务逻辑代码的继续执行
 */
public class MyException extends RuntimeException {

    /**
     * 构建 就要传入提示的信息
     * @param message
     */
    public MyException(String message){
        super(message);
    }
}
