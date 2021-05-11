package com.xiaojie.core.parse.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangye
 * @classname DeleteParams
 * @date 2020/12/17 14:31
 **/
public class DeleteParams implements Serializable {

    private static final long serialVersionUID = -7233315051790577399L;

    @XmlElement(name = "delete-param")
    private List<DeleteParam> deleteParams;

    @XmlTransient
    public List<DeleteParam> getDeleteParams() {
        return deleteParams;
    }

    public void setDeleteParams(List<DeleteParam> deleteParams) {
        this.deleteParams = deleteParams;
    }
}
