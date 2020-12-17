package com.xiaojie.core.parse.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author wangye
 * @classname DeleteExtensionClass
 * @date 2020/12/17 14:20
 **/
public class DeleteExtensionClass implements Serializable {

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
