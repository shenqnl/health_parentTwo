package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/29
 */
public interface CheckItemService {
    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 更新检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();
}
