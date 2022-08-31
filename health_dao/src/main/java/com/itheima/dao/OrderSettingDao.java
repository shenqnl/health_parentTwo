package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
public interface OrderSettingDao {

    /**
     * 通过日期查询预约设置信息
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 通过日期,更新最大预约数
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 添加预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 通过月份查询预约设置信息
     * @param s
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String s);

    /**
     * 查询当前月份的预约设置
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonthOne(Map map);
}
