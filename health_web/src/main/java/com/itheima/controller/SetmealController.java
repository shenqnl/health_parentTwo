package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);
    @Autowired
    private SetmealService setmealService;

    /**
     * 上传套餐图片
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        // 生成唯一的文件名
        String originalFilename = imgFile.getOriginalFilename();
        // 后缀名, 包含了.   e.g: .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String uniqueFilename = uuid+suffix;
        // 调用7牛上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), uniqueFilename);
            // 返回给页面, map, domain, imgName
            Map<String,String> map = new HashMap<String,String>();
            map.put("domain",QiNiuUtils.DOMAIN);
            map.put("imgName",uniqueFilename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            log.error("上传图片失败了",e);
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

}
