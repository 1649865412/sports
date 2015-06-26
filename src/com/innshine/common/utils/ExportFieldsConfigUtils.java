package com.innshine.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.utils.Exceptions;

public class ExportFieldsConfigUtils
{
	private final static Logger LOGGER = LoggerFactory.getLogger(ExportFieldsConfigUtils.class);
	/**
	 * 获取所有节点元素
	 */
	private static final String FIELD = "//fields/*";
	
	private static final String CLASSPATH = "classpath:";
	
	/**
	 * 配置文件名称
	 */
	private static final String RESOURCE_FILE_NAME = "export-fields-config.xml";
	
	/**
	 * 配置 参数集合
	 */
	private static Map<String, Map<String, String>> attrEntities = null;
	
	/**
	 * 内部实例
	 */
	private static ExportFieldsConfigUtils resource;
	
	/**
	 * 是否初始化标识
	 */
	private static boolean isInit = false;
	
	private ExportFieldsConfigUtils()
	{
		
	}
	
	public synchronized static ExportFieldsConfigUtils getInstance()
	{
		if (null == resource)
		{
			resource = new ExportFieldsConfigUtils();
			
			try
			{
				attrEntities = initConfigFile();
				
				isInit = true;
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return resource;
	}
	
	private static Map<String, Map<String, String>> initConfigFile() throws ParserConfigurationException,
			FileNotFoundException, SAXException, IOException, XPathExpressionException, IllegalArgumentException,
			DOMException, IllegalAccessException
	{
		Document doc = getDocument();
		
		// 创建Xpath解析工厂
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xPath = xFactory.newXPath();
		
		// 获取所有元素
		NodeList nodes = (NodeList) xPath.evaluate(FIELD, doc, XPathConstants.NODESET);
		
		Map<String, Map<String, String>> idNodeMap = new HashMap<String, Map<String, String>>();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			
			if (null != node)
			{
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Node idNode = node.getAttributes().getNamedItem("id");
					NodeList childNodeList = node.getChildNodes();
					
					if (null != childNodeList)
					{
						Map<String, String> map = new HashMap<String, String>();
						for (int j = 0; j < childNodeList.getLength(); j++)
						{
							Node clildNode = childNodeList.item(j);
							
							if (clildNode.getNodeType() == Node.ELEMENT_NODE)
							{
								NamedNodeMap namedNodeMap = clildNode.getAttributes();
								if (null != namedNodeMap)
								{
									Node nameNode = namedNodeMap.getNamedItem("name");
									Node titleNode = namedNodeMap.getNamedItem("title");
									Node isExportNode = namedNodeMap.getNamedItem("isExport");
									if (null == isExportNode || BooleanUtils.toBoolean(isExportNode.getNodeValue()))
									{
										if (null != nameNode && null != titleNode)
										{
											if (StringUtils.isNotEmpty(nameNode.getNodeValue())
													&& StringUtils.isNotEmpty(titleNode.getNodeValue()))
											{
												map.put(nameNode.getNodeValue(), titleNode.getNodeValue());
											}
										}
									}
								}
							}
						}
						
						if (null != idNode && MapUtils.isNotEmpty(map))
						{
							idNodeMap.put(idNode.getNodeValue(), map);
						}
					}
				}
			}
		}
		
		return idNodeMap;
	}
	
	/**
	 * 根据配置的ID，获取对应字段列表，key:name value :title，没有则返回null
	 * 
	 * @param key
	 *        配置 的ID
	 * @return Map<String, String>
	 */
	public Map<String, String> getFieldsConfig(String key)
	{
		if (isInit)
		{
			return attrEntities.get(key);
		}
		
		return null;
	}
	
	private static Document getDocument() throws ParserConfigurationException, SAXException, IOException,
			FileNotFoundException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		Document doc = documentBuilder
				.parse(new FileInputStream(ResourceUtils.getFile(CLASSPATH + RESOURCE_FILE_NAME)));
		return doc;
	}
	
	public static void main(String[] args)
	{
		Map<String, String> map = ExportFieldsConfigUtils.getInstance().getFieldsConfig("product_info");
		System.out.println(map);
		
	}
}
