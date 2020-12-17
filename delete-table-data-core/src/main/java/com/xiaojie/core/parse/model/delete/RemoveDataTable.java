package com.xiaojie.core.parse.model.delete;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 删除的表
 * @author wangye
 * @classname RemoveDataTable
 * @date 2020/12/14 10:23
 **/
public class RemoveDataTable {

    @XmlAttribute(name = "table-name")
    private String tableName;

    @XmlAttribute(name = "field-name")
    private String fieldName;

    @XmlAttribute(name = "query-param-name")
    private String queryParamName;

    @XmlElement(name = "depend-table")
    private DependTable dependTable;


    @XmlTransient
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlTransient
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @XmlTransient
    public String getQueryParamName() {
        return queryParamName;
    }

    public void setQueryParamName(String queryParamName) {
        this.queryParamName = queryParamName;
    }

    @XmlTransient
    public DependTable getDependTable() {
        return dependTable;
    }

    public void setDependTable(DependTable dependTable) {
        this.dependTable = dependTable;
    }

}
