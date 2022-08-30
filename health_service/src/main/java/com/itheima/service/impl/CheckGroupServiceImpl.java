package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/30
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //1. 判断是否有查询条件，如果有查询条件则要使用模糊查询，则查询的条件就要拼接%，再调用dao查询
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 不为空，有条件
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //2. 使用PageHelper来查询
        // * PageHelper.startPage(页码，大小)
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // * 条件查询即可,返回Page对象
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());

        // * 分页结果就是page.getResult
        // * 总记录数page.getTotal就可以了
        //3. 封装到PageResult返回给controller
        PageResult<CheckGroup> pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 先添加检查组
        checkGroupDao.add(checkGroup);
        // 获取新增检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 遍历选中检查项id集合, 非空判断
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                // 添加检查组与检查项关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
        // 事务控制
    }

    /**
     * 通过id查询检查组
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 通过检查组id查询选中的检查项id集合
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 更新检查组
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, List<Integer> checkitemIds) {
        // 先更新检查组
        checkGroupDao.update(checkGroup);
        // 获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroupId);
        // 遍历选中的检查项id集合，非空
        if(null != checkitemIds){
            // 添加新关系
            checkitemIds.forEach(checkItemId->checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkItemId));
        }
        // 事务控制
    }

    /**
     * 查询所有的检查组
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    /**
     * 通过id删除检查组
     *
     * @param id
     */
    @Override
    public void deleteById(int id) {
        //判断检查组是否被套餐使用
        //统计检查组id被套餐（t_setmeal_checkgroup）使用的个数
        int count = checkGroupDao.findCountByCheckGroupId(id);
        if (count>0){
            throw new MyException("该检查组被套餐使用了，不能删除");
        }
        //先删除检查组与检查项关系，即取消多对多关系
        // 删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //count没使用套餐，进入删除
        checkGroupDao.deleteById(id);
    }
}
