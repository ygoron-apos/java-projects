/**
 * 
 */
package com.apos.infoobject.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.apos.infoobject.xml.DestinationSmtp;
import com.apos.infoobject.xml.ExcelDataOnlyFormat;
import com.apos.infoobject.xml.ExcelFormat;
import com.apos.infoobject.xml.InfoObject;
import com.apos.infoobject.xml.ParameterValue;
import com.apos.infoobject.xml.PluginInterface;
import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.ReportLogon;
import com.apos.infoobject.xml.ReportParameter;
import com.apos.infoobject.xml.RichTextFormat;
import com.apos.infoobject.xml.SchedulingInfo;
import com.apos.infoobject.xml.TableLocationPrefix;
import com.apos.infoobject.xml.WebiPrompt;
import com.apos.infoobject.xml.WordFormat;

/**
 * @author Yuri Goron
 * 
 */
public class Type2OutputXmlFileInserter implements Runnable {

	private static final Logger Log = Logger.getLogger(Type2OutputXmlFileInserter.class);
	private String fileName;

	public Type2OutputXmlFileInserter(String fileName) {
		this.fileName = fileName;
	}

	public void run() {

		OutputStreamWriter outputStream = null;
		XMLStreamWriter writer = null;

		File tempFile = null;
		try {
			Log.debug("XMLOutputInserter Thread Started");
			tempFile = File.createTempFile("XmlobjectExport_", UUID.randomUUID().toString());
			tempFile.deleteOnExit();
			outputStream = new OutputStreamWriter(new FileOutputStream(tempFile.getAbsoluteFile().toString()), "ISO-8859-1");
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
			writer.writeStartDocument("ISO-8859-1", "1.0");
			// writer = new IndentingXMLStreamWriter(writer);
			writer.writeStartElement("Root");

			while (true) {
				InfoObject xmlInfoObject = Extractor.type2XmlOutputQueue.take();
				Log.debug("Took Job. Queue size:" + Extractor.type2XmlOutputQueue.size());
				if (xmlInfoObject.getSI_ID() <= 0) {
					Log.debug("No More Jobs Left");
					throw new NoMoreJobsException();
				}

				Log.debug("Processing Object ID:" + xmlInfoObject.getSI_ID());
				SchedulingInfo schedulingInfo = xmlInfoObject.getSchedulingInfo();
				writer.writeStartElement("Object");

				writeXmlElement(writer, "Cluster", xmlInfoObject.getCluster());
				writeXmlElement(writer, "SI_ID", xmlInfoObject.getSI_ID());
				writeXmlElement(writer, "SI_PARENTID", xmlInfoObject.getSI_PARENTID());
				writeXmlElement(writer, "SI_LOCATION", xmlInfoObject.getSI_LOCATION());
				writeXmlElement(writer, "InstanceName", schedulingInfo.getInstanceName());

				writer.writeStartElement("SchedulingInfo");
				writeXmlElement(writer, "Type", schedulingInfo.getType());

				if (xmlInfoObject.getSchedulingInfo().getIntervalDays() != null)
					if (xmlInfoObject.getSchedulingInfo().getIntervalDays() > 0)
						writeXmlElement(writer, "IntervalDays", xmlInfoObject.getSchedulingInfo().getIntervalDays());

				if (xmlInfoObject.getSchedulingInfo().getIntervalHours() != null)
					if (xmlInfoObject.getSchedulingInfo().getIntervalHours() > 0)
						writeXmlElement(writer, "IntervalHours", xmlInfoObject.getSchedulingInfo().getIntervalHours());

				if (xmlInfoObject.getSchedulingInfo().getIntervalMinutes() != null)
					if (xmlInfoObject.getSchedulingInfo().getIntervalMinutes() > 0)
						writeXmlElement(writer, "IntervalMinutes", xmlInfoObject.getSchedulingInfo().getIntervalMinutes());

				if (xmlInfoObject.getSchedulingInfo().getIntervalMonths() > 0)
					writeXmlElement(writer, "IntervalMonths", xmlInfoObject.getSchedulingInfo().getIntervalMonths());

				if (xmlInfoObject.getSchedulingInfo().getIntervalNthDay() != null)
					if (xmlInfoObject.getSchedulingInfo().getIntervalNthDay() > 0)
						writeXmlElement(writer, "IntervalNthDay", xmlInfoObject.getSchedulingInfo().getIntervalNthDay());

				if (xmlInfoObject.getSchedulingInfo().getIntervalWeeks() != null)
					if (xmlInfoObject.getSchedulingInfo().getIntervalWeeks() > 0)
						writeXmlElement(writer, "IntervalWeeks", xmlInfoObject.getSchedulingInfo().getIntervalWeeks());

				writer.writeStartElement("Dependants");
				writer.writeCharacters(String.valueOf(schedulingInfo.getDependants().size()));

				for (int i = 0; i < schedulingInfo.getDependants().size(); i++) {
					String tagName = "Dependants-" + (i + 1);
					writeXmlElement(writer, tagName, schedulingInfo.getDependants().get(i));
				}

				writer.writeEndElement(); // Dependants

				writer.writeStartElement("Dependencies");
				writer.writeCharacters(String.valueOf(schedulingInfo.getDependencies().size()));

				for (int i = 0; i < schedulingInfo.getDependencies().size(); i++) {
					String tagName = "Dependencies-" + (i + 1);
					writeXmlElement(writer, tagName, schedulingInfo.getDependencies().get(i));
				}
				writer.writeEndElement(); // Dependences

				writeXmlElement(writer, "BeginDate", schedulingInfo.getBeginDate());
				writeXmlElement(writer, "EndDate", schedulingInfo.getEndDate());
				writeXmlElement(writer, "RetriesAllowed", schedulingInfo.getRetriesAllowed());
				writeXmlElement(writer, "RetryInterval", schedulingInfo.getRetryInterval());
				writeXmlElement(writer, "RightNow", schedulingInfo.isRightNow());
				writeXmlElement(writer, "ScheduleOnBehalfOf", schedulingInfo.getScheduleOnBehalfOf());

				if (schedulingInfo.getDestinationManaged() != null) {
					writer.writeStartElement("Destination");
					writeXmlElement(writer, "DestinationType", schedulingInfo.getDestinationManaged().getType());
					writeXmlElement(writer, "DestinationSendOption", schedulingInfo.getDestinationManaged().getSendOption());
					writeXmlElement(writer, "DestinationName", schedulingInfo.getDestinationManaged().getName());
					writeXmlElement(writer, "DestinationCount", schedulingInfo.getDestinationManaged().getCount());
					if (schedulingInfo.getDestinationManaged().getUserIdInboxes().size() > 0) {
						writer.writeStartElement("UserIDInbox");
						for (int i = 0; i < schedulingInfo.getDestinationManaged().getUserIdInboxes().size(); i++) {
							writeXmlElement(writer, "UserID" + (i + 1), schedulingInfo.getDestinationManaged().getUserIdInboxes().get(i));
						}
						writer.writeEndElement();// UserIDInbox
					}
					writer.writeEndElement();// Destination
				} else if (schedulingInfo.getDestinationDiskUnManaged() != null) {
					writer.writeStartElement("Destination");
					writeXmlElement(writer, "DestinationType", schedulingInfo.getDestinationDiskUnManaged().getType());
					writeXmlElement(writer, "OutputFileName", schedulingInfo.getDestinationDiskUnManaged().getOutputFileName());
					writeXmlElement(writer, "UserName", schedulingInfo.getDestinationDiskUnManaged().getUserName());
					writer.writeEndElement();// Destination
				} else if (schedulingInfo.getDestinationSmtp() != null) {
					writer.writeStartElement("Destination");
					writeXmlElement(writer, "DestinationType", schedulingInfo.getDestinationSmtp().getType());
					writeXmlElement(writer, "From", schedulingInfo.getDestinationSmtp().getFrom());
					writeXmlElement(writer, "To", schedulingInfo.getDestinationSmtp().getTo());
					writeXmlElement(writer, "CC", schedulingInfo.getDestinationSmtp().getCC());
					writeXmlElement(writer, "Subject", schedulingInfo.getDestinationSmtp().getSubject());
					writeXmlElement(writer, "Message", schedulingInfo.getDestinationSmtp().getMessage());
					writeXmlElement(writer, "AttachmentName", schedulingInfo.getDestinationSmtp().getAttachmentName());
					writer.writeEndElement();// Destination
				} else if (schedulingInfo.getDestinationFtp() != null) {
					writer.writeStartElement("Destination");
					writeXmlElement(writer, "DestinationType", schedulingInfo.getDestinationFtp().getType());
					writeXmlElement(writer, "FTPUserName", schedulingInfo.getDestinationFtp().getUserName());
					writeXmlElement(writer, "FTPServerName", schedulingInfo.getDestinationFtp().getServerName());
					writeXmlElement(writer, "FTPPath", schedulingInfo.getDestinationFtp().getPath());
					writeXmlElement(writer, "FTPPort", schedulingInfo.getDestinationFtp().getPort());
					writer.writeEndElement();// Destination

				}
				if (schedulingInfo.getNotification().getEmailNotificationSuccess() != null || schedulingInfo.getNotification().getEmailNotificationFailure() != null) {
					// if
					// (schedulingInfo.getNotification().getAuditNotificationOptionCode()
					// > 0) {
					writer.writeStartElement("Notification");
					writeXmlElement(writer, "AuditNotificationType", schedulingInfo.getNotification().getAuditNotificationOptionCode());
					if (schedulingInfo.getNotification().getEmailNotificationSuccess() != null) {
						DestinationSmtp xmlNotifSuccess = schedulingInfo.getNotification().getEmailNotificationSuccess();
						writer.writeStartElement("Success");
						if (xmlNotifSuccess.getFrom() != null) {
							writeXmlElement(writer, "From", xmlNotifSuccess.getFrom());
						}
						if (xmlNotifSuccess.getTo() != null) {
							writeXmlElement(writer, "To", xmlNotifSuccess.getTo());
						}
						if (xmlNotifSuccess.getCC() != null) {
							writeXmlElement(writer, "CC", xmlNotifSuccess.getCC());
						}

						if (xmlNotifSuccess.getBCC() != null) {
							writeXmlElement(writer, "BCC", xmlNotifSuccess.getBCC());
						}
						if (xmlNotifSuccess.getSubject() != null) {
							writeXmlElement(writer, "Subject", xmlNotifSuccess.getSubject());
						}
						if (xmlNotifSuccess.getMessage() != null) {
							writeXmlElement(writer, "Message", xmlNotifSuccess.getMessage());
						}
						writer.writeEndElement();// Success
					}

					if (schedulingInfo.getNotification().getEmailNotificationFailure() != null) {
						DestinationSmtp xmlNotifFailure = schedulingInfo.getNotification().getEmailNotificationFailure();
						writer.writeStartElement("Failure");
						if (xmlNotifFailure.getFrom() != null) {
							writeXmlElement(writer, "From", xmlNotifFailure.getFrom());
						}
						if (xmlNotifFailure.getTo() != null) {
							writeXmlElement(writer, "To", xmlNotifFailure.getTo());
						}
						if (xmlNotifFailure.getCC() != null) {
							writeXmlElement(writer, "CC", xmlNotifFailure.getCC());
						}

						if (xmlNotifFailure.getBCC() != null) {
							writeXmlElement(writer, "BCC", xmlNotifFailure.getBCC());
						}
						if (xmlNotifFailure.getSubject() != null) {
							writeXmlElement(writer, "Subject", xmlNotifFailure.getSubject());
						}

						if (xmlNotifFailure.getMessage() != null) {
							writeXmlElement(writer, "Message", xmlNotifFailure.getMessage());
						}

						writer.writeEndElement();// Failure
					}

					writer.writeEndElement();// Notifications
				}
				// }

				writer.writeEndElement();// SchedulingInfo

				if (xmlInfoObject.getPluginInterface() != null) {
					PluginInterface xmlPluginInterface = xmlInfoObject.getPluginInterface();
					writer.writeStartElement("PluginInterface");
					writeXmlElement(writer, "GroupFormula", xmlPluginInterface.getGroupFormula());
					writeXmlElement(writer, "RecordFormula", xmlPluginInterface.getRecordFormula());

					if (xmlPluginInterface.getReportFormatOptions() != null) {
						writer.writeStartElement("ReportFormatOptions");
						writeXmlElement(writer, "Format", xmlPluginInterface.getReportFormatOptions().getFormat());

						if (xmlPluginInterface.getReportFormatOptions().getExcelFormat() != null) {
							writer.writeStartElement("ExcelFormat");
							ExcelFormat xmlExcelFormat = xmlPluginInterface.getReportFormatOptions().getExcelFormat();
							writeXmlElement(writer, "ConvertDateToString", xmlExcelFormat.isConvertDateToString());
							writeXmlElement(writer, "CreatePageBreak", xmlExcelFormat.isCreatePageBreak());
							writeXmlElement(writer, "ExportPageHeader", xmlExcelFormat.isExportPageHeader());
							writeXmlElement(writer, "HasColumnHeadings", xmlExcelFormat.isHasColumnHeadings());
							writeXmlElement(writer, "IsTabularFormat", xmlExcelFormat.isTabularFormat());
							writeXmlElement(writer, "ExportAllPages", xmlExcelFormat.isExportAllPages());
							writeXmlElement(writer, "UseConstColWidth", xmlExcelFormat.isUseConstColWidth());
							writeXmlElement(writer, "BaseAreaType", xmlExcelFormat.getBaseAreaType());
							writeXmlElement(writer, "BaseAreaGroupNum", xmlExcelFormat.getBaseAreaGroupNum());
							writeXmlElement(writer, "ConstColWidth", xmlExcelFormat.getConstColWidth());
							writeXmlElement(writer, "StartPageNumber", xmlExcelFormat.getStartPageNumber());
							writeXmlElement(writer, "EndPageNumber", xmlExcelFormat.getEndPageNumber());
							writeXmlElement(writer, "ExportShowGridlines", xmlExcelFormat.isExportShowGridlines());
							writeXmlElement(writer, "ExportPageHeaderFooter", xmlExcelFormat.isExportPageHeader());

							writer.writeEndElement(); // ExcelFormat
						} else if (xmlPluginInterface.getReportFormatOptions().getExcelDataOnlyFormat() != null) {
							writer.writeStartElement("ExcelDataOnlyFormat");
							ExcelDataOnlyFormat xmlExcelDataOnlyFormat = xmlPluginInterface.getReportFormatOptions().getExcelDataOnlyFormat();
							writeXmlElement(writer, "ExportImage", xmlExcelDataOnlyFormat.isImageExported());
							writeXmlElement(writer, "ExportPageHeader", xmlExcelDataOnlyFormat.isPageHeaderExported());
							writeXmlElement(writer, "MaintainColAlignment", xmlExcelDataOnlyFormat.isColAlignmentMaintained());
							writeXmlElement(writer, "SimplifyPageHeader", xmlExcelDataOnlyFormat.isPageHeaderSimplified());
							writeXmlElement(writer, "UseFormat", xmlExcelDataOnlyFormat.isFormatUsed());
							writeXmlElement(writer, "UseWorksheetFunc", xmlExcelDataOnlyFormat.isWorksheetFuncUsed());
							writeXmlElement(writer, "UseConstColWidth", xmlExcelDataOnlyFormat.isConstColWidthUsed());
							writeXmlElement(writer, "BaseAreaType", xmlExcelDataOnlyFormat.getBaseAreaType());
							writeXmlElement(writer, "BaseAreaGroupNum", xmlExcelDataOnlyFormat.getBaseAreaGroupNum());
							writeXmlElement(writer, "ConstColWidth", xmlExcelDataOnlyFormat.getConstColWidth());
							writeXmlElement(writer, "MaintainRelativeObjPosition", xmlExcelDataOnlyFormat.isRelativeObjPositionMaintained());

							writer.writeEndElement(); // ExcelDataOnlyFormat
						} else if (xmlPluginInterface.getReportFormatOptions().getRichTextFormat() != null) {
							RichTextFormat xmlRichTextFormat = xmlPluginInterface.getReportFormatOptions().getRichTextFormat();
							writer.writeStartElement("RichTextFormat");
							writeXmlElement(writer, "ExportAllPages", xmlRichTextFormat.isAllPagesExported());
							writeXmlElement(writer, "StartPageNumber", xmlRichTextFormat.getStartPageNumber());
							writeXmlElement(writer, "EndPageNumber", xmlRichTextFormat.getEndPageNumber());

							writer.writeEndElement(); // WordFormat
						} else if (xmlPluginInterface.getReportFormatOptions().getWordFormat() != null) {
							WordFormat xmlWordFormat = xmlPluginInterface.getReportFormatOptions().getWordFormat();
							writer.writeStartElement("WordFormat");
							writeXmlElement(writer, "ExportAllPages", xmlWordFormat.isAllPagesExported());
							writeXmlElement(writer, "StartPageNumber", xmlWordFormat.getStartPageNumber());
							writeXmlElement(writer, "EndPageNumber", xmlWordFormat.getEndPageNumber());

							writer.writeEndElement(); // WordFormat
						}

						writer.writeEndElement();// ReportFormatOptions
					}

					if (xmlPluginInterface.getReportLogons().size() > 0) {
						Log.debug("Number of ReportLogons:" + xmlPluginInterface.getReportLogons().size());
						writer.writeStartElement("ReportLogons");
						writer.writeCharacters(String.valueOf(xmlPluginInterface.getReportLogons().size()));
						for (int i = 0; i < xmlPluginInterface.getReportLogons().size(); i++) {
							ReportLogon xmlReportLogon = xmlPluginInterface.getReportLogons().get(i);
							String logonTagName = "ReportLogons-" + (i + 1);
							writer.writeStartElement(logonTagName);
							writer.writeCharacters(xmlReportLogon.getServerName());
							writeXmlElement(writer, "CustomDatabaseDLLName", xmlReportLogon.getCustomDatabaseDLLName());
							writeXmlElement(writer, "CustomDatabaseName", xmlReportLogon.getCustomDatabaseName());
							writeXmlElement(writer, "CustomServerName", xmlReportLogon.getCustomServerName());
							writeXmlElement(writer, "CustomServerType", xmlReportLogon.getCustomServerType());

							writeXmlElement(writer, "DatabaseName", xmlReportLogon.getDatabaseName());
							writeXmlElement(writer, "CustomPassword", xmlReportLogon.getCustomPassword());
							writeXmlElement(writer, "CustomUserName", xmlReportLogon.getCustomUserName());

							writeXmlElement(writer, "ServerName", xmlReportLogon.getServerName());
							writeXmlElement(writer, "SubReportName", xmlReportLogon.getSubReportName());
							writeXmlElement(writer, "UseOriginalDataSource", xmlReportLogon.isUseOriginalDataSource());
							writeXmlElement(writer, "UserName", xmlReportLogon.getUserName());

							if (xmlReportLogon.getTablePrefixes().size() > 0) {
								writer.writeStartElement("TableLocationPrefixes");
								writer.writeCharacters(String.valueOf(xmlReportLogon.getTablePrefixes().size()));
								int j = 0;
								for (TableLocationPrefix tablePrefix : xmlReportLogon.getTablePrefixes()) {
									j++;
									String tablePrefixName = "TableLocationPrefixes-" + j;
									writer.writeStartElement(tablePrefixName);
									writer.writeCharacters(tablePrefix.getTableLocationPrefix());
									writeXmlElement(writer, "MappedTablePrefix", tablePrefix.getMappedTablePrefix());
									writeXmlElement(writer, "TablePrefix", tablePrefix.getTableLocationPrefix());
									writeXmlElement(writer, "UseMappedTablePrefix", tablePrefix.isUseMappedTablePrefix());
									writer.writeEndElement(); // TableLocationPrefixes-X;
								}
								writer.writeEndElement(); // TableLocationPrefixes
							}

							writer.writeEndElement(); // ReportLogons-X
						}
						writer.writeEndElement(); // reportLogons
					}

					if (xmlPluginInterface.getReportParameters().size() > 0) {
						Log.debug("Number of Report Parameters:" + xmlPluginInterface.getReportParameters().size());
						writer.writeStartElement("ReportParameters");
						writer.writeCharacters(String.valueOf(xmlPluginInterface.getReportParameters().size()));
						for (int i = 0; i < xmlPluginInterface.getReportParameters().size(); i++) {
							ReportParameter xmlParameter = xmlPluginInterface.getReportParameters().get(i);
							String elementName = "ReportParameters-" + (i + 1);
							writer.writeStartElement(elementName);
							writer.writeCharacters(xmlParameter.getName());
							writeXmlElement(writer, "ParameterName", xmlParameter.getName());
							writeXmlElement(writer, "ReportName", xmlParameter.getSubReportName());

							if (xmlParameter.getCurrentValues().size() > 0) {
								writer.writeStartElement("CurrentValues");
								writer.writeCharacters(String.valueOf(xmlParameter.getCurrentValues().size()));
								for (int j = 0; j < xmlParameter.getCurrentValues().size(); j++) {
									ParameterValue xmlValue = xmlParameter.getCurrentValues().get(j);
									elementName = "CurrentValues-" + (j + 1);
									writer.writeStartElement(elementName);
									writeXmlElement(writer, "IsNull", xmlValue.isNullValue());
									writeXmlElement(writer, "IsRangeValue", xmlParameter.isRangeValue());
									writeXmlElement(writer, "IsSingleValue", xmlParameter.isSingleValue());
									Log.debug("Value:" + xmlValue.getValue());
									writeXmlElement(writer, "Value", xmlValue.getValue());
									writer.writeEndElement(); // CurrentValues-X
								}
								writer.writeEndElement(); // CurrentValues
							}

							writer.writeEndElement(); // ReportParameters-X
						}
						writer.writeEndElement(); // ReportParameters

					} else {
						writer.writeStartElement("ReportParameters");
						writer.writeCharacters("0");
						writer.writeEndElement(); // ReportParameters
					}

					if (xmlPluginInterface.getReportPrinterOptions() != null) {
						writer.writeStartElement("ReportPrinterOptions");
						writeXmlElement(writer, "Enabled", xmlPluginInterface.getReportPrinterOptions().isEnabled());
						writeXmlElement(writer, "Copies", xmlPluginInterface.getReportPrinterOptions().getCopies());
						writeXmlElement(writer, "FromPage", xmlPluginInterface.getReportPrinterOptions().getFromPage());
						writeXmlElement(writer, "ToPage", xmlPluginInterface.getReportPrinterOptions().getToPage());
						writeXmlElement(writer, "PrinterName", xmlPluginInterface.getReportPrinterOptions().getPrinterName());
						writer.writeEndElement(); // ReportPrinterOptions
					}
					if (xmlPluginInterface.getPageLayoutSettings() != null) {
						writer.writeStartElement("PageLayoutSettings");
						writeXmlElement(writer, "PageLayout", xmlPluginInterface.getPageLayoutSettings().getPageLayout());
						if (xmlPluginInterface.getReportPrinterOptions() != null) {
							writeXmlElement(writer, "PageSize", xmlPluginInterface.getReportPrinterOptions().getPageSize());
							writeXmlElement(writer, "LandscapeMode", xmlPluginInterface.getReportPrinterOptions().isIsLandScapeMode());
						}
						writer.writeEndElement(); // PageLayoutSettings

					}
					writer.writeEndElement(); // PluginInterface
				} else if (xmlInfoObject.getPluginInterfaceWebi() != null) {
					writer.writeStartElement("PluginInterface");
					PluginInterfaceWebi xmlPluginInterfaceWebi = xmlInfoObject.getPluginInterfaceWebi();
					if (xmlPluginInterfaceWebi.getWebiFormatOptions() != null) {
						writer.writeStartElement("WebiFormatOptions");
						writeXmlElement(writer, "Format", xmlPluginInterfaceWebi.getWebiFormatOptions().getFormat());
						writer.writeEndElement(); // WebiFormatOptions
					}
					writeXmlElement(writer, "PreCacheTypes", xmlPluginInterfaceWebi.getPreCacheTypes());
					Log.debug("Prompts Size:" + xmlPluginInterfaceWebi.getPrompts().size());
					// if (xmlPluginInterfaceWebi.getPrompts().size() > 0) {
					writer.writeStartElement("Prompts");
					writer.writeCharacters(String.valueOf(xmlPluginInterfaceWebi.getPrompts().size()));

					for (int i = 0; i < xmlPluginInterfaceWebi.getPrompts().size(); i++) {
						WebiPrompt xmlWebiPrompt = xmlPluginInterfaceWebi.getPrompts().get(i);
						String elementName = "Prompts-" + (i + 1);
						writer.writeStartElement(elementName);
						writer.writeCharacters(xmlWebiPrompt.getName());
						writeXmlElement(writer, "Name", xmlWebiPrompt.getName());
						if (xmlWebiPrompt.getValues().size() > 0) {
							writer.writeStartElement("Values");
							writer.writeCharacters(String.valueOf(xmlWebiPrompt.getValues().size()));
							for (int j = 0; j < xmlWebiPrompt.getValues().size(); j++) {
								elementName = "Values-" + (j + 1);
								writer.writeStartElement(elementName);

								if (xmlWebiPrompt.getValues().get(j).getValue() != null) {
									writeXmlElement(writer, "Value", xmlWebiPrompt.getValues().get(j).getValue());
								}

								writer.writeEndElement(); // Values-X
							}

							writer.writeEndElement(); // Values

						}
						writer.writeEndElement(); // Prompts-X
					}
					// }

					writer.writeEndElement(); // Prompts

					writer.writeEndElement(); // PluginInterface

				}
				writer.writeEndElement(); // Object
			}

		} catch (NoMoreJobsException endofJob) {
			Log.debug("Output Inserter Thread will be finished-Copy temp File to the location");
			try {
				writer.writeEndDocument(); // Root
				writer.flush();
				FileUtils.copyFile(tempFile, new File(fileName));
				Log.debug("File :" + tempFile.getAbsolutePath() + " copied to:" + fileName);
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
				Log.error("Failed to copy File :" + tempFile.getAbsolutePath() + " to:" + fileName);
			}
		} catch (Throwable ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			Log.debug("Output Inserter Thread finished");
			try {
				if (writer != null)
					writer.close();
			} catch (Exception ee) {
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception ee) {

			}

		}

	}

	/**
	 * Writes XML Tag
	 * 
	 * @param tagName
	 * @param value
	 * @throws XMLStreamException
	 */
	private void writeXmlElement(XMLStreamWriter writer, String tagName, boolean value) throws XMLStreamException {
		writer.writeStartElement(tagName);
		writer.writeCharacters(String.valueOf(value));
		writer.writeEndElement(); // Cluster

	}

	/**
	 * Writes XML Tag
	 * 
	 * @param tagName
	 * @param value
	 * @throws XMLStreamException
	 */
	private void writeXmlElement(XMLStreamWriter writer, String tagName, int value) throws XMLStreamException {
		writer.writeStartElement(tagName);
		writer.writeCharacters(String.valueOf(value));
		writer.writeEndElement(); // Cluster

	}

	/**
	 * Writes XML Tag
	 * 
	 * 
	 * 
	 * @param writer
	 * @param tagName
	 * @param value
	 * @throws XMLStreamException
	 */
	private void writeXmlElement(XMLStreamWriter writer, String tagName, Double value) throws XMLStreamException {
		writer.writeStartElement(tagName);
		writer.writeCharacters(String.valueOf(value));
		writer.writeEndElement(); // Cluster

	}

	/**
	 * Writes XML Tag
	 * 
	 * @param tagName
	 * @param value
	 * @throws XMLStreamException
	 */
	private void writeXmlElement(XMLStreamWriter writer, String tagName, String value) throws XMLStreamException {
		writer.writeStartElement(tagName);
		writer.writeCharacters(value);
		writer.writeEndElement(); // Cluster

	}

}
