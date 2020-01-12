package capodanno.click.lablog4j;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This example show a hybrid configuration with log4j 1.x and log4j 2 to use in case of migration
 * 
 * @author Francesco Capodanno
 */
public class Main {
	
	public static final Logger logger =  Logger.getLogger(Main.class);

	public static void main(String[] args)  {
		
		//Set Compatibility at system level with log4j 1.x 
		Properties props = System.getProperties();
		props.setProperty("log4j1.compatibility", "true");
		
		// Configure the Logging-System
		GeneralLoggingConfiguration.configure();

		// Create some messages
		for (int i = 0; i <= 150; i++) {
			logger.info("info message");
			logger.debug("debug message");
			logger.error("error message");
			logger.trace("trace message!");
		}

	}

}
