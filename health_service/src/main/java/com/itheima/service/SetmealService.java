package com.itheima.service;

import com.itheima.pojo.Setmeal;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);
}
