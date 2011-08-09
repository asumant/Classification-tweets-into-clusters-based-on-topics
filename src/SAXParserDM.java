

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXParserDM extends DefaultHandler{

	public String tempVal;
	public String temp;	
	private BufferedWriter out;
	
	public SAXParserDM(BufferedWriter out){
		this.out = out;
	}
	
	public void runExample() {
		parseDocument("test_T_10.xml");
	}

	public void parseDocument(String uri) {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse(uri, this);
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		tempVal = "";
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		temp = new String(ch,start,length);
		StringTokenizer st = new StringTokenizer(temp);
		temp = temp.replaceAll(",", "");
		
		if(!temp.equalsIgnoreCase("\n"))	{
			if(st.hasMoreTokens() &&!st.nextToken().contains("RT"))
				tempVal = temp;
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("body")) {
			System.out.println(tempVal);
			try {
				out.write(tempVal.toCharArray());
				out.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
}




