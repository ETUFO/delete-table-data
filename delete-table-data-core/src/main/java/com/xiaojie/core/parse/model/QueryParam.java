package com.xiaojie.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author wangye1
 * @classname QueryParam
 * @date 2020/12/17 14:03
 **/
@ToString
public class QueryParam implements Serializable {

    private static final long serialVersionUID = 4124569940631752502L;

    @XmlAttribute(name = "param-name")
    private String paramName;
    @XmlAttribute(name = "field-name")
    private String fieldName;

    @XmlTransient
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @XmlTransient
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
