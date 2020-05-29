package com.developer.idea.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.developer.idea.PlateModel.CsvModel;

public class Utility {

	public static String csvToJson(List<String> csv) {

		// remove empty lines
		// this will affect permanently the list.
		// be careful if you want to use this list after executing this method
		csv.removeIf(e -> e.trim().isEmpty());

		// csv is empty or have declared only columns
		if (csv.size() <= 1) {
			return "[]";
		}

		// get first line = columns names
		String[] columns = csv.get(0).split(",");

		// get all rows
		StringBuilder json = new StringBuilder("[\n");
		csv.subList(1, csv.size()) // substring without first row(columns)
				.stream().map(e -> e.split(",")).filter(e -> e.length == columns.length) // values size should match
																							// with columns size
				.forEach(row -> {

					json.append("\t{\n");

					for (int i = 0; i < columns.length; i++) {
						json.append("\t\t\"").append("A" + columns[i]).append("\" : \"").append(row[i]).append("\",\n"); // comma-1
					}

					// replace comma-1 with \n
					json.replace(json.lastIndexOf(","), json.length(), "\n");

					json.append("\t},"); // comma-2

				});

		// remove comma-2
		json.replace(json.lastIndexOf(","), json.length(), "");

		json.append("\n]");

		return json.toString();

	}

	public static List<CsvModel> csvMapper(String fileName) throws IOException {
		List<CsvModel> csvList = new ArrayList<CsvModel>();
		File filess = ResourceUtils.getFile("uploads/" + fileName);

		BufferedReader br = new BufferedReader(new FileReader(filess));
		String line;
		CsvModel csv = null;
		while ((line = br.readLine()) != null) {
			// use comma as separator
			String[] s = line.split(",");
			switch (s.length) {
			case 1:
				csv = new CsvModel(s[0], "", "", "", "", "", "", "", "", "", "", "", "");
				break;
			case 2:
				csv = new CsvModel(s[0], s[1], "", "", "", "", "", "", "", "", "", "", "");
				break;
			case 3:
				csv = new CsvModel(s[0], s[1], s[2], "", "", "", "", "", "", "", "", "", "");
				break;
			case 4:
				csv = new CsvModel(s[0], s[1], s[2], s[3], "", "", "", "", "", "", "", "", "");
				break;
			case 5:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], "", "", "", "", "", "", "", "");
				break;
			case 6:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], "", "", "", "", "", "", "");
				break;
			case 7:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], "", "", "", "", "", "");
				break;
			case 8:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], "", "", "", "", "");

				break;
			case 9:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], "", "", "", "");
				break;
			case 10:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9], "", "", "");

				break;
			case 11:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9], s[10], "", "");
				break;
			case 12:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9], s[10], s[11], "");
				break;
			case 13:
				csv = new CsvModel(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9], s[10], s[11], s[12]);
				break;

			}

			csvList.add(csv);
		}

		br.close();

		return csvList;

	}
	
	
	public static List<Integer> getTiterList() throws SAXException, IOException
	{
		ArrayList<Integer> titerList=new ArrayList<Integer>();
		File AeXML = ResourceUtils.getFile("classpath:AE.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(AeXML); 
			
			NodeList flowList = doc.getElementsByTagName("Titers");
			
			 for (int i = 0; i < flowList.getLength(); i++) {
			        NodeList childList = flowList.item(i).getChildNodes();
			        for (int j = 0; j < childList.getLength(); j++) {
			            Node childNode = childList.item(j);
			            if ("Titer".equals(childNode.getNodeName())) {
			            	int titerValue=Integer.parseInt(childList.item(j).getTextContent()
			                        .trim());
			            	titerList.add(titerValue);
			                System.out.println(childList.item(j).getTextContent()
			                        .trim());
			            }
			        }
			    }
			// System.out.println(titerList);
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return titerList;
	}
	
   
    
    public static int findIndex(int muNumber) throws SAXException, IOException
    {
    	List<Integer> list=Utility.getTiterList();
//			long distance = Math.abs(list.get(0) - muNumber);
//			int idx = 0;
//			for(int c = 1; c < list.size(); c++){
//				  long cdistance = Math.abs(list.get(c) - muNumber);
//				  if(cdistance < distance){
//				        idx = c;
//				        distance = cdistance;
//				    }
//			}
//			
//			int theNumber = idx;
//			System.out.println("Group Number is "+theNumber);
    	int theNumber = list.stream()
                .min(Comparator.comparingInt(i -> Math.abs(i - muNumber)))
                .orElseThrow(() -> new NoSuchElementException("No value present"));
    	
			return list.indexOf(theNumber)+1;
    }
  

}
