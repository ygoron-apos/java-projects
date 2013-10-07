package com.apos.infoobject.properties;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

public class TestXmlWriter {

	private static final Logger Log = Logger.getLogger(TestXmlWriter.class);
	private static final String STR_OUTPUT_XML_FILE_NAME = "/Volumes/STORAGE1/Temp/ids/output.xml";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void writeXmlToFile() {
		FileOutputStream outputStream = null;
		XMLStreamWriter writer = null;
		try {
			outputStream = new FileOutputStream(new File(STR_OUTPUT_XML_FILE_NAME));
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
			// writer =
			// XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
			writer = new IndentingXMLStreamWriter(writer);
			writer.writeStartElement("Root");
			writer.writeStartElement("Object");

			writer.writeStartElement("Cluster");
			writer.writeCharacters("Cluster Name1");
			writer.writeEndElement();

			writer.writeStartElement("SI_ID");
			writer.writeCharacters("1234");
			writer.writeEndElement();

			writer.writeStartElement("PluginInterface");

			writer.writeStartElement("GroupSelectionFormula");
			writer.writeEndElement();

			writer.writeStartElement("ReportLogons");
			writer.writeCharacters("1");
			writer.writeStartElement("ReportLogons-1");
			writer.writeCharacters("efashion-webi");
			writer.writeStartElement("CustomDatabaseDLLName");
			writer.writeCharacters("crobc");
			writer.writeEndElement();

			writer.writeEndElement();

			writer.writeEndElement();

			writer.writeEndElement();

			writer.writeEndElement();
			writer.writeEndElement();
			writer.flush();

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			try {
				writer.close();
			} catch (Exception ee) {

			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception ee) {
				}
			}
		}
	}

}
