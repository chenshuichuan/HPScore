package hpscore.domain;
/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/6/30
 * Time: 9:04
 */

import hpscore.tools.ScoreUtil;

/**
 *@ClassName: FileInfo
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/6/30 9:04
 **/
public class FileInfo {
    //文件名
    private String name;
    //文件大小(KB)
    private double size;
    //最后修改时间
    private String updateTime;
    //size(KB)
    public FileInfo(String name, double size, String updateTime) {

        this.name = name;
        this.size = ScoreUtil.DecimalDouble(size,2);
        this.updateTime = updateTime;
    }
    //size(B)
    public FileInfo(String name, long size, String updateTime) {

        this.name = name;
        double kb = ((double)size)/(double)1024;
        this.size = ScoreUtil.DecimalDouble(kb,2);
        this.updateTime = updateTime;
    }
    public FileInfo( ) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = ScoreUtil.DecimalDouble(size,2);
    }
    public void setSize(long size) {
        double kb = ((double)size)/(double)1024;
        this.size = ScoreUtil.DecimalDouble(size,2);
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
