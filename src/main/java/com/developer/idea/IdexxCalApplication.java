package com.developer.idea;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.developer.idea.util.Utility;

@SpringBootApplication
public class IdexxCalApplication {

	public static void main(String[] args) throws SAXException, IOException {
		SpringApplication.run(IdexxCalApplication.class, args);
		
		File file = ResourceUtils.getFile("classpath:AE.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(file); 
			doc.getDocumentElement().normalize();
		//	System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		//	System.out.println(doc.getElementsByTagName("SampleFilter").item(0).getTextContent());
			
			List<Integer> list=Utility.getTiterList();
		//	System.out.println(list);
			int muNumber=4804;
			int distance = Math.abs(list.get(0) - muNumber);
			int idx = 0;
			for(int c = 1; c < list.size(); c++){
				  int cdistance = Math.abs(list.get(c) - muNumber);
				  if(cdistance < distance){
				        idx = c;
				        distance = cdistance;
				    }
			}
			
			int theNumber = idx;
			System.out.println("Group Number is "+theNumber);
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}

}
