package com.lagou.config;

import com.lagou.utils.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class ApplicationConfigXmlBuilder {

    public static String getBasePackage(InputStream inputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.selectNodes("//context:component-scan");
        Element element = elements.get(0);
//        Element element = rootElement.element("context:component-scan");
        String basePackage = element.attributeValue("base-package");
        return basePackage;
    }
}
