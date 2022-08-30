package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * 切面中的通知，增强controller中的方法，异常通知
 */
@RestControllerAdvice
public class MyExceptionAdvice {
    /**
     * 记录日志对象
     * 参数：MyExceptionAdvice.class,定位在哪一行代码出问题，打印日志里会显示出是在哪具类的第几行代码
     * 用来做异常信息分类
     * info: 记流程性的记录，执行到哪里？方便跟踪问题，系统还在运行 订单支付成功
     * debug: 记重要性关键的key, 订单(id)支付成功
     * error: 记异常
     *
     * 分类：
     * 保存到数据库: 关键数据，转帐, 记录更新前后的变化，如果数据有问题，通过日志恢复
     * 记录日志文件: 统计性，流程性 ELK
     */
    private static final Logger log = LoggerFactory.getLogger(MyExceptionAdvice.class);

    /**
     * security 没有权限处理 AccessDeniedException就security的包
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException e){
        // 没有权限
        return new Result(false, "没有权限");
    }

    /**
     * ExceptionHandler 注解的作用是来捕获异常
     * 属性value, 指定异常的类型
     * 方法的形参类型就是 指定异常的类型
     * @param e
     * @return
     */
    @ExceptionHandler(value=MyException.class)
    public Result handleMyException(MyException e){
        // 返回失败的提示信息, 给前端
        return new Result(false, e.getMessage());
    }

    /**
     * 捕获大的异常，系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value=Exception.class)
    public Result handleException(Exception e){
        log.error("发生未知异常",e);
        // e.printStackTrace(); System.out.println
        // 返回失败的提示信息
        return new Result(false, "发生未知异常，请稍后重试!");
    }
}
