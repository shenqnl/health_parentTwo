package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/29
 * 操作检查项
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Autowired
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    // 权限控制
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    //@RequestMapping(value = "add", method = RequestMethod.POST)
    public Result add(@RequestBody CheckItem checkItem){
        // 调用service
        checkItemService.add(checkItem);
        // 响应结果
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用业务查询,返回pageResult
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        // 返回给页面
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 通过id删除检查项
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 更新检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        // 调用service
        checkItemService.update(checkItem);
        // 响应结果
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    /**
     * 查询所有的检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList);
    }

}
