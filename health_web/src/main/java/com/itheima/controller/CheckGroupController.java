package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/30
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Autowired
    private CheckGroupService checkGroupService;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用业务查询,返回pageResult
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        // 返回给页面
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 添加检查组
     * @param checkGroup 检查组基本信息 formData
     * @param checkitemIds 选中的检查项id集合
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemIds);
    }

    /**
     * 更新检查组
     * @param checkGroup 检查组基本信息 formData
     * @param checkitemIds 选中的检查项id集合
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,@RequestParam List<Integer> checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有的检查组
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
    }

    /**
     * 通过id删除检查组
     * @param id
     * @return
     */
    @PostMapping("/groupDeleteById")
    public Result deleteById(int id) {
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
