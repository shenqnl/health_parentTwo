package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/29
 */
public interface CheckItemDao {
    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 统计检查项的id被检查组检查项关系表(t_checkgroup_checkitem)中的个数
     * @param id
     * @return
     */
    int findCountByCheckItemId(int id);

    /**
     * 通过id删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过id查询
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
