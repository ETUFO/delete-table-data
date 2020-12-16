package com.xiaojie.core.parse.model;

import com.xiaojie.core.parse.model.RemoveDataTable;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * 删除考试相关数据表的配置文件对应的实体类
 *
 * @author wangye
 * @classname RemoveDataTables
 * @date 2020/12/14 10:05
 **/
@ToString
@XmlRootElement(name = "tables")
public class RemoveDataTables implements Serializable {

    @XmlElement(name = "table")
    private List<RemoveDataTable> removeDataTableList;

    @XmlTransient
    public List<RemoveDataTable> getRemoveDataTableList() {
        return removeDataTableList;
    }

    public void setRemoveDataTableList(List<RemoveDataTable> removeDataTableList) {
        this.removeDataTableList = removeDataTableList;
    }
}
