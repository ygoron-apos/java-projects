/**
 * 
 */
package com.apos.infoobject.properties;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ygoron
 * 
 */
public class ExtractorMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				ICommandLineArgs commandLineArgs = new CommandLineArgs(args);
				commandLineArgs.print();
				commandLineArgs.setBeanParameters();
				final AbstractApplicationContext context = new FileSystemXmlApplicationContext("classpath:META-INF/spring/app-context.xml");
				context.registerShutdownHook();
				IService propertyExtractor = context.getBean(IService.class);
				propertyExtractor.execute(commandLineArgs);
				context.close();
			} else {
				throw new Exception("Missing Command Line Arguments");
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}

	public void execute(ICommandLineArgs commandLineArgs, IService propertyExtractor) {
		propertyExtractor.execute(commandLineArgs);
	}

}
