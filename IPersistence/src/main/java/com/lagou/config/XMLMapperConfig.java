package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class XMLMapperConfig {
    private Configuration configuration;

    public XMLMapperConfig(Configuration configuration){
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> elements = rootElement.selectNodes("//select");
        HashMap<String, MappedStatement> map = new HashMap<String, MappedStatement>();
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            String key = namespace + "." + id;
            map.put(key, mappedStatement);
        }

        List<Element> insertElements = rootElement.selectNodes("//insert");
        for (Element insertElement : insertElements) {
            String id = insertElement.attributeValue("id");
            String parameterType = insertElement.attributeValue("parameterType");
            String sql = insertElement.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            String key = namespace + "." + id;
            map.put(key, mappedStatement);

        }

        List<Element> deleteElements = rootElement.selectNodes("//delete");
        for (Element updateElement : deleteElements) {
            String id = updateElement.attributeValue("id");
            String parameterType = updateElement.attributeValue("parameterType");
            String sql = updateElement.getTextTrim();
            String key = namespace + "." + id;
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            map.put(key, mappedStatement);
        }

        List<Element> updateElements = rootElement.selectNodes("update");
        for (Element updateElement : updateElements) {
            String id = updateElement.attributeValue("id");
            String parameterType = updateElement.attributeValue("parameterType");
            String sql = updateElement.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            String key = namespace + "." + id;
            map.put(key, mappedStatement);
        }
        configuration.setMappedStatementMap(map);
    }
}
