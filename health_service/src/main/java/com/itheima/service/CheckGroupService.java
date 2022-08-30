package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/30
 */
public interface CheckGroupService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, List<Integer> checkitemIds);

    /**
     * 查询所有的检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 通过id删除检查组
     * @param id
     */
    void deleteById(int id);
}
