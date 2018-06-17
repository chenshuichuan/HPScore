package hpscore.service.impl;


import hpscore.domain.*;
import hpscore.repository.RelativeScoreRepository;
import hpscore.repository.UserRepository;
import hpscore.service.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PingweiScoreService pingweiScoreService;
    @Autowired
    private PingweiService pingweiService;
    @Autowired
    private RelativeScoreRepository relativeScoreRepository;

    //原始打分表
    @Override
    public String reviewExcel(String model) {
        List<String> pingweiList = pingweiService.selectAllCodeByModel(model);

        String[] headers = {
                "作品编号", "作品名称",
                "选题意义","科学性","创新性","开发难易程度","实用价值","文字描述质量及答辩效果"
                ,"总分"};
        String excelName = "2018泛珠赛全国总决赛终评评委打分审核表("+model+")";
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        for (int i = 0; i < pingweiList.size(); i++) {
            CreatePingweiScore(wb, styles, headers,pingweiList.get(i), model);
        }
        // Write the output to a file
        String file = excelName+".xls";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    //相对分、创新分、实用分
    @Override
    public String relativeScoreExcel(String model) {

        String[] headers = {
                "作品编号", "作品名称",
                "评委1","评委2","评委3","评委4","评委5","评委6"
                ,"评委7","评委8","评委9","评委10","评委11",
                "最高分", "最低分", "平均分"};
        String excelName = "2018泛珠赛全国总决赛终评相对分统计表("+model+")";
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        CreateRelativeScore(wb, styles,headers,model);
        CreateInnovationScore(wb, styles,headers,model);
        CreateUsefulScore(wb, styles,headers,model);
        // Write the output to a file
        String file = excelName+".xls";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    @Override
    public String finalScoreExcel(String model) {
        String excelName = "2018泛珠赛全国总决赛作品得分汇总表("+model+")";

        String[] titles = {
                "作品编号", "作品名称", "学校名称",
                "指导老师", "研发学生", "相对分平均分", "名次"};
        List<Works> worksList = scoreService.selectFinalScoreRanking(model);
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet(excelName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(excelName);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$G$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < worksList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                switch (j){
                    case 0: cell.setCellValue(worksList.get(i).getCode());break;
                    case 1: cell.setCellValue(worksList.get(i).getName());break;
                    case 2: cell.setCellValue(worksList.get(i).getSchool());break;
                    case 3: cell.setCellValue(worksList.get(i).getTeachers());break;
                    case 4: cell.setCellValue(worksList.get(i).getStudents());break;
                    case 5: cell.setCellValue(worksList.get(i).getFinalScore());break;
//                    case 6: cell.setCellValue(null);break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 30*256); //30 characters wide
        sheet.setColumnWidth(2, 15*256); //30 characters wide
        sheet.setColumnWidth(3, 15*256); //30 characters wide
        sheet.setColumnWidth(4, 15*256); //30 characters wide
        sheet.setColumnWidth(5, 10*256);
        sheet.setColumnWidth(6, 6*256);
        // Write the output to a file
        String file = excelName+".xls";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }


    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setBold(true);
        monthFont.setColor(IndexedColors.BLACK.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", cellStyle);

        return styles;
    }

    private int CreateRelativeScore(Workbook wb, Map<String, CellStyle> styles,
                                    String[] headers,String model){
        //所有作品的相对分
        List<RelativeScore>relativeScoreList = relativeScoreRepository.findByModel(model);
        //按照平均分排序
        Collections.sort(relativeScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof RelativeScore && o2 instanceof RelativeScore){
                    RelativeScore e1 = (RelativeScore) o1;
                    RelativeScore e2 = (RelativeScore) o2;
                    return compareTwoDouble(e1.getAverage(),e2.getAverage());
                }
                throw new ClassCastException("不能转换为Works类型");
            }
        });
        String excelName = "2018泛珠赛全国总决赛终评相对分统计表("+model+")";

        Sheet sheet = wb.createSheet(excelName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(excelName);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$P$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < headers.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < relativeScoreList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);
                switch (j){
                    case 0: cell.setCellValue(relativeScoreList.get(i).getProId());break;
                    case 1: cell.setCellValue(relativeScoreList.get(i).getProName());break;
                    case 2: cell.setCellValue(relativeScoreList.get(i).getpScore1());break;
                    case 3: cell.setCellValue(relativeScoreList.get(i).getpScore2());break;
                    case 4: cell.setCellValue(relativeScoreList.get(i).getpScore3());break;
                    case 5: cell.setCellValue(relativeScoreList.get(i).getpScore4());break;
                    case 6: cell.setCellValue(relativeScoreList.get(i).getpScore5());break;
                    case 7: cell.setCellValue(relativeScoreList.get(i).getpScore6());break;
                    case 8: cell.setCellValue(relativeScoreList.get(i).getpScore7());break;
                    case 9: cell.setCellValue(relativeScoreList.get(i).getpScore8());break;
                    case 10: cell.setCellValue(relativeScoreList.get(i).getpScore9());break;
                    case 11: cell.setCellValue(relativeScoreList.get(i).getpScore10());break;
                    case 12: cell.setCellValue(relativeScoreList.get(i).getpScore11());break;

                    case 13: cell.setCellValue(relativeScoreList.get(i).getMaxScore());break;
                    case 14: cell.setCellValue(relativeScoreList.get(i).getMinScore());break;
                    case 15: cell.setCellValue(relativeScoreList.get(i).getAverage());break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 30*256); //30 characters wide

        for (int i = 2; i < 16; i++) {
            sheet.setColumnWidth(i, 6*256); //30 characters wide
        }
        return  0;
    }

    //创新分
    private int CreateInnovationScore(Workbook wb, Map<String, CellStyle> styles,
                                    String[] headers,String model){
        //所有作品的创新分
        List<InnovationScore>innovationScoreList = scoreService.calculateInnovationScore(model);

        String excelName = "2018泛珠赛全国总决赛终评创新分统计表("+model+")";

        Sheet sheet = wb.createSheet(excelName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(excelName);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$P$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < headers.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < innovationScoreList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);
                switch (j){
                    case 0: cell.setCellValue(innovationScoreList.get(i).getProId());break;
                    case 1: cell.setCellValue(innovationScoreList.get(i).getProName());break;
                    case 2: cell.setCellValue(innovationScoreList.get(i).getpScore1());break;
                    case 3: cell.setCellValue(innovationScoreList.get(i).getpScore2());break;
                    case 4: cell.setCellValue(innovationScoreList.get(i).getpScore3());break;
                    case 5: cell.setCellValue(innovationScoreList.get(i).getpScore4());break;
                    case 6: cell.setCellValue(innovationScoreList.get(i).getpScore5());break;
                    case 7: cell.setCellValue(innovationScoreList.get(i).getpScore6());break;
                    case 8: cell.setCellValue(innovationScoreList.get(i).getpScore7());break;
                    case 9: cell.setCellValue(innovationScoreList.get(i).getpScore8());break;
                    case 10: cell.setCellValue(innovationScoreList.get(i).getpScore9());break;
                    case 11: cell.setCellValue(innovationScoreList.get(i).getpScore10());break;
                    case 12: cell.setCellValue(innovationScoreList.get(i).getpScore11());break;

                    case 13: cell.setCellValue(innovationScoreList.get(i).getMaxScore());break;
                    case 14: cell.setCellValue(innovationScoreList.get(i).getMinScore());break;
                    case 15: cell.setCellValue(innovationScoreList.get(i).getAverage());break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 30*256); //30 characters wide

        for (int i = 2; i < 16; i++) {
            sheet.setColumnWidth(i, 8*256); //30 characters wide
        }
        return  0;
    }

    //实用分
    private int CreateUsefulScore(Workbook wb, Map<String, CellStyle> styles,
                                    String[] headers,String model){
        //所有作品的创新分
        List<InnovationScore>innovationScoreList = scoreService.calculateUsefulScore(model);

        String excelName = "2018泛珠赛全国总决赛终评实用分统计表("+model+")";

        Sheet sheet = wb.createSheet(excelName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(excelName);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$P$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < headers.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < innovationScoreList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);
                switch (j){
                    case 0: cell.setCellValue(innovationScoreList.get(i).getProId());break;
                    case 1: cell.setCellValue(innovationScoreList.get(i).getProName());break;
                    case 2: cell.setCellValue(innovationScoreList.get(i).getpScore1());break;
                    case 3: cell.setCellValue(innovationScoreList.get(i).getpScore2());break;
                    case 4: cell.setCellValue(innovationScoreList.get(i).getpScore3());break;
                    case 5: cell.setCellValue(innovationScoreList.get(i).getpScore4());break;
                    case 6: cell.setCellValue(innovationScoreList.get(i).getpScore5());break;
                    case 7: cell.setCellValue(innovationScoreList.get(i).getpScore6());break;
                    case 8: cell.setCellValue(innovationScoreList.get(i).getpScore7());break;
                    case 9: cell.setCellValue(innovationScoreList.get(i).getpScore8());break;
                    case 10: cell.setCellValue(innovationScoreList.get(i).getpScore9());break;
                    case 11: cell.setCellValue(innovationScoreList.get(i).getpScore10());break;
                    case 12: cell.setCellValue(innovationScoreList.get(i).getpScore11());break;

                    case 13: cell.setCellValue(innovationScoreList.get(i).getMaxScore());break;
                    case 14: cell.setCellValue(innovationScoreList.get(i).getMinScore());break;
                    case 15: cell.setCellValue(innovationScoreList.get(i).getAverage());break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 30*256); //30 characters wide

        for (int i = 2; i < 16; i++) {
            sheet.setColumnWidth(i, 8*256); //30 characters wide
        }
        return  0;
    }
    private int compareTwoDouble(double score1,double score2){
        int i=0;
        if(score1<score2)i=1;
        else if(score1>score2)i=-1;
        return i;
    }


    //根据给定的评委的所有作品评分数据，创建评委打分表
    private int CreatePingweiScore(Workbook wb, Map<String, CellStyle> styles,
                                  String[] headers,String pid, String model){
        //所有作品的分
        List<PingweiScore> pingweiScoreList =
                pingweiScoreService.selectByPidAndModel(pid,model);
        String titleName = "2018泛珠赛全国总决赛终评评委打分审核表("+model+")";
        String pingweiName = "评委"+pid;

        Sheet sheet = wb.createSheet(pingweiName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(titleName);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$I$1"));

        //Second row
        Row secondRow = sheet.createRow(1);
        secondRow.setHeightInPoints(40);
        Cell secondCell = secondRow.createCell(0);
        secondCell.setCellValue("评委编号："+pid+"          评委签名：");
        secondCell.setCellStyle(styles.get("header"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$I$2"));

//        Cell thirdCell = secondRow.createCell(1);
//        thirdCell.setCellValue("评委签名：");
//        thirdCell.setCellStyle(styles.get("header"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("$E$2:$I$2"));


        //header row
        Row headerRow = sheet.createRow(2);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < headers.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 3;
        for (int i = 0; i < pingweiScoreList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);
                switch (j){
                    case 0: cell.setCellValue(pingweiScoreList.get(i).getProId());break;
                    case 1: cell.setCellValue(pingweiScoreList.get(i).getProName());break;
                    case 2: cell.setCellValue(pingweiScoreList.get(i).getOption1());break;
                    case 3: cell.setCellValue(pingweiScoreList.get(i).getOption2());break;
                    case 4: cell.setCellValue(pingweiScoreList.get(i).getOption3());break;
                    case 5: cell.setCellValue(pingweiScoreList.get(i).getOption4());break;
                    case 6: cell.setCellValue(pingweiScoreList.get(i).getOption5());break;
                    case 7: cell.setCellValue(pingweiScoreList.get(i).getOption6());break;
                    case 8: cell.setCellValue(pingweiScoreList.get(i).getTotalScore());break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 10*256); //30 characters wide
        sheet.setColumnWidth(1, 30*256); //30 characters wide

        for (int i = 2; i < 9; i++) {
            sheet.setColumnWidth(i, 10*256); //30 characters wide
        }
        return  0;
    }
}
