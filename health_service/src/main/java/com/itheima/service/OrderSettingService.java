package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
public interface OrderSettingService {
    /**
     * 批量导入
     * @param orderSettingList
     */
    void doImport(List<OrderSetting> orderSettingList);

    /**
     * 日历展示预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
}
