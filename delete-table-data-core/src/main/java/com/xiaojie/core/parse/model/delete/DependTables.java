package com.xiaojie.core.parse.model.delete;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * 被依赖表，作为查询数据条件
 * @author wangye
 * @classname DependTable
 * @date 2020/12/14 10:07
 **/
@ToString
public class DependTables implements Serializable {

    @XmlElement(name = "depend-table")
    private List<DependTable> dependTableList;

    @XmlTransient
    public List<DependTable> getDependTableList() {
        return dependTableList;
    }

    public void setDependTableList(List<DependTable> dependTableList) {
        this.dependTableList = dependTableList;
    }
}
