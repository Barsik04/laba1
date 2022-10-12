package laba1;

import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test

{
	private static String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static String target = "Q5A8ZWS0XEDC6RFVT9GBY4HNU3J2MI1KO7LP";
	
	private static String obf = "C:\\Users\\prest\\git\\laba1\\laba1\\files\\obf.xml";
	private static String file = "C:\\Users\\prest\\git\\laba1\\laba1\\files\\test.xml";
	private static String unubf = "C:\\Users\\prest\\git\\laba1\\laba1\\files\\unubf.xml";
	

	public static void main(String args[]) {
		
		System.out.print("1. Run obfuscation\n");
		System.out.print("2. Run deobfuscation\n");
		int choose = -1;

		while ((choose != 1) && (choose != 2)) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String name = scanner.nextLine();

			if (isNumber(name))
				choose = Integer.parseInt(name);
		}

		switch (choose) {
		case (1):
			obfuscation();
			break;
		case (2):
			unobfuscatation();
			break;
		}
		System.out.print("End of program");
	}

	public static boolean isNumber(String str) {
		if (str == null || str.isEmpty())
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				System.out.print("Wrong character\n");
				return false;
			}
		}
		return true;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	public static List<Employees> readData(String filepath)

	{
		List<Employees> list = new ArrayList<Employees>();

		File xmlFile = new File(filepath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			// получаем узлы с именем Employee
			// теперь XML полностью загружен в память
			// в виде объекта Document
			NodeList nodeList = document.getElementsByTagName("employee");

			// создадим из него список объектов employee

			for (int i = 0; i < nodeList.getLength(); i++) {
				list.add(getEmploye(nodeList.item(i)));
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return list;
	}

	public static void obfuscation() {
		List<Employees> list = new ArrayList<Employees>();
		list = readData(file);
		
		for (Employees emp : list) {
			emp.setId(obfuscate(emp.getId()));
			emp.setFirstName(obfuscate(emp.getFirstName()));
			emp.setLastName(obfuscate(emp.getLastName()));
			emp.setLocation(obfuscate(emp.getLocation()));
		}
		saveXMLfile(list,obf);
	}
	
	public static void unobfuscatation() {
		List<Employees> list = new ArrayList<Employees>();
		list = readData(obf);
		
		for (Employees emp : list) {
			emp.setId(unobfuscate(emp.getId()));
			emp.setFirstName(unobfuscate(emp.getFirstName()));
			emp.setLastName(unobfuscate(emp.getLastName()));
			emp.setLocation(unobfuscate(emp.getLocation()));
		}
		saveXMLfile(list,unubf);
	}

	private static void saveXMLfile(List<Employees> list, String way) {
		 try {
	            XMLOutputFactory output = XMLOutputFactory.newInstance();
	            XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(way));
	            String end = "\n\t";
	 
	            // Открываем XML-документ и Пишем корневой элемент
	            writer.writeStartDocument("1.0");
	            writer.writeCharacters("\n");
	            writer.writeStartElement("employees");

	      
	            
	            // Делаем цикл для сотрудников
	            for (Employees emp:list) {
	                // Записываем Employee
	            	writer.writeCharacters("\n ");
	                writer.writeStartElement("employee");
	                writer.writeAttribute("id",emp.getId());
	                writer.writeCharacters(end);
	    
	                // Заполняем все тэги для работников                
	                // firstName
	                writer.writeStartElement("firstName");
	                writer.writeCharacters(emp.getFirstName());
	                writer.writeEndElement();
	                writer.writeCharacters(end);
	                // lastName
	                writer.writeStartElement("lastName");
	                writer.writeCharacters(emp.getLastName());
	                writer.writeEndElement();
	                writer.writeCharacters(end);
	                // location
	                writer.writeStartElement("location");
	                writer.writeCharacters(emp.getLocation());
	                writer.writeEndElement();
	                writer.writeCharacters("\n ");
	               
	 
	                // Закрываем тэг Employee
	                writer.writeEndElement();
	            }
	            // Закрываем корневой элемент
	            writer.writeCharacters("\n");
	            writer.writeEndElement();
	            // Закрываем XML-документ
	            writer.writeEndDocument();
	            writer.flush();
	        } catch (XMLStreamException | IOException ex) {
	            ex.printStackTrace();
	        }
	}

	private static Employees getEmploye(Node node) {
		Employees emp = new Employees();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;

			emp.setFirstName(getTagValue("firstName", element));
			emp.setLastName(getTagValue("lastName", element));
			emp.setLocation(getTagValue("location", element));

			NamedNodeMap attributes = node.getAttributes();
			Node nameAttrib = attributes.getNamedItem("id");	
			emp.setId(nameAttrib.getNodeValue());
		}

		return emp;
	}

	public static String obfuscate(String s) {
		s = s.toUpperCase();

		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++) 
		{
			char c = s.charAt(i);
			int index = source.indexOf(c);
			result[i] = target.charAt(index);
			result[i] = Character.toLowerCase(result[i]);
		}
		result[0] = Character.toUpperCase(result[0]);
		return new String(result);
	}

	public static String unobfuscate(String s) {
		s = s.toUpperCase();
		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++) 
		{
			char c = s.charAt(i);
			int index = target.indexOf(c);
			result[i] = source.charAt(index);
			result[i] = Character.toLowerCase(result[i]);
		}
		result[0] = Character.toUpperCase(result[0]);
		return new String(result);
	}

}