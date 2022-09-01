package com.itheima.controller;

import com.itheima.dao.SetmealDao;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author shenhuamin
 * @date 2022/8/31
 * 这个是跑七牛图片多余图片删除
 */
@Component
public class TaskDemo {
    private static final Logger log = LoggerFactory.getLogger(TaskDemo.class);

    @Autowired
    private SetmealDao setmealDao;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void TaskD() {
        //每隔5秒隔行一次
        //默认单线程，阻塞
        log.info("开始执行清理7牛上的垃圾图片");
        System.out.println("七牛-----------");
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void  task() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        System.out.println(date + ":定时任务TaskJobTest");
    }

    /**
     * 任务的方法
     */
    @Scheduled(cron = "0/59 * * * * ? ")
    public void clean7NiuImgJob(){
        log.info("开始执行清理7牛上的垃圾图片");
        // 1. 查询7牛上的所有图片
        List<String> imgsIn7Niu = QiNiuUtils.listFile();
        log.info("7牛上有 【{}】 张图片.", imgsIn7Niu.size());
        // 2. 数据库中的图片
        List<String> imgsInDb = setmealDao.selectImgs();
        log.info("数据库上有 【{}】 张图片.", null==imgsInDb?0:imgsInDb.size());
        // 3. 7牛减去数据库的
        imgsIn7Niu.removeAll(imgsInDb);
        // 4. 调用7牛删除
        String[] imgs2Delete = imgsIn7Niu.toArray(new String[]{});
        log.info("需要删除的7牛上的图片有【{}】张", imgs2Delete.length);
        QiNiuUtils.removeFiles(imgs2Delete);
        log.info("执行清理7牛上的垃圾图片完成");
    }


}