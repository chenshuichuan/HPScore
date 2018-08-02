package hpscore.service;

import hpscore.domain.BusinessException;
import hpscore.domain.Works;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResolveExcelService {
    public List<Works> resolveExcel(MultipartFile file)throws BusinessException;
}
