package com.itheima.dao;

import com.itheima.pojo.Setmeal;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
public interface SetmealDao {
    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckGroup(Integer setmealId, Integer checkgroupId);

    /**
     * 别的模块类CleanImgJob.java引用
     * 查询套餐表中的所有图片名
     * @return
     */
    List<String> selectImgs();
}
