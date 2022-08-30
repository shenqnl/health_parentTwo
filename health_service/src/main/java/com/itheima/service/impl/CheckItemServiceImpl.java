package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 检查项业务
 * @Transactional
 */
@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    /**
     * 添加检查项
     *
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //1. 判断是否有查询条件，如果有查询条件则要使用模糊查询，则查询的条件就要拼接%，再调用dao查询
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 不为空，有条件
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //2. 使用PageHelper来查询
        // * PageHelper.startPage(页码，大小)
        Page<CheckItem> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // * 条件查询即可,返回Page对象
//        List<CheckItem> list = checkItemDao.findByCondition(queryPageBean.getQueryString());
//        ArrayList<CheckItem> list1 = checkItemDao.findByCondition(queryPageBean.getQueryString());
        ////第二种，Mapper接口方式的调用，推荐这种使用方式。
        // 底层使用ThreadLocal,创建page对象后存入线程中，将来在dao执行过程中就可以自由的存取
        Page<CheckItem> page2 = checkItemDao.findByCondition(queryPageBean.getQueryString());
        System.out.println("page==============" + page);
        System.out.println("page2=============" + page2);
        System.out.println(page==page2);

        // * 分页结果就是page.getResult
        // * 总记录数page.getTotal就可以了
        //3. 封装到PageResult返回给controller
        PageResult<CheckItem> pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 通过id删除检查项
     * 删除分：
     *   逻辑删除 表state字段，state=0 失效，不可见
     *   物理删除 真的把表中记录删除
     * 写删除语句或更新语句时【注意】where
     * @param id
     */
    @Override
    public void deleteById(int id) {
        // 判断要删除的这个检查项是否被检查组使用了
        // 统计检查项的id被检查组检查项关系表(t_checkgroup_checkitem)中的个数
        int count = checkItemDao.findCountByCheckItemId(id);
        // 使用了count>0，不能删除，抛出自定义异常
        if(count > 0){
            throw new MyException("该检查项已经被检查组使用了，不能删除");
        }
        // count=0没有被使用，则可以删除
        checkItemDao.deleteById(id);
    }

    /**
     * 通过id查询检查项
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 更新检查项
     *
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 查询所有的检查项
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
