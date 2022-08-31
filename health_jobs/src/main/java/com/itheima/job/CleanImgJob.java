package com.itheima.job;

import com.itheima.dao.SetmealDao;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 清理7牛上的垃圾图片后台任务
 */
@Component
public class CleanImgJob {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 任务的方法
     */
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

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");

        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");

        list1.removeAll(list2);

        list1.forEach(System.out::println);
    }
}
