package com.hxh.controller;

import com.hxh.domain.ResultEntity;
import com.hxh.service.ExcelParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@RestController
public class ExcelParserController {
    @Autowired
    private ExcelParserService excelParserService;

    /**
     * 解析上传的excel
     * @param file
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    public ResultEntity analysisExcel(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        // TODO: 2023/11/12  添加一个上传日志，保留excel名称和上传时间
        Map<String, String> map = excelParserService.analysisExcel(file.getInputStream());
//        WeatherController.queryWeather(servletRequest);
        file.getInputStream().close();
        return ResultEntity.SUCCESS(map);
        //return "redirect:/weather/hz";
    }

    /**
     * 解析初始化的excel
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/init")
    public ResultEntity initExcel() throws IOException, ParseException {
        String filePath = "/opt/information/2023年12月排班(1).xlsx";
        //String filePath = "C:/Users/10071/Desktop/code/information/2023年11月排班.xlsx";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Map<String, String> map = excelParserService.analysisExcel(fileInputStream);
//        WeatherController.queryWeather(servletRequest);
        fileInputStream.close();
        return ResultEntity.SUCCESS(map);
        //return "redirect:/weather/hz";
    }
}
