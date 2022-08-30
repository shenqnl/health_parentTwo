package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/30
 */
public interface CheckGroupDao {
    /**
     * 条件查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项关系
     * mybatis中 参数类型相同时，要取别名
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

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
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项的关系
     * @param checkGroupId
     */
    void deleteCheckGroupCheckItem(Integer checkGroupId);

    /**
     * 统计检查组id被套餐（t_setmeal_checkgroup）使用的个数
     * @param id
     * @return
     */
    int findCountByCheckGroupId(int id);

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
