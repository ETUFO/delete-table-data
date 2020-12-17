package com.xiaojie.core.parse.model.delete;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * 删除的表
 * @author wangye
 * @classname RemoveDataTable
 * @date 2020/12/14 10:23
 **/
public class RemoveDataTable {

    @XmlAttribute(name = "table-name")
    private String tableName;
    @XmlAttribute(name = "before-table")
    private String beforeTable;
    @XmlAttribute(name = "after-name")
    private String afterTable;
    @XmlAttribute(name = "delete-max-limit")
    private String deleteMaxLimit;

    @XmlElement(name = "query-depend-tables")
    private DependTables queryDependTables;

    @XmlElement(name = "delete-depend-tables")
    private DependTables deleteDependTables;

    @XmlElement(name = "query-params")
    private QueryParams queryParams;

    @XmlElement(name = "delete-extension-class")
    private DeleteExtensionClass deleteExtensionClass;

    @XmlElement(name = "query-extension-class")
    private QueryExtensionClass queryExtensionClass;

    @XmlElement(name = "delete-params")
    private DeleteParams deleteParams;


    @XmlTransient
    public String getTableName() {
        return tableName;
    }



    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlTransient
    public DependTables getQueryDependTables() {
        return queryDependTables;
    }

    public void setQueryDependTables(DependTables queryDependTables) {
        this.queryDependTables = queryDependTables;
    }

    @XmlTransient
    public DependTables getDeleteDependTables() {
        return deleteDependTables;
    }

    public void setDeleteDependTables(DependTables deleteDependTables) {
        this.deleteDependTables = deleteDependTables;
    }

    @XmlTransient
    public String getBeforeTable() {
        return beforeTable;
    }

    public void setBeforeTable(String beforeTable) {
        this.beforeTable = beforeTable;
    }
    @XmlTransient
    public String getAfterTable() {
        return afterTable;
    }

    public void setAfterTable(String afterTable) {
        this.afterTable = afterTable;
    }
    @XmlTransient
    public String getDeleteMaxLimit() {
        return deleteMaxLimit;
    }

    public void setDeleteMaxLimit(String deleteMaxLimit) {
        this.deleteMaxLimit = deleteMaxLimit;
    }
    @XmlTransient
    public QueryParams getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }
    @XmlTransient
    public DeleteExtensionClass getDeleteExtensionClass() {
        return deleteExtensionClass;
    }

    public void setDeleteExtensionClass(DeleteExtensionClass deleteExtensionClass) {
        this.deleteExtensionClass = deleteExtensionClass;
    }

    @XmlTransient
    public DeleteParams getDeleteParams() {
        return deleteParams;
    }

    public void setDeleteParams(DeleteParams deleteParams) {
        this.deleteParams = deleteParams;
    }
}
