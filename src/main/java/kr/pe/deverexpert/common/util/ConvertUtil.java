/**
 * 
 */
package kr.pe.deverexpert.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author DEVEREXPERT
 * 
 */
public class ConvertUtil {

	public static String convertObjectToXml(Object object) throws JAXBException {
		JAXBContext context = null;
		StringWriter sw = null;

		context = JAXBContext.newInstance(object.getClass());
		sw = new StringWriter();

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		m.marshal(object, sw);

		return sw.toString();
	}

	public static Object convertXmlToObject(Object object, String xml) throws JAXBException {
		JAXBContext context = null;
		Unmarshaller u = null;
		StringReader sr = null;

		context = JAXBContext.newInstance(object.getClass());
		u = context.createUnmarshaller();
		sr = new StringReader(xml);

		return u.unmarshal(sr);
	}
	
	public static String convertObjectToJson(Object object) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String strJson = objectMapper.writeValueAsString(object);
		return strJson;
	}
	
	public static Object convertJsonToPojo(String strJson, Object object) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(strJson, object.getClass());
	}

}
