package hpscore.service.impl;


import hpscore.domain.*;
import hpscore.repository.UserRepository;
import hpscore.service.*;
import hpscore.tools.ScoreUtil;
import hpscore.tools.StringUtil;
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
    private WorksService worksService;
    //原始打分审核表
    @Override
    public String reviewExcel(String model) {
        List<String> pingweiList = pingweiService.selectAllCodeByModel(model);
        String[] headers =null;
        if(model.equals("本科组")){
           String[] header1 = {
                   "序号","作品编号", "作品名称",
                   "选题\n(10分)","科学性\n(15分)","创新性\n(20分)",
                   "难易度\n(20分)","实用价值\n(20分)","答辩效果\n(15分)"
                   ,"总分"};
           headers = header1;
        }
        else{
            String[] header2 = {
                    "序号", "作品编号", "作品名称",
                    "选题\n(10分)","科学性\n(15分)","创新性\n(10分)",
                    "难易度\n(25分)","实用价值\n(25分)","答辩效果\n(15分)"
                    ,"总分"};
            headers = header2;
        }

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
    //根据给定的评委的所有作品评分数据，创建评委打分审核表
    private int CreatePingweiScore(Workbook wb, Map<String, CellStyle> styles,
                                   String[] headers,String pid, String model){
        //所有作品的分
        List<PingweiScore> pingweiScoreList =
                pingweiScoreService.selectByPidAndModel(pid,model);

        //按照序号排序
        Collections.sort(pingweiScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof PingweiScore && o2 instanceof PingweiScore){
                    PingweiScore e1 = (PingweiScore) o1;
                    PingweiScore e2 = (PingweiScore) o2;
                    return StringUtil.comparePidOrProId(e1.getProId(),e2.getProId());
                }
                throw new ClassCastException("不能转换为PingweiScore类型");
            }
        });

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
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$J$1"));

        //Second row
        Row secondRow = sheet.createRow(1);
        secondRow.setHeightInPoints(40);
        Cell secondCell = secondRow.createCell(0);
        secondCell.setCellValue("评委编号："+pid+"          评委签名：");
        secondCell.setCellStyle(styles.get("header"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$J$2"));

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
                    case 1: cell.setCellValue(pingweiScoreList.get(i).getBianHao());break;

                    case 2: cell.setCellValue(pingweiScoreList.get(i).getProName());break;
                    case 3: cell.setCellValue(pingweiScoreList.get(i).getOption1());break;
                    case 4: cell.setCellValue(pingweiScoreList.get(i).getOption2());break;
                    case 5: cell.setCellValue(pingweiScoreList.get(i).getOption3());break;
                    case 6: cell.setCellValue(pingweiScoreList.get(i).getOption4());break;
                    case 7: cell.setCellValue(pingweiScoreList.get(i).getOption5());break;
                    case 8: cell.setCellValue(pingweiScoreList.get(i).getOption6());break;
                    case 9: cell.setCellValue(pingweiScoreList.get(i).getTotalScore());break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 10*256); //30 characters wide
        sheet.setColumnWidth(1, 10*256); //30 characters wide
        sheet.setColumnWidth(2, 30*256); //30 characters wide
        for (int i = 3; i < headers.length; i++) {
            sheet.setColumnWidth(i, 9*256); //30 characters wide
        }
        return  0;
    }

    //打分转换表，增加相对分列
    @Override
    public String reviewTransferExcel(String model) {
        List<String> pingweiList = pingweiService.selectAllCodeByModel(model);
        String[] headers =null;
        if(model.equals("本科组")){
            String[] header1 = {
                    "序号","作品编号", "作品名称",
                    "选题\n(10分)","科学性\n(15分)","创新性\n(20分)",
                    "难易度\n(20分)","实用价值\n(20分)","答辩效果\n(15分)"
                    ,"总分\n(原分)","相对分"};
            headers = header1;
        }
        else{
            String[] header2 = {
                    "序号", "作品编号", "作品名称",
                    "选题\n(10分)","科学性\n(15分)","创新性\n(10分)",
                    "难易度\n(25分)","实用价值\n(25分)","答辩效果\n(15分)"
                    ,"总分\n(原分)","相对分"};
            headers = header2;
        }

        String excelName = "2018泛珠赛全国总决赛终评评委打分转换表("+model+")";
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        for (int i = 0; i < pingweiList.size(); i++) {
            CreateTransferScore(wb, styles, headers,pingweiList.get(i), model);
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
    //根据给定的评委的所有作品评分数据，
    private int CreateTransferScore(Workbook wb, Map<String, CellStyle> styles,
                                   String[] headers,String pid, String model){
        //所有作品的分
        List<PingweiScore> pingweiScoreList =
                pingweiScoreService.selectByPidAndModel(pid,model);

        //按照序号排序
        Collections.sort(pingweiScoreList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof PingweiScore && o2 instanceof PingweiScore){
                    PingweiScore e1 = (PingweiScore) o1;
                    PingweiScore e2 = (PingweiScore) o2;
                    return StringUtil.comparePidOrProId(e1.getProId(),e2.getProId());
                }
                throw new ClassCastException("不能转换为PingweiScore类型");
            }
        });

        String titleName = "2018泛珠赛全国总决赛终评评委打分转换表("+model+")";
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
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$J$1"));

        //Second row
        Row secondRow = sheet.createRow(1);
        secondRow.setHeightInPoints(40);
        Cell secondCell = secondRow.createCell(0);
        secondCell.setCellValue("评委编号:"+pid+"      评委签名:                 数据处理员: ");
        secondCell.setCellStyle(styles.get("header"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$J$2"));

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
                    case 1: cell.setCellValue(pingweiScoreList.get(i).getBianHao());break;

                    case 2: cell.setCellValue(pingweiScoreList.get(i).getProName());break;
                    case 3: cell.setCellValue(pingweiScoreList.get(i).getOption1());break;
                    case 4: cell.setCellValue(pingweiScoreList.get(i).getOption2());break;
                    case 5: cell.setCellValue(pingweiScoreList.get(i).getOption3());break;
                    case 6: cell.setCellValue(pingweiScoreList.get(i).getOption4());break;
                    case 7: cell.setCellValue(pingweiScoreList.get(i).getOption5());break;
                    case 8: cell.setCellValue(pingweiScoreList.get(i).getOption6());break;
                    case 9: cell.setCellValue(pingweiScoreList.get(i).getTotalScore());break;
                    case 10: cell.setCellValue(pingweiScoreList.get(i).getFinalScore());break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 10*256); //30 characters wide
        sheet.setColumnWidth(1, 30*256); //30 characters wide

        for (int i = 2; i < 11; i++) {
            sheet.setColumnWidth(i, 10*256); //30 characters wide
        }
        return  0;
    }

    //相对分、创新分、实用分  的平均分
    @Override
    public String relativeScoreExcel(String model) {
        //评委pid已经按照序号大小排序
        List<String> pingweiStrList = pingweiService.selectAllCodeByModel(model);
        String[] headers =new String[pingweiStrList.size()+4];
        int i = 0;
        headers[i++]="序号";
        headers[i++]="作品编号";
        headers[i++]="作品名称";
        for (String pid: pingweiStrList) {
            headers[i++]="评委"+pid;
        }
        headers[i++]="平均分";

        String excelName = "2018泛珠赛全国总决赛终评平均分统计表("+model+")";
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        //相对分
        CreateRelativeScore(wb, styles,headers,model,pingweiStrList);
        //创新分
        CreateInnovationScore(wb, styles,headers,model,pingweiStrList);
        //实用分
        CreateUsefulScore(wb, styles,headers,model,pingweiStrList);
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
                                    String[] headers,String model,List<String> pingweiStrList){
        //所有作品的相对分
        List<RelativeScore>relativeScoreList =
                scoreService.calculteRelativeScoreAverageAndMaxAndMin(model);
        //按照平均分排序
        ScoreUtil.sortRelativeScore(relativeScoreList);
        String excelName = "2018泛珠赛总决赛终评相对平均分统计表("+model+")";
        String sheetName = "相对平均分统计表("+model+")";
        Sheet sheet = wb.createSheet(sheetName);
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
        String str = StringUtil.getNextCell( 'A', headers.length-1);
        //
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$"+str+"$1"));

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
                if(j==0){
                    cell.setCellValue(relativeScoreList.get(i).getProId());
                }
                else if(j==1){
                    cell.setCellValue(relativeScoreList.get(i).getBianHao());
                }else if(j==2){
                    cell.setCellValue(relativeScoreList.get(i).getProName());
                }
                else if(j>2&&j<=(pingweiStrList.size()+2)){
                    cell.setCellValue(relativeScoreList.get(i).getpScores()[j-3]);
                }
                //最后一列
                else if(j==(headers.length-1))cell.setCellValue(relativeScoreList.get(i).getAverage());
            }
        }
        sheet.setColumnWidth(0, 6*256); //6 characters wide
        sheet.setColumnWidth(1, 10*256); //10 characters wide
        sheet.setColumnWidth(2, 30*256); //30 characters wide

        for (int i = 3; i < headers.length; i++) {
            sheet.setColumnWidth(i, 10*256); //30 characters wide
        }
        return  0;
    }

    //创新分
    private int CreateInnovationScore(Workbook wb, Map<String, CellStyle> styles,
                                    String[] headers,String model,List<String> pingweiStrList){
        //所有作品的创新分
        List<InnovationScore>innovationScoreList = scoreService.calculateInnovationScore(model);
        ScoreUtil.sortInnovationScore(innovationScoreList);
        String sheetName = "创新分统计表("+model+")";
        String titleName = "2018泛珠赛总决赛终评创新分统计表("+model+")";
        Sheet sheet = wb.createSheet(sheetName);
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
        String str = StringUtil.getNextCell( 'A', headers.length-1);
        //
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$"+str+"$1"));

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
                if(j==0){
                    cell.setCellValue(innovationScoreList.get(i).getProId());
                }
                else if(j==1){
                    cell.setCellValue(innovationScoreList.get(i).getBianHao());
                }else if(j==2){
                    cell.setCellValue(innovationScoreList.get(i).getProName());
                }
                else if(j>2&&j<=(pingweiStrList.size()+2)){
                    cell.setCellValue(innovationScoreList.get(i).getpScores()[j-3]);
                }
                else if(j==(headers.length-1))cell.setCellValue(innovationScoreList.get(i).getAverage());
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 10*256); //30 characters wide
        sheet.setColumnWidth(2, 30*256); //30 characters wide

        for (int i = 3; i < headers.length; i++) {
            sheet.setColumnWidth(i, 10*256); //30 characters wide
        }
        return  0;
    }

    //实用分
    private int CreateUsefulScore(Workbook wb, Map<String, CellStyle> styles,
                                    String[] headers,String model,List<String> pingweiStrList){
        //所有作品的创新分
        List<InnovationScore>innovationScoreList = scoreService.calculateUsefulScore(model);
        ScoreUtil.sortInnovationScore(innovationScoreList);
        String sheetName = "实用分统计表("+model+")";
        String titleName = "2018泛珠赛总决赛终评实用分统计表("+model+")";

        Sheet sheet = wb.createSheet(sheetName);
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
        String str = StringUtil.getNextCell( 'A', headers.length-1);
        //
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$"+str+"$1"));

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
                if(j==0){
                    cell.setCellValue(innovationScoreList.get(i).getProId());
                }
                else if(j==1){
                    cell.setCellValue(innovationScoreList.get(i).getBianHao());
                }else if(j==2){
                    cell.setCellValue(innovationScoreList.get(i).getProName());
                }
                else if(j>2&&j<=(pingweiStrList.size()+2)){
                    cell.setCellValue(innovationScoreList.get(i).getpScores()[j-3]);
                }
                else if(j==(headers.length-1))cell.setCellValue(innovationScoreList.get(i).getAverage());
            }
        }
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 10*256); //30 characters wide
        sheet.setColumnWidth(2, 30*256); //30 characters wide
        for (int i = 3; i < headers.length; i++) {
            sheet.setColumnWidth(i, 10*256); //30 characters wide
        }
        return  0;
    }

    //作品获奖表
    @Override
    public String finalScoreExcel(String model) {
        String excelName = "2018泛珠赛总决赛作品获奖表("+model+")";

        String[] headers = {
                "序号","作品编号", "作品名称",
                "分赛区名称","学校名称",
                "指导老师", "研发学生","分数","排名", "获奖等级"};
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);

        List<Works> worksList = worksService.getSumUpAward(model);
        CreateSumUpExcel(wb, styles,headers,model,"综合奖", worksList);

        worksList = worksService.getInnovationAward(model);
        CreateSumUpExcel(wb, styles,headers,model,"创新奖", worksList);

        worksList = worksService.getUsefulAward(model);
        CreateSumUpExcel(wb, styles,headers,model,"实用奖", worksList);
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

    private int CreateSumUpExcel(Workbook wb, Map<String, CellStyle> styles,
                                 String[] headers,String model,String typeName,
                                 List<Works> worksList){
        //按照序号排序
        Collections.sort(worksList,new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Works && o2 instanceof Works){
                    Works e1 = (Works) o1;
                    Works e2 = (Works) o2;
                    return StringUtil.compareTwoDouble(e1.getFinalScore(),e2.getFinalScore());
                }
                throw new ClassCastException("不能转换为Works类型");
            }
        });

        Sheet sheet = wb.createSheet(model+typeName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("2018泛珠赛总决赛作品获奖表("+model+typeName+")");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$J$1"));

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
        for (int i = 0; i < worksList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);
                switch (j){
                    case 0: cell.setCellValue(worksList.get(i).getCode());break;
                    case 1: cell.setCellValue(worksList.get(i).getBianHao());break;
                    case 2: cell.setCellValue(worksList.get(i).getName());break;
                    case 3: cell.setCellValue(worksList.get(i).getPartName());break;
                    case 4: cell.setCellValue(worksList.get(i).getSchool());break;
                    case 5: cell.setCellValue(worksList.get(i).getTeachers());break;
                    case 6: cell.setCellValue(worksList.get(i).getStudents());break;
                    case 7: cell.setCellValue(worksList.get(i).getFinalScore());break;
                    case 8: cell.setCellValue(worksList.get(i).getRanking());break;
//                    case 6: cell.setCellValue(null);break;
                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6*256); //30 characters wide
        sheet.setColumnWidth(1, 10*256); //30 characters wide
        for (int i = 2; i <7; i++) {
            sheet.setColumnWidth(i, 30*256); //30 characters wide
        }
        for (int i = 7; i <headers.length; i++) {
            sheet.setColumnWidth(i, 8*256); //30 characters wide
        }
        return  0;
    }
    ////评分统计表，各个子项的平均分
    @Override
    public String scoringSumUpExcel(String model) {
        String[] headers = {
                "序号","作品编号", "作品名称",
                "选题\n(平均分)","科学性\n(平均分)","创新性\n(平均分)",
                "难易度\n(平均分)","实用价值\n(平均分)","答辩效果\n(平均分)"
                ,"相对分\n(平均分)","相对分排序","创新分排序","实用分排序"};

        String excelName = "2018泛珠赛总决赛终评评委打分统计表("+model+")";
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        CreateSumUpAverage(wb, styles, headers, model);
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

    private int CreateSumUpAverage(Workbook wb, Map<String, CellStyle> styles,
                                   String[] headers, String model){
        //所有作品的相对分
        List<RelativeScore>relativeScoreList =
                scoreService.calculteRelativeScoreAverageAndMaxAndMin(model);
        //按照总分的平均分排序
        ScoreUtil.sortRelativeScore(relativeScoreList);
        String excelName = "2018泛珠赛总决赛终评评委打分统计表("+model+")";

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
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:M$1"));

        //second row
        Row secondRow = sheet.createRow(1);
        secondRow.setHeightInPoints(40);
        Cell secondCell = secondRow.createCell(0);
        secondCell.setCellValue("数据处理员：");
        secondCell.setCellStyle(styles.get("header"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$M$2"));


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
        for (int i = 0; i < relativeScoreList.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);
                if(j==0){
                    cell.setCellValue(relativeScoreList.get(i).getProId());
                }
                else if(j==1){
                    cell.setCellValue(relativeScoreList.get(i).getBianHao());
                }else if(j==2){
                    cell.setCellValue(relativeScoreList.get(i).getProName());
                }
                else if(j>2&&j<=8){
                    cell.setCellValue(relativeScoreList.get(i).getpAverage()[j-3]);
                }
                else if(j==9){
                    cell.setCellValue(relativeScoreList.get(i).getAverage());
                }else if(j==10){
                    cell.setCellValue(relativeScoreList.get(i).getRanking());
                }
            }
        }
        sheet.setColumnWidth(0, 6*256); //6 characters wide
        sheet.setColumnWidth(1, 10*256); //10 characters wide
        sheet.setColumnWidth(2, 30*256); //30 characters wide

        for (int i = 3; i < headers.length; i++) {
            sheet.setColumnWidth(i, 10*256); //30 characters wide
        }
        return  0;
    }
}
