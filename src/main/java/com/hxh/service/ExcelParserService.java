package com.hxh.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelParserService {
    /**
     * 解析excel表格分析下次班次日期
     * @param fileInputStream
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public Map<String, String> analysisExcel(InputStream fileInputStream) throws IOException, ParseException {
        Map<String, String> map = new HashMap<>();
        // 替换为你的工作表名称
        String sheetName = "汇总";
        // 替换为你想要读取的行和列的索引
        int rowIndex = 0;
        int cellIndex = 0;
        //当前日期之后的数据
        int dateCellIndex = 0;
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取今天的日期
        String today = simpleDateFormat.format(date);
        // 遍历每一行
        for (Row row : sheet) {
            // 遍历每一列
            for (Cell cell : row) {
                // 判断单元格内容是否包含目标内容
                if(cell.getRowIndex() == 0 && cell.getColumnIndex() > 0){
                    if(null != cell && null != cell.getDateCellValue()){
                        String cellDate = simpleDateFormat.format(cell.getDateCellValue().getTime());
                        if (cellDate.contains(today)) {
                            dateCellIndex = cell.getColumnIndex();
                            continue;
                        }
                    }
                }
                if (cell.toString().contains("李曼曼")) {
                    rowIndex = row.getRowNum();
                    // 输出找到的内容以及行和列索引
                    break;
                }
            }
        }
        //白班列
        int dayCellIndex = -1;
        //晚班列
        int nightCellIndex = -1;
        //白加晚列
        int allDayCellIndex = -1;
        //休息列
        int restCellIndex = -1;
        //只统计遍历出的第一个班次
        int dayCount = 0;
        int nightCount = 0;
        int allDayCount = 0;
        int restCount = 0;
        //当前班次持续时间
        int dayNumber = 0;
        int nightNumber = 0;
        int allDayNumber = 0;
        int restNumber = 0;
        for (Cell cell : sheet.getRow(rowIndex)) {
            if(cell.getColumnIndex() > dateCellIndex){
                if(cell.toString().contains("白班") && dayCount == 0 ){
                    dayCellIndex = cell.getColumnIndex();
                    dayCount++;
                }else if(cell.toString().contains("白加晚") && allDayCount == 0 ){
                    allDayCellIndex = cell.getColumnIndex();
                    allDayCount++;
                }else if (cell.toString().contains("晚班") && nightCount == 0 ){
                    nightCellIndex = cell.getColumnIndex();
                    nightCount++;
                }else if(cell.toString().contains("休息") && restCount == 0 ){
                    restCellIndex = cell.getColumnIndex();
                    restCount++;
                }
            }
        }
        //白班单元格
        Cell dayCell = null;
        String dayTime = null;
        String dayWeek = null;
        //晚班单元格
        Cell nightCell = null;
        String nightTime = null;
        String nightWeek = null;
        //白加晚单元格
        Cell allDayCell = null;
        String allDayTime = null;
        String allDayWeek = null;
                //休息单元格
        Cell restCell = null;
        String restTime = null;
        String restWeek = null;
        if(dayCellIndex != -1){
            dayCell = sheet.getRow(0).getCell(dayCellIndex);
            //下次白班日期
            dayTime = simpleDateFormat.format(dayCell.getDateCellValue().getTime());
            dayWeek = sheet.getRow(1).getCell(dayCellIndex).getStringCellValue();
        }
        if(nightCellIndex != -1){
            nightCell = sheet.getRow(0).getCell(nightCellIndex);
            //下次晚班日期
            nightTime = simpleDateFormat.format(nightCell.getDateCellValue().getTime());
            nightWeek = sheet.getRow(1).getCell(nightCellIndex).getStringCellValue();
        }
        if(allDayCellIndex != -1){
            allDayCell = sheet.getRow(0).getCell(allDayCellIndex);
            //下次白加晚日期
            allDayTime = simpleDateFormat.format(allDayCell.getDateCellValue().getTime());
            allDayWeek = sheet.getRow(1).getCell(allDayCellIndex).getStringCellValue();
        }
        if(restCellIndex != -1){
            restCell = sheet.getRow(0).getCell(restCellIndex);
            //下次休息日期
            restTime = simpleDateFormat.format(restCell.getDateCellValue().getTime());
            restWeek = sheet.getRow(1).getCell(restCellIndex).getStringCellValue();
        }
//        Map<String, Map<String, String>> monthMap = analysisExcelForEveryDay(sheet, rowIndex,servletRequest);
//        servletRequest.setAttribute("monthMap",monthMap);
        //List<Date> list = new ArrayList<>();
        //list.add(simpleDateFormat.parse(dayTime));
        //list.add(simpleDateFormat.parse(allDayTime));
        //list.add(simpleDateFormat.parse(nightTime));
        //list.add(simpleDateFormat.parse(restTime));
        //Collections.sort(list);
        ////白班的时间范围
        //String dayTimeRange = "";
        ////晚班的时间范围
        //String nightTimeRange = "";
        ////白加晚的时间范围
        //String allDayTimeRange = "";
        ////休息的时间范围
        //String restTimeRange = "";
        //for (int i = 0; i < list.size(); i++) {
        //    String format = simpleDateFormat.format(list.get(i));
        //    if(format.equals(dayTime) && i <list.size()-1){
        //        dayTimeRange = dayTime + "——" + simpleDateFormat.format(list.get(i+1)) ;
        //    }else if(format.equals(allDayTime)  && i <list.size()-1){
        //        allDayTimeRange = allDayTime + "——" + simpleDateFormat.format(list.get(i+1)) ;
        //    }else if(format.equals(nightTime) && i <list.size()-1){
        //        nightTimeRange = nightTime + "——" + simpleDateFormat.format(list.get(i+1)) ;
        //    }else if(format.equals(restTime) && i <list.size()-1){
        //        restTimeRange = restTime + "——" + simpleDateFormat.format(list.get(i+1)) ;
        //    }
        //}
        fileInputStream.close();
        map.put("dayTime",dayTime);
        map.put("nightTime",nightTime);
        map.put("restTime",restTime);
        map.put("dayWeek",dayWeek);
        map.put("nightWeek",nightWeek);
        map.put("restWeek",restWeek);
        map.put("allDayWeek",allDayWeek);
        map.put("allDayTime",allDayTime);
        return map;
    }

    public  Map<String, Map<String, String>> analysisExcelForEveryDay(Sheet sheet,int rowIndex ,HttpServletRequest servletRequest) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Map<String, String> dateWeekMap = new TreeMap<>();
        Map<String, String> dateWorkMap = new TreeMap<>();
        Map<String, String> map = new TreeMap<>();
        Map<String, List<String>> dataMap = new TreeMap<>();
        List<String> dateList = new ArrayList<>();
        List<String> workList = new ArrayList<>();
        List<String> weekList = new ArrayList<>();
        List<List<String>> dataList = new ArrayList<>();
        Map<String, Map<String, String>> monthMap = new HashMap<>();
        for (Cell cell : sheet.getRow(rowIndex)) {
            if(cell.getColumnIndex() == 0){
                continue;
            }
            //map.put(simpleDateFormat.format(sheet.getRow(0).getCell(cell.getColumnIndex()).getDateCellValue()),cell.getStringCellValue());
            dateWeekMap.put(simpleDateFormat.format(sheet.getRow(0).getCell(cell.getColumnIndex()).getDateCellValue()),sheet.getRow(1).getCell(cell.getColumnIndex()).getStringCellValue());
            dateWorkMap.put(simpleDateFormat.format(sheet.getRow(0).getCell(cell.getColumnIndex()).getDateCellValue()),cell.getStringCellValue());
            dateList.add(simpleDateFormat.format(sheet.getRow(0).getCell(cell.getColumnIndex()).getDateCellValue()));
            workList.add(sheet.getRow(rowIndex).getCell(cell.getColumnIndex()).getStringCellValue());
            weekList.add(sheet.getRow(1).getCell(cell.getColumnIndex()).getStringCellValue());
            //monthMap.put(sheet.getRow(1).getCell(cell.getColumnIndex()).getStringCellValue(),map);
            //if(cell.getColumnIndex() > dateCellIndex){
            //
            //}
        }
        dataList.add(dateList);
        dataList.add(weekList);
        dataList.add(workList);
        dataMap.put("星期",weekList);
        dataMap.put("班次",workList);
        dataMap.put("日期",dateList);
        servletRequest.setAttribute("dataMap",dataMap);
        servletRequest.setAttribute("dateWeekMap",dateWeekMap);
        servletRequest.setAttribute("dateWorkMap",dateWorkMap);
        monthMap.remove("日期");
        for (Map.Entry<String, String> dayEntry : dateWeekMap.entrySet()) {
            String date = dayEntry.getKey();
            String dayOfWeek = dayEntry.getValue();
            String shift = dateWorkMap.get(date);

            if (shift != null) {
                // 如果日期在两个Map中都存在，则添加到合并的Map中
                monthMap.computeIfAbsent(dayOfWeek, k -> new HashMap<>()).put(date, shift);
            }
        }
        return monthMap;
    }
}