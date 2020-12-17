package com.xiaojie.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * 被依赖表，作为查询数据条件
 * @author wangye
 * @classname DependTable
 * @date 2020/12/14 10:07
 **/
@ToString
public class DependTable implements Serializable {

    @XmlAttribute(name = "table-name")
    private String tableName;

    @XmlAttribute(name = "source-field-name")
    private String sourceFieldName;

    @XmlAttribute(name = "depend-field-name")
    private String dependFieldName;

    @XmlTransient
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlTransient
    public String getSourceFieldName() {
        return sourceFieldName;
    }

    public void setSourceFieldName(String sourceFieldName) {
        this.sourceFieldName = sourceFieldName;
    }

    @XmlTransient
    public String getDependFieldName() {
        return dependFieldName;
    }

    public void setDependFieldName(String dependFieldName) {
        this.dependFieldName = dependFieldName;
    }
}
