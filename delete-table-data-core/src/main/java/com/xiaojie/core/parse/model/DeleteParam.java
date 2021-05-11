package com.xiaojie.core.parse.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author wangye
 * @classname DeleteParam
 * @date 2020/12/17 14:32
 **/
public class DeleteParam implements Serializable {

    private static final long serialVersionUID = -3611223481319920635L;

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
