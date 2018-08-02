package hpscore.controller;

import hpscore.domain.BusinessException;
import hpscore.domain.Works;
import hpscore.repository.WorksRepository;
import hpscore.service.ResolveExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resolve")
public class ResolveExcelController {
    private final static Logger logger = LoggerFactory.getLogger(ResolveExcelController.class);

    @Resource(name="resolveExcelServiceImpl")
    private ResolveExcelService resolveExcelService;

    @Autowired
    private WorksRepository worksRepository;

    /**
     * @Author haien
     * @Description 文件上传
     * @Date 12:28 2018/7/26
     * @Param
     * @return
     **/
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadExcel(@RequestParam("file")MultipartFile file,@RequestParam("year")String year1,@RequestParam("cover")String cover) {
        int year = Integer.parseInt(year1);
        List<Works> works = worksRepository.findByYear(year);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Works> worksList = null;
        //作品表无数据/已确定要覆盖，开始写入数据库
        if (null == works || works.size() == 0 || cover.equals("yes")) {
            try {
                logger.info("---导入作品表，正在写入数据库---");

                //cover有值，证明访问过一次，因数据库有数据而返回；再回来，清空
                if (cover.equals("yes")) {
                    List<Works> wl=worksRepository.findByYear(year);
                    worksRepository.delete(wl);
                }
                //解析Excel
                worksList = resolveExcelService.resolveExcel(file);
                //写入数据库
                Iterator<Works> it = worksList.iterator();
                Works w=null;
                while(it.hasNext()) {
                    w=it.next();
                    w.setYear(year);
                    worksRepository.save(w);
                }
                map.put("result", 1);
                map.put("message", "导入数据成功！");
            } catch (BusinessException e) {
                e.printStackTrace();
                map.put("result", 0);
                map.put("message", e.getErrMsg());
                return map;
            }
        } else { //作品表已存在数据
            map.put("result", 0);
            map.put("message", "当年作品已存在，是否覆盖？");
        }
        return map;
    }
}
