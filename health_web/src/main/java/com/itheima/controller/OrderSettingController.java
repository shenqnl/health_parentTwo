package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 批量导入预约设置
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        // 1. 读取excel中的数据
        try {
            List<String[]> stringArrayList = POIUtils.readExcel(excelFile);
            // 2. 把List<String[]>转成List<OrderSetting>数据
            List<OrderSetting> orderSettingList = new ArrayList<>(stringArrayList.size());
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            for (String[] stringArray : stringArrayList) {
                // stringArray[0] 日期
                // stringArray[1] 数量
                OrderSetting os = new OrderSetting(sdf.parse(stringArray[0]), Integer.parseInt(stringArray[1]));
                orderSettingList.add(os);
            }
            // 3. 调用业务批量导入
            orderSettingService.doImport(orderSettingList);
            // 4. 返回结果
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (MyException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            log.error("批量导入失败",e);
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 日历展示预约设置信息
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonthTwo")
    public Result getOrderSettingByMonthTwo(String month){
        List<Map<String,Integer>> orderSettingDatas = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingDatas);
    }

    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * @param date
     * @return
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){//参数格式为：2019-03
        try{
            List<Map> list = orderSettingService.getOrderSettingByMonthOne(date);
            //获取预约设置数据成功
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            //获取预约设置数据失败
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
}
