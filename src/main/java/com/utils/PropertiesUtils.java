package com.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	public static final String GLOBAL_PROPERTIES = "application.properties";
	private static Properties globalProperties = GetPropertiesByClassPath("application.properties");

	public static Properties GetPropertiesByFileSystem(String path) {
		Resource resource = new FileSystemResource(path);
		Properties roperties = null;
		try {
			roperties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return roperties;
	}

	public static Properties GetPropertiesByClassPath(String path) {
		Resource resource = new ClassPathResource(path);
		Properties roperties = null;
		try {
			roperties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return roperties;
	}

	public static String getGlobalProperties(String key) {
		String value = globalProperties.getProperty(key);

		if (value == null) {
			value = "";
		}
		return value;
	}
}
