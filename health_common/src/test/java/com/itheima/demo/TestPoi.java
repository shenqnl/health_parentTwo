package com.itheima.demo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author shenhuamin
 * @date 2022/8/31
 */
public class TestPoi {
    /*
    POI结构：
HSSF － 提供读写Microsoft Excel XLS格式档案的功能 97-2003
XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能（我们使用） 2007
HWPF － 提供读写Microsoft Word DOC格式档案的功能 .doc word
HSLF － 提供读写Microsoft PowerPoint格式档案的功能 ppt
HDGF － 提供读Microsoft Visio格式档案的功能
HPBF － 提供读Microsoft Publisher格式档案的功能
HSMF － 提供读Microsoft Outlook格式档案的功能 邮件

我们使用：XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能
POI操作offices文档，常用它操作excel, 2007版以后的excel  XSSFWorkbook
     */
    // 读取excel
    @Test
    public void readExcel1() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\javasejavaee\\sevennew\\read.xlsx");
        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        //遍历工作表获得行对象
        for (Row row : sheet) {
            //遍历行对象获取单元格对象
            for (Cell cell : row) {
                //获得单元格中的值
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }


    /*
XSSFWorkbook：工作簿
XSSFSheet：工作表
XSSFRow：行
XSSFCell：单元格
上面案例是通过遍历工作表获得行，遍历行获得单元格，最终获取单元格中的值。
还有一种方式就是获取工作表最后一个行号，
从而根据行号获得行对象，通过行获取最后一个单元格索引，
从而根据单元格索引获取每行的一个单元格对象，代码如下：
     */
    // 获取excel，获取最后一行
    @Test
    public void readExcel2() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\javasejavaee\\sevennew\\read.xlsx");
        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取当前工作表最后一行的行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            //根据行号获取行对象
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for(short j=0;j<lastCellNum;j++){
                String value = row.getCell(j).getStringCellValue();
                System.out.println(value);
            }
        }
        workbook.close();
    }

    // 创建excel
    @Test
    public void createExcel() throws IOException {
        //在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建工作表，指定工作表名称
        XSSFSheet sheet = workbook.createSheet("传智播客");

        //创建行，0表示第一行
        XSSFRow row = sheet.createRow(0);
        //创建单元格，0表示第一个单元格
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");

        //通过输出流将workbook对象下载到磁盘
        FileOutputStream out = new FileOutputStream("E:\\javasejavaee\\sevennew\\itcast.xlsx");
        workbook.write(out);
        out.flush();//刷新
        out.close();//关闭
        workbook.close();
    }
}
