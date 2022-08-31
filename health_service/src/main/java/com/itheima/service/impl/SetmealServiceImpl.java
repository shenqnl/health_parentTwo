package com.itheima.service.impl;

import com.itheima.dao.SetmealDao;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 1. 添加套餐
        setmealDao.add(setmeal);
        // 2. 获取套餐的id
        Integer setmealId = setmeal.getId();
        // 3. 遍历选中的检查组id集合，非空判断
        if(null != checkgroupIds) {
            // 4. 添加套餐与检查组关系
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
        // 5. 事务控制
    }
}
