package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {
    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

    //解析sqlConfigure.xml
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        List<Element> elements = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element: elements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        ComboPooledDataSource combopooledDataSource = new ComboPooledDataSource();
        combopooledDataSource.setDriverClass(properties.getProperty("jdbcClass"));
        combopooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        combopooledDataSource.setUser(properties.getProperty("username"));
        combopooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(combopooledDataSource);

        List<Element> mapper = rootElement.selectNodes("//mapper");
        for (Element element : mapper) {
            String resource = element.attributeValue("resource");
            InputStream mapperStream = Resources.getResourceAsStream(resource);
            XMLMapperConfig xmlMapperConfig = new XMLMapperConfig(configuration);
            xmlMapperConfig.parse(mapperStream);
        }



        return configuration;
    }
}
