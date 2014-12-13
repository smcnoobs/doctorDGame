package doctord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UIElementBuilder {
	private static ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	public static ArrayList<UIElement> getElements() {
		return elements;
	}
	
	public static boolean loadElements(String filename) {
		boolean level_did_load = false;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = fact.newDocumentBuilder();
			InputStream is = new FileInputStream(filename);
			Document doc = build.parse(is);
			
			System.out.println("Loading... Document Root: " + doc.getDocumentElement().getNodeName());
			
			NodeList actables = doc.getElementsByTagName("Element");
			
			for (int temp = 0; temp < actables.getLength(); temp++) {
				Node n = actables.item(temp);
		 
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					switch (((Element) n).getAttribute("type")) {
						case "Rectangle":
							elements.add(loadRectangle((Element) n));
							break;
						case "RoundedRect":
							elements.add(loadRoundRect((Element) n));
							break;
						case "Text":
							elements.add(loadText((Element) n));
							break;
						default:
							throw new Exception("Unable to Load Actable of type: " + ((Element) n).getAttribute("type"));
					}
				} else {
					System.out.println("Node is not an Element Node");
				}
			}

			level_did_load = true;
		} catch (ParserConfigurationException e) {
			level_did_load = false;
			e.printStackTrace();
		} catch (SAXException e) {
			level_did_load = false;
			e.printStackTrace();
		} catch (IOException e) {
			level_did_load = false;
			e.printStackTrace();
		} catch (Exception e) {
			level_did_load = false;
			e.printStackTrace();
		}
		return level_did_load;
	}
	
	private static UIShapeElement loadRectangle(Element e) {
		String intString = e.getElementsByTagName("X").item(0).getTextContent();
		int x =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("Y").item(0).getTextContent();
		int y =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("WIDTH").item(0).getTextContent();
		int width =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("HEIGHT").item(0).getTextContent();
		int height =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("ALIGN").item(0).getTextContent();
		int align =  Integer.parseInt(intString);
		Element colorElement = (Element)e.getElementsByTagName("COLOR").item(0);
		Color color = loadColor(colorElement);
		
		if(e.getAttribute("id") != null) {
			UIShapeElement uiel = new UIShapeElement(new Rectangle(x,y,width,height), color, align);
			uiel.setId(e.getAttribute("id"));
			return uiel;
		}
		
		return new UIShapeElement(new Rectangle(x,y,width,height), color, align);
	}
	
	private static UIShapeElement loadRoundRect(Element e) {
		String intString = e.getElementsByTagName("X").item(0).getTextContent();
		int x =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("Y").item(0).getTextContent();
		int y =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("WIDTH").item(0).getTextContent();
		int width =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("HEIGHT").item(0).getTextContent();
		int height =  Integer.parseInt(intString);
		intString = e.getElementsByTagName("RADIUS").item(0).getTextContent();
		int radius = Integer.parseInt(intString);
		intString = e.getElementsByTagName("ALIGN").item(0).getTextContent();
		int align =  Integer.parseInt(intString);
		
		Element colorElement = (Element)e.getElementsByTagName("COLOR").item(0);
		Color color = loadColor(colorElement);
		
		if(e.getAttribute("id") != null) {
			UIShapeElement uiel = new UIShapeElement(new RoundedRectangle(x,y,width,height,radius), color, align);
			uiel.setId(e.getAttribute("id"));
			return uiel;
		}
		
		return new UIShapeElement(new RoundedRectangle(x,y,width,height,radius), color, align);
	}
	
	private static UITextElement loadText(Element e) {
		String text = e.getElementsByTagName("TEXT").item(0).getTextContent();
		String floatString = e.getElementsByTagName("X").item(0).getTextContent();
		float x = Float.parseFloat(floatString);
		floatString = e.getElementsByTagName("Y").item(0).getTextContent();
		float y = Float.parseFloat(floatString);
		
		String intString = e.getElementsByTagName("ALIGN").item(0).getTextContent();
		int align =  Integer.parseInt(intString);
		
		Element colorElement = (Element)e.getElementsByTagName("COLOR").item(0);
		Color color = loadColor(colorElement);
		
		return new UITextElement(text, new Vector2f(x,y), color, align);
	}
	
	private static Color loadColor(Element e) {
		String intString = e.getElementsByTagName("R").item(0).getTextContent();
		int r = Integer.parseInt(intString);
		intString = e.getElementsByTagName("G").item(0).getTextContent();
		int g = Integer.parseInt(intString);
		intString = e.getElementsByTagName("B").item(0).getTextContent();
		int b = Integer.parseInt(intString);
		if(e.getElementsByTagName("ALPHA").getLength() > 0) {
			intString = e.getElementsByTagName("ALPHA").item(0).getTextContent();
			int alpha = Integer.parseInt(intString);
			return new Color(r,g,b,alpha);
		}
			
		return new Color(r,g,b);
	}
}
