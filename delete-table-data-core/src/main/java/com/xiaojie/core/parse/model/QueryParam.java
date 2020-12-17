package com.xiaojie.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author wangye
 * @classname QueryParam
 * @date 2020/12/17 14:03
 **/
@ToString
public class QueryParam implements Serializable {

    @XmlAttribute(name = "param-name")
    private String paramName;
    @XmlAttribute(name = "field-name")
    private String filedName;

    @XmlTransient
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @XmlTransient
    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }
}
