package hpscore.service.impl;

import hpscore.domain.BusinessException;
import hpscore.domain.ReturnCode;
import hpscore.domain.Works;
import hpscore.service.ResolveExcelService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service("resolveExcelServiceImpl")
public class ResolveExcelServiceImpl implements ResolveExcelService {
    //打印日志
    private  static final Logger logger = LoggerFactory.getLogger(ResolveExcelServiceImpl.class);

    //注册url
    private static final String SUFFIX_2003=".xls";
    private static final String SUFFIX_2007=".xlsx";

    @Override
    public List<Works> resolveExcel(MultipartFile file) throws BusinessException {
        List<Works> list=new ArrayList<Works>();
        if(file==null){
            throw new BusinessException(ReturnCode.CODE_FAIL,"文件不存在！");
        }
        //获取文件名
        String originalFilename=file.getOriginalFilename();
        Workbook workbook=null;
        //判断格式
        try{
            if(originalFilename.endsWith(SUFFIX_2003)){
                workbook=new HSSFWorkbook(file.getInputStream());
            }else if(originalFilename.endsWith(SUFFIX_2007)){  //不知道为什么这种格式会抛异常
                workbook=new XSSFWorkbook(file.getInputStream());
            }
        }catch (Exception e){
            logger.info(originalFilename);
            e.printStackTrace();
            throw new BusinessException(ReturnCode.CODE_FAIL,"格式错误！");
        }
        if(null==workbook){
            logger.info(originalFilename);
            throw new BusinessException(ReturnCode.CODE_FAIL,"格式错误！");
        }else{
            //获取所有工作表的数量
            int numOfSheet=workbook.getNumberOfSheets();
            //遍历这些表
            for(int i=0;i<numOfSheet;i++){
                //获取一个sheet
                Sheet sheet=workbook.getSheetAt(i);
                int lastRowNum=sheet.getLastRowNum();
                //从第三行开始，第1行一般是标题，第二行是表头
                for(int j=2;j<=lastRowNum;j++){
                    Row row=sheet.getRow(j);
                    Works works=new Works();
                    //获取序号单元格
                    if(row.getCell(0)!=null){
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        //获取单元格内容（将内容当做字符串处理）
                        String code=row.getCell(0).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^\\d{1,2}$",code);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"序号错误！");
                        }
                        works.setCode(code);
                    }
                    //编号
                    if(row.getCell(1)!=null){
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        String bianHao=row.getCell(1).getStringCellValue();
                        //校验编号长度
                        boolean matche=Pattern.matches("^\\d{6}$",bianHao);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"序号错误！");
                        }
                        works.setBianHao(bianHao);
                    }
                    //名称
                    if(row.getCell(2)!=null){
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        String name=row.getCell(2).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^[\\u4E00-\\u9FA5\\w\\-\\——]{1,}$",name);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"作品名称错误！");
                        }
                        works.setName(name);
                    }
                    //分赛区
                    if(row.getCell(3)!=null){
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        String district=row.getCell(3).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^[\\u4E00-\\u9FA5]{1,}$",district);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"分赛区名称错误！");
                        }
                        works.setPartName(district);
                    }
                    //学校
                    if(row.getCell(4)!=null){
                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        String school=row.getCell(4).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^[\\u4E00-\\u9FA5]{1,}$",school);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"学校名称错误！");
                        }
                        works.setSchool(school);
                    }
                    //指导老师
                    if(row.getCell(5)!=null){
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        String teachers=row.getCell(5).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^[\\u4E00-\\u9FA5\\s,，、]{1,}$",teachers);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"老师姓名格式错误！");
                        }
                        works.setTeachers(teachers);
                    }
                    //学生
                    if(row.getCell(6)!=null){
                        row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                        String students=row.getCell(6).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^[\\u4E00-\\u9FA5\\s,，、]{1,}$",students);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"学生姓名格式错误！");
                        }
                        works.setStudents(students);
                    }
                    //组别
                    if(row.getCell(7)!=null){
                        row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                        String model=row.getCell(7).getStringCellValue();
                        //正则对比
                        boolean matche=Pattern.matches("^[\\u4E00-\\u9FA5]{1,}$",model);
                        if(!matche){
                            throw new BusinessException(ReturnCode.CODE_FAIL,"组别格式错误！");
                        }
                        works.setModel(model);
                    }
                    list.add(works);
                }
            }
        }
        return list;
    }
}