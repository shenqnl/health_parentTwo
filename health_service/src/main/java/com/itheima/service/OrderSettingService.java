package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;

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
}
