package com.pojo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Pojo_Properties {

	public static void main(String[] args) throws IOException {
		
		Properties properties=new Properties();
		
		FileInputStream stream=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Config.properties");
		
		properties.load(stream);
		
		Object object = properties.get("username");
		String name=(String) object;
		System.out.println(name);
	}
}
