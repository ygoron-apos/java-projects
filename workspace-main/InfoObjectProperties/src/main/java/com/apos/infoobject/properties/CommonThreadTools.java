/**
 * 
 */
package com.apos.infoobject.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.apos.infoobject.xml.DestinationSmtp;
import com.apos.infoobject.xml.ExcelDataOnlyFormat;
import com.apos.infoobject.xml.ExcelFormat;
import com.apos.infoobject.xml.PageLayoutSettings;
import com.apos.infoobject.xml.ParameterValue;
import com.apos.infoobject.xml.PluginInterface;
import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.ProgramObject;
import com.apos.infoobject.xml.ReportFormatOptions;
import com.apos.infoobject.xml.ReportLogon;
import com.apos.infoobject.xml.ReportParameter;
import com.apos.infoobject.xml.ReportPrinterOptions;
import com.apos.infoobject.xml.RichTextFormat;
import com.apos.infoobject.xml.TableLocationPrefix;
import com.apos.infoobject.xml.WebiPrompt;
import com.apos.infoobject.xml.WebiPromptValue;
import com.apos.infoobject.xml.WordFormat;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiFormatOptions;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.plugin.desktop.common.IExcelDataOnlyFormat;
import com.crystaldecisions.sdk.plugin.desktop.common.IExcelFormat;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportFormatOptions;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportLogon;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameter;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterRangeValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterSingleValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterValues;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportProcessingInfo;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportTablePrefix;
import com.crystaldecisions.sdk.plugin.desktop.common.IRichTextFormat;
import com.crystaldecisions.sdk.plugin.desktop.common.IWordFormat;
import com.crystaldecisions.sdk.plugin.desktop.program.IBinaryProgram;
import com.crystaldecisions.sdk.plugin.desktop.program.IJavaProgram;
import com.crystaldecisions.sdk.plugin.desktop.program.IProgram;
import com.crystaldecisions.sdk.plugin.desktop.program.IScriptProgram;
import com.crystaldecisions.sdk.plugin.destination.smtp.IAttachment;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;
import com.crystaldecisions.sdk.properties.ISDKList;
import com.crystaldecisions.sdk.properties.ISDKSet;

/**
 * @author ygoron
 * 
 */
public class CommonThreadTools {

	private static final String STR_EMPTY_PARAMETER_VALUE = "^SKIPREPORTPROMPT^";
	private static final Logger Log = Logger.getLogger(CommonThreadTools.class);

	/**
	 * Gets Webi Plugin Intereface
	 * 
	 * @param infoObject
	 * @return
	 */
	public PluginInterfaceWebi getPluginInterfaceWebi(IInfoObject infoObject) {
		Log.debug("Process Webi");
		PluginInterfaceWebi xmlInterfaceWebi = null;
		try {
			IWebiXMLProcessor webiXmlProcessor;

			if (Extractor.BI_VERSION == 3)
				webiXmlProcessor = new Webi31Processor();
			else
				webiXmlProcessor = new Webi4xProcessor();
			xmlInterfaceWebi = webiXmlProcessor.getWebiPluginInterface(infoObject);
			return xmlInterfaceWebi;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return null;
	}

	/**
	 * @param infoObject
	 * @return
	 */
	public PluginInterface getCRBasedPluginInterface(IInfoObject infoObject) {
		try {
			PluginInterface xmlPluginInterface = new PluginInterface();
			IReportProcessingInfo procesingInfo = (IReportProcessingInfo) infoObject;
			Log.debug("Report Kind:" + infoObject.getKind());
			xmlPluginInterface.setGroupFormula(procesingInfo.getGroupFormula());
			xmlPluginInterface.setRecordFormula(procesingInfo.getRecordFormula());

			if (procesingInfo.getReportPrinterOptions() != null) {
				ReportPrinterOptions xmlReportPrinterOptions = new ReportPrinterOptions();
				xmlReportPrinterOptions.setEnabled(procesingInfo.getReportPrinterOptions().isEnabled());
				xmlReportPrinterOptions.setCopies(procesingInfo.getReportPrinterOptions().getCopies());
				xmlReportPrinterOptions.setFromPage(procesingInfo.getReportPrinterOptions().getFromPage());
				xmlReportPrinterOptions.setPageHeight(new Integer(procesingInfo.getReportPrinterOptions().getPageHeight()));
				xmlReportPrinterOptions.setPageLayout(procesingInfo.getReportPrinterOptions().getPageLayout());
				xmlReportPrinterOptions.setPageSize(procesingInfo.getReportPrinterOptions().getPageSize());
				xmlReportPrinterOptions.setPageWidth(new Integer(procesingInfo.getReportPrinterOptions().getPageWidth()));
				xmlReportPrinterOptions.setPrintCollationType(procesingInfo.getReportPrinterOptions().getPrintCollationType());
				xmlReportPrinterOptions.setPrinterName(procesingInfo.getReportPrinterOptions().getPrinterName());
				xmlReportPrinterOptions.setToPage(procesingInfo.getReportPrinterOptions().getToPage());

				xmlPluginInterface.setReportPrinterOptions(xmlReportPrinterOptions);
			}

			if (procesingInfo.getReportPrinterOptions() != null) {
				PageLayoutSettings xmlPageLayoutSettings = new PageLayoutSettings();
				xmlPageLayoutSettings.setPageLayout(procesingInfo.getReportPrinterOptions().getPageLayout());
				xmlPluginInterface.setPageLayoutSettings(xmlPageLayoutSettings);
			}

			if (procesingInfo.getReportFormatOptions() != null) {
				ReportFormatOptions xmlReportFormatOptions = new ReportFormatOptions();
				xmlReportFormatOptions.setFormat(procesingInfo.getReportFormatOptions().getFormat());
				xmlReportFormatOptions.setFormatString(getCRStringFormat(procesingInfo.getReportFormatOptions()));

				Log.debug("Format: " + procesingInfo.getReportFormatOptions().getFormat());
				Log.debug("Format Interface:" + procesingInfo.getReportFormatOptions().getFormatInterface());
				if (procesingInfo.getReportFormatOptions().getFormatInterface() instanceof IExcelFormat) {
					Log.debug("Instance of IExcelFormat");

					if (procesingInfo.getReportFormatOptions().getFormat() == IReportFormatOptions.CeReportFormat.EXCEL) {
						IExcelFormat excelFormat = (IExcelFormat) procesingInfo.getReportFormatOptions().getFormatInterface();

						ExcelFormat xmlExcelFormat = new ExcelFormat();

						try {
							xmlExcelFormat.setBaseAreaGroupNum(new Integer(excelFormat.getBaseAreaGroupNum()));
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setBaseAreaType(excelFormat.getBaseAreaType());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setConstColWidth(excelFormat.getConstColWidth());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setConvertDateToString(excelFormat.isDateConvertedToString());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setCreatePageBreak(excelFormat.isPageBreakCreated());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setEndPageNumber(excelFormat.getEndPageNumber());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setExportAllPages(excelFormat.isAllPagesExported());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setExportPageHeader(excelFormat.isPageHeaderExported());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setExportShowGridlines(excelFormat.isGridlineShown());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setHasColumnHeadings(excelFormat.isColumnHeadingAvailable());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setStartPageNumber(excelFormat.getStartPageNumber());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setTabularFormat(excelFormat.isTabularFormat());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlExcelFormat.setUseConstColWidth(excelFormat.isConstColWidthUsed());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}

						xmlReportFormatOptions.setExcelFormat(xmlExcelFormat);
					}
				} else if (procesingInfo.getReportFormatOptions().getFormatInterface() instanceof IExcelDataOnlyFormat) {
					if (procesingInfo.getReportFormatOptions().getFormat() == IReportFormatOptions.CeReportFormat.EXCEL_DATA_ONLY) {
						IExcelDataOnlyFormat excelDataOnlyFormat = (IExcelDataOnlyFormat) procesingInfo.getReportFormatOptions().getFormatInterface();
						ExcelDataOnlyFormat xmlDataOnlyFormat = new ExcelDataOnlyFormat();

						try {
							xmlDataOnlyFormat.setBaseAreaGroupNum(new Integer(excelDataOnlyFormat.getBaseAreaGroupNum()));
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setBaseAreaType(excelDataOnlyFormat.getBaseAreaType());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setColAlignmentMaintained(excelDataOnlyFormat.isColAlignmentMaintained());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setConstColWidth(excelDataOnlyFormat.getConstColWidth());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setConstColWidthUsed(excelDataOnlyFormat.isConstColWidthUsed());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setFormatUsed(excelDataOnlyFormat.isFormatUsed());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setImageExported(excelDataOnlyFormat.isImageExported());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setPageHeaderExported(excelDataOnlyFormat.isPageHeaderExported());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setPageHeaderSimplified(excelDataOnlyFormat.isPageHeaderSimplified());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setRelativeObjPositionMaintained(excelDataOnlyFormat.isRelativeObjPositionMaintained());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setShowGroupOutlines(excelDataOnlyFormat.isShowGroupOutlines());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlDataOnlyFormat.setWorksheetFuncUsed(excelDataOnlyFormat.isWorksheetFuncUsed());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						xmlReportFormatOptions.setExcelDataOnlyFormat(xmlDataOnlyFormat);
					}

				} else if (procesingInfo.getReportFormatOptions().getFormatInterface() instanceof IRichTextFormat) {
					if (procesingInfo.getReportFormatOptions().getFormat() == IReportFormatOptions.CeReportFormat.RTF) {
						IRichTextFormat richTextFormat = (IRichTextFormat) procesingInfo.getReportFormatOptions().getFormatInterface();
						RichTextFormat xmlRichTextFormat = new RichTextFormat();
						try {
							xmlRichTextFormat.setAllPagesExported(richTextFormat.isAllPagesExported());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlRichTextFormat.setEndPageNumber(richTextFormat.getEndPageNumber());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlRichTextFormat.setStartPageNumber(richTextFormat.getStartPageNumber());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						xmlReportFormatOptions.setRichTextFormat(xmlRichTextFormat);
					}
				} else if (procesingInfo.getReportFormatOptions().getFormatInterface() instanceof IWordFormat) {

					if (procesingInfo.getReportFormatOptions().getFormat() == IReportFormatOptions.CeReportFormat.WORD) {
						IWordFormat wordFormat = (IWordFormat) procesingInfo.getReportFormatOptions().getFormatInterface();
						WordFormat xmlWordtFormat = new WordFormat();
						try {
							xmlWordtFormat.setAllPagesExported(wordFormat.isAllPagesExported());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlWordtFormat.setEndPageNumber(wordFormat.getEndPageNumber());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						try {
							xmlWordtFormat.setStartPageNumber(wordFormat.getStartPageNumber());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage());
						}
						xmlReportFormatOptions.setWordFormat(xmlWordtFormat);
					}

				}

				xmlPluginInterface.setReportFormatOptions(xmlReportFormatOptions);

			}

			List<ReportLogon> reportLogons = new ArrayList<ReportLogon>();
			reportLogons = getLogonInfo(infoObject, reportLogons);
			List<ReportParameter> reportParameters = new ArrayList<ReportParameter>();
			reportParameters = getReportParameters(infoObject, reportParameters);
			xmlPluginInterface.setReportParameters(reportParameters);
			xmlPluginInterface.setReportLogons(reportLogons);
			return xmlPluginInterface;

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return null;
	}

	public String getCRStringFormat(IReportFormatOptions reportFormatOptions) {
		switch (reportFormatOptions.getFormat()) {
		case IReportFormatOptions.CeReportFormat.CRYSTAL_REPORT:
			return "";
		case IReportFormatOptions.CeReportFormat.EXCEL:
			return "XLS";
		case IReportFormatOptions.CeReportFormat.EXCEL_DATA_ONLY:
			return "XLS";
		case IReportFormatOptions.CeReportFormat.PDF:
			return "PDF";
		case IReportFormatOptions.CeReportFormat.RTF:
			return "RTF";
		case IReportFormatOptions.CeReportFormat.TEXT_CHARACTER_SEPARATED:
			return "CSV";
		case IReportFormatOptions.CeReportFormat.TEXT_PAGINATED:
			return "PXT";
		case IReportFormatOptions.CeReportFormat.TEXT_PLAIN:
			return "TXT";
		case IReportFormatOptions.CeReportFormat.WORD:
			return "DOC";
		case IReportFormatOptions.CeReportFormat.XML:
			return "XML";
		}

		return "";
	}

	public String getWebiStringFormat(IWebiFormatOptions webiFormatOptions) {
		switch (webiFormatOptions.getFormat()) {
		case IWebiFormatOptions.CeWebiFormat.CSV:
			return "CSV";
		case IWebiFormatOptions.CeWebiFormat.EXCEL:
			return "XLS";
		case IWebiFormatOptions.CeWebiFormat.PDF:
			return "PDF";
		case IWebiFormatOptions.CeWebiFormat.Webi:
			return "WEBI";
		}

		return "";
	}

	/**
	 * Gets XML Program Info
	 * 
	 * @param infoObject
	 * @return
	 */
	public ProgramObject getProgramObject(IInfoObject infoObject) {
		Log.debug("Get Program Object Started");
		ProgramObject xmlProgramObject = null;
		try {
			if (infoObject.getKind().equalsIgnoreCase("Program")) {
				xmlProgramObject = new ProgramObject();
				IProgram program = (IProgram) infoObject;
				switch (program.getProgramType()) {
				case IProgram.CeProgramType.BINARY:
					IBinaryProgram binaryProgram = (IBinaryProgram) program.getProgramInterface();
					xmlProgramObject.setArgs(binaryProgram.getArgs());
					Log.debug("Binary Program Args:" + xmlProgramObject.getArgs());
					xmlProgramObject.setUserName(binaryProgram.getUserName());
					xmlProgramObject.setPassword("");
					break;

				case IProgram.CeProgramType.JAVA:
					IJavaProgram javaProgram = (IJavaProgram) program.getProgramInterface();
					xmlProgramObject.setArgs(javaProgram.getArgs());
					Log.debug("Java Program Args:" + xmlProgramObject.getArgs());
					xmlProgramObject.setUserName(javaProgram.getUserName());
					xmlProgramObject.setPassword("");
					break;

				case IProgram.CeProgramType.SCRIPT:
					IScriptProgram scriptProgram = (IScriptProgram) program.getProgramInterface();
					xmlProgramObject.setArgs(scriptProgram.getArgs());
					Log.debug("Script Program Args:" + xmlProgramObject.getArgs());
					xmlProgramObject.setUserName(scriptProgram.getUserName());
					xmlProgramObject.setPassword("");
					break;

				}
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return xmlProgramObject;
	}

	/**
	 * Get Logon Info
	 * 
	 * @param result
	 * @param infoObject
	 * @param maxDbLogons
	 * @return
	 */
	public List<ReportLogon> getLogonInfo(IInfoObject infoObject, List<ReportLogon> reportLogons) {
		Log.debug("getReportLogonInfo Started for Object ID:" + infoObject.getID());
		try {

			if (infoObject.properties().getProperty("SI_PROGID_MACHINE").toString().equalsIgnoreCase("CrystalEnterprise.Report")) {
				// if (infoObject.getKind().equalsIgnoreCase("CrystalReport")) {
				IReportProcessingInfo processingInfo = (IReportProcessingInfo) infoObject;

				ISDKList logons = processingInfo.getReportLogons();
				Log.debug("Logon Size:" + logons.size());
				for (int i = 0; i < logons.size(); i++) {
					IReportLogon reportLogon = (IReportLogon) logons.get(i);
					ReportLogon xmlReportLogon = new ReportLogon();
					// reportLogon.setLogon(logonBag.getProperty("SI_SERVER").toString())
					xmlReportLogon.setCustomDatabaseDLLName(reportLogon.getCustomDatabaseDLLName() != null ? reportLogon.getCustomDatabaseDLLName() : "");
					xmlReportLogon.setCustomDatabaseName(reportLogon.getCustomDatabaseName() != null ? reportLogon.getCustomDatabaseName() : "");
					xmlReportLogon.setCustomServerName(reportLogon.getCustomServerName() != null ? reportLogon.getCustomServerName() : "");
					xmlReportLogon.setCustomServerType(reportLogon.getCustomServerType());
					xmlReportLogon.setCustomUserName(reportLogon.getCustomUserName() != null ? reportLogon.getCustomUserName() : "");
					xmlReportLogon.setDatabaseName(reportLogon.getDatabaseName() != null ? reportLogon.getDatabaseName() : "");
					xmlReportLogon.setServerName(reportLogon.getServerName() != null ? reportLogon.getServerName() : "");
					xmlReportLogon.setSubReportName(reportLogon.getSubReportName() != null ? reportLogon.getSubReportName() : "");
					xmlReportLogon.setUseOriginalDataSource(reportLogon.isOriginalDataSource() ? true : false);

					List<TableLocationPrefix> tablePrefixes = new ArrayList<TableLocationPrefix>();

					Log.debug("Table Prefixes Size:" + reportLogon.getReportTablePrefixes().size());

					// while
					// (reportLogon.getReportTablePrefixes().iterator().hasNext())
					// {
					// IReportTablePrefix reportTablePrefix =
					// (IReportTablePrefix)
					// reportLogon.getReportTablePrefixes().iterator().next();
					// if (reportTablePrefix != null) {
					// TableLocationPrefix xmlTablePrefix = new
					// TableLocationPrefix();
					// xmlTablePrefix.setMappedTablePrefix(reportTablePrefix.getMappedTablePrefix());
					// xmlTablePrefix.setTableLocationPrefix(reportTablePrefix.getTablePrefix());
					// xmlTablePrefix.setUseMappedTablePrefix(reportTablePrefix.getUseMappedTablePrefix());
					// Log.debug("Table Prefix " +
					// reportTablePrefix.getTablePrefix() + " Added");
					// tablePrefixes.add(xmlTablePrefix);
					//
					// }
					//
					// }

					ISDKSet prefixes = reportLogon.getReportTablePrefixes();

					@SuppressWarnings("rawtypes")
					Iterator iterator = prefixes.iterator();
					while (iterator.hasNext()) {
						IReportTablePrefix reportTablePrefix = (IReportTablePrefix) iterator.next();
						if (reportTablePrefix != null) {
							TableLocationPrefix xmlTablePrefix = new TableLocationPrefix();
							xmlTablePrefix.setMappedTablePrefix(reportTablePrefix.getMappedTablePrefix());
							xmlTablePrefix.setTableLocationPrefix(reportTablePrefix.getTablePrefix());
							xmlTablePrefix.setUseMappedTablePrefix(reportTablePrefix.getUseMappedTablePrefix());
							Log.debug("Table Prefix " + reportTablePrefix.getTablePrefix() + " Added");
							tablePrefixes.add(xmlTablePrefix);

						}
					}
					xmlReportLogon.setTablePrefixes(tablePrefixes);
					xmlReportLogon.setUseOriginalDataSource(reportLogon.isCustomizable());
					xmlReportLogon.setUserName(reportLogon.getUserName());
					// TODO Delete!
					xmlReportLogon.setPassword("Moscow360");
					reportLogons.add(xmlReportLogon);
				}
			}

		} catch (Exception ee) {
			Log.error("Object Id:" + infoObject.getID(), ee);
		}

		return reportLogons;
	}

	/**
	 * Populates Report Parameter List
	 * 
	 * @param infoObject
	 * @return
	 */
	public List<ReportParameter> getReportParameters(IInfoObject infoObject, List<ReportParameter> xmlReportParameters) {
		Log.debug("get Report parameters started for Object ID:" + infoObject.getID() + " Report Name:" + infoObject.getTitle());

		// List<ReportParameter> xmlReportParameters = new
		// ArrayList<ReportParameter>();
		try {
			if (infoObject.getKind().equalsIgnoreCase("CrystalReport")) {
				IReportProcessingInfo processingInfo = (IReportProcessingInfo) infoObject;
				Log.debug("Parameters for report " + infoObject.getID() + " " + processingInfo.getReportParameters().size());
				for (int i = 0; i < processingInfo.getReportParameters().size(); i++) {
					ReportParameter xmlReportParameter = new ReportParameter();
					IReportParameter reportParameter = (IReportParameter) processingInfo.getReportParameters().get(i);

					Log.debug("Parameter Name:" + reportParameter.getParameterName());
					xmlReportParameter.setName(reportParameter.getParameterName());
					xmlReportParameter.setSubReportName(reportParameter.getReportName());
					xmlReportParameter.setReportName(infoObject.getTitle());
					xmlReportParameter.setIsRangeValue(reportParameter.isRangeValueSupported());
					xmlReportParameter.setSingleValue(!reportParameter.isRangeValueSupported());

					List<ParameterValue> xmlParameterValues = new ArrayList<ParameterValue>();
					if (reportParameter != null) {
						if (reportParameter.getCurrentValues() != null) {
							IReportParameterValues currentValues = reportParameter.getCurrentValues();
							Log.debug("Current Values Size:" + currentValues.size());
							for (int j = 0; j < currentValues.size(); j++) {
								if (!reportParameter.isRangeValueSupported()) {
									Log.debug("Single Value");
									ParameterValue xmlCurrentValue = new ParameterValue();
									Object value = currentValues.get(j);
									Log.debug("Object Class:" + value.toString());
									IReportParameterSingleValue parameterSingleValue = (IReportParameterSingleValue) value;
									if (!reportParameter.isInUse())
										parameterSingleValue.setValue(STR_EMPTY_PARAMETER_VALUE);
									xmlCurrentValue.setNullValue(parameterSingleValue.isNull());
									Log.debug("SingleValue:" + parameterSingleValue.getValue());
									xmlCurrentValue.setValue(parameterSingleValue.getValue());
									xmlParameterValues.add(xmlCurrentValue);
								} else {
									Log.debug("Range Value");
									ParameterValue xmlCurrentValue = new ParameterValue();
									Object value = currentValues.get(j);
									Log.debug("Object Class:" + value.toString());
									IReportParameterRangeValue rangeValue = (IReportParameterRangeValue) value;
									xmlCurrentValue.setIsRangeValueFromNull(rangeValue.getFromValue().isNull());
									xmlCurrentValue.setIsRangeValueToNull(rangeValue.getToValue().isNull());
									xmlCurrentValue.setIsIncludeValueFrom(rangeValue.isLowerBoundIncluded());
									xmlCurrentValue.setIsIncludeValueTo(rangeValue.isUpperBoundIncluded());
									xmlCurrentValue.setIsNoLowerValue(rangeValue.isLowerBoundNotAvailable());
									xmlCurrentValue.setIsNoUpperValue(rangeValue.isUpperBoundNotAvailable());
									xmlCurrentValue.setRangeValueFrom(rangeValue.getFromValue().getValue());
									Log.debug("Range Value From:" + rangeValue.getFromValue().getValue());
									xmlCurrentValue.setRangeValueTo(rangeValue.getToValue().getValue());
									Log.debug("Range Value To:" + rangeValue.getToValue().getValue());
									xmlParameterValues.add(xmlCurrentValue);

								}
							}
							xmlReportParameter.setCurrentValues(xmlParameterValues);
						}

					}
					xmlReportParameters.add(xmlReportParameter);
				}
			} else if (infoObject.getKind().equalsIgnoreCase("Webi")) {
				Log.debug("Process Webi");
				PluginInterfaceWebi xmlInterfaceWebi = null;

				try {
					IWebiXMLProcessor webiXmlProcessor;

					if (Extractor.BI_VERSION == 3)
						webiXmlProcessor = new Webi31Processor();
					else
						webiXmlProcessor = new Webi4xProcessor();
					xmlInterfaceWebi = webiXmlProcessor.getWebiPluginInterface(infoObject);

					Log.debug("Prompts Size:" + xmlInterfaceWebi.getPrompts().size());
					for (WebiPrompt webiPrompt : xmlInterfaceWebi.getPrompts()) {
						ReportParameter xmlReportParameter = new ReportParameter();

						xmlReportParameter.setIsRangeValue(false);
						xmlReportParameter.setSingleValue(true);

						xmlReportParameter.setName(webiPrompt.getName());
						List<ParameterValue> xmlParameterValues = new ArrayList<ParameterValue>();
						if (webiPrompt.getValues() != null) {
							for (WebiPromptValue webiPromptValue : webiPrompt.getValues()) {
								ParameterValue xmlCurrentValue = new ParameterValue();

								xmlCurrentValue.setNullValue(webiPromptValue == null ? true : false);

								Log.debug("SingleValue:" + webiPromptValue.getValue());
								xmlCurrentValue.setValue(webiPromptValue.getValue());
								xmlParameterValues.add(xmlCurrentValue);
							}

							xmlReportParameter.setCurrentValues(xmlParameterValues);
						}

						xmlReportParameters.add(xmlReportParameter);

					}

				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
				}

			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return xmlReportParameters;
	}

	/**
	 * Gets XML for SMTP destination
	 * 
	 * @param destination
	 * @param infoStore
	 * @param commonThreadTools
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DestinationSmtp getDestinationSmtp(IDestination destination, IInfoStore infoStore) {

		try {
			DestinationSmtp xmlDestinationSmtp = new DestinationSmtp();

			Log.debug("Destination Smtp");
			IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_SMTP).get(0);
			destination.copyToPlugin(destinationPlugin);
			ISMTPOptions smtpOptions = (ISMTPOptions) destinationPlugin.getScheduleOptions();
			if (smtpOptions.getAttachments() != null) {
				String result = "";
				for (int j = 0; j < smtpOptions.getAttachments().size(); j++) {
					IAttachment attachment = (IAttachment) smtpOptions.getAttachments().get(j);
					result += attachment.getEmbedName() + ",";
				}
				if (result.endsWith(","))
					result = result.substring(0, result.length() - 1);
				xmlDestinationSmtp.setAttachmentName(result);
			}
			xmlDestinationSmtp.setCC(stringFromArray(smtpOptions.getCCAddresses()));
			// if (smtpOptions.getSenderAddress() != null)
			xmlDestinationSmtp.setFrom((smtpOptions.getSenderAddress() == null) ? "" : smtpOptions.getSenderAddress());

			// if (smtpOptions.getMessage() != null)
			xmlDestinationSmtp.setMessage((smtpOptions.getMessage() == null) ? "" : smtpOptions.getMessage());

			// if (smtpOptions.getSubject() != null)
			xmlDestinationSmtp.setSubject((smtpOptions.getSubject()) == null ? "" : smtpOptions.getSubject());

			xmlDestinationSmtp.setPort(smtpOptions.getPort());
			xmlDestinationSmtp.setTo(stringFromArray(smtpOptions.getToAddresses()));
			return xmlDestinationSmtp;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return null;

	}

	/**
	 * Gets Deski Plugin Intereface
	 * 
	 * @param infoObject
	 * @return
	 */
	public PluginInterfaceWebi getPluginInterfaceDeski(IInfoObject infoObject) {
		Log.debug("Process Deski");
		PluginInterfaceWebi xmlInterfaceWebi = null;
		try {
			IWebiXMLProcessor webiXmlProcessor;

			if (Extractor.BI_VERSION == 3) {
				webiXmlProcessor = new Webi31Processor();
				xmlInterfaceWebi = webiXmlProcessor.getWebiPluginInterfaceFromDeski(infoObject);
				return xmlInterfaceWebi;
			}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return null;
	}

	public String stringFromArray(List<String> strings) {
		String result = "";
		if (strings != null) {
			for (String string : strings) {
				result += string + ",";
			}
			if (result.endsWith(","))
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

}
