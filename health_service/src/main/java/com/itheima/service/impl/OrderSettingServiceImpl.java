package com.itheima.service.impl;

import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.MyException;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入
     *
     * @param orderSettingList
     */
    @Override
    public void doImport(List<OrderSetting> orderSettingList) {
        //1. 遍历预约设置集合
        for (OrderSetting orderSetting : orderSettingList) {
            //2. 通过日期查询预约设置信息
            OrderSetting osInDb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            //3. 存在
            if(null != osInDb) {
                // 3.1 判断最大预约数是否小于已预约数
                int reservations = osInDb.getReservations();//已预约数
                if(orderSetting.getNumber() < reservations) {
                    //    则要报错
                    throw new MyException("最大预约数不能小于已预约人数!");
                }
                //    否则可以更新
                orderSettingDao.updateNumber(orderSetting);
            }else {
                //4. 不存在，则插入
                orderSettingDao.add(orderSetting);
            }
        }
    }

    /**
     * 日历展示预约设置信息
     *
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        return orderSettingDao.getOrderSettingByMonth(month + "%");
    }
}
