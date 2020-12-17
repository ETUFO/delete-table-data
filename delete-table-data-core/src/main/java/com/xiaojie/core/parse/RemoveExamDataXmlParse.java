package com.xiaojie.core.parse;

import cn.hutool.core.io.FileUtil;
import com.xiaojie.core.parse.model.delete.RemoveDataTables;
import jdk.nashorn.internal.ir.ThrowNode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.Optional;

/**
 * 解析xml
 * @author wangye
 * @classname XmlParse
 * @date 2020/9/16 13:54
 **/
@Slf4j
public class RemoveExamDataXmlParse {

    public static RemoveDataTables getRemoveDataTables(String fileName){
        Object unmarshal = null;
        try {
            fileName = fileName + ".xml";
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            File file = classPathResource.getFile();
            if (!file.exists()) {
                log.error(fileName + "文件不存在");
            }
            String content = FileUtil.readString(file, "UTF-8");
            JAXBContext context = JAXBContext.newInstance(RemoveDataTables.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshal = unmarshaller.unmarshal(new StringReader(content));
        } catch (Exception e) {
            log.error("解析" + fileName + "文件异常", e);
        }
        if (unmarshal == null) {
            log.error("获取" + fileName + "文件内容为空");
        }
        return  Optional.ofNullable(unmarshal).map(u -> (RemoveDataTables) u).orElse(null);
    }

}
