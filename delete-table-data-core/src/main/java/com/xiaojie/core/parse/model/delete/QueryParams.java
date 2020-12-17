package com.xiaojie.core.parse.model.delete;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangye
 * @classname QueryParam
 * @date 2020/12/17 14:03
 **/
@ToString
public class QueryParams implements Serializable {

   @XmlElement(name = "query-param")
   private List<QueryParam> paramList;

    @XmlTransient
    public List<QueryParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<QueryParam> paramList) {
        this.paramList = paramList;
    }
}
