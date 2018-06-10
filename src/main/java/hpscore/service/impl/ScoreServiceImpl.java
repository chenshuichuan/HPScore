package hpscore.service.impl;


import hpscore.domain.Score;
import hpscore.repository.ScoreRepository;
import hpscore.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/7.
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreRepository scoreRepository;
    @Override
    public int add(Score score) {
        Score score1 = scoreRepository.save(score);
        if (null != score1) return 1;
        return 0;
    }

    @Override
    public int update(Score score) {
       Score score1 = scoreRepository.findOne(score.getId());
       if (null!=score1){
           scoreRepository.save(score);
           return 1;
       }
       return 0;
    }

    @Override
    public int delete(Score score) {
        Score score1 = scoreRepository.findOne(score.getId());
        if (null!=score1){
            scoreRepository.delete(score);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Score> selectAll() {
        return scoreRepository.findAll();
    }

    //返回该editor编辑过的model类型的记录数据
    @Override
    public List<Score> selectByEditorAndModel(String editor, String model) {
        List<Score> scores1 = scoreRepository.findByEditor1AndModel(editor,model);
        List<Score> scores2 = scoreRepository.findByEditor2AndModel(editor,model);
        if(scores1!=null){
            if(scores2!=null)scores1.addAll(scores2);
        }
        return scores1;
    }

    @Override
    public List<Score> selectByModel(String model) {

        return scoreRepository.findByModel(model);
    }

    @Override
    public Score selectByPidAndProIdAndModel(String pid, String proId, String model) {
        return scoreRepository.findByPidAndProIdAndModel(pid,proId,model);
    }

}
