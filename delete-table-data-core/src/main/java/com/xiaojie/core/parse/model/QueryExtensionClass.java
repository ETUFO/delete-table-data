package com.xiaojie.core.parse.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author wangye
 * @classname QueryExtensionClass
 * @date 2020/12/17 14:37
 **/
public class QueryExtensionClass implements Serializable {

    private static final long serialVersionUID = -927993573678054472L;

    @XmlAttribute(name = "bean-name")
    private String beanName;

    @XmlTransient
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
