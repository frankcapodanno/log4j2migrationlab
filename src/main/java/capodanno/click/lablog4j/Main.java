package capodanno.click.lablog4j;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/**
 * This example show a hybrid configuration with log4j 1.x and log4j 2 to use in
 * case of migration (programmatically)
 * 
 * @author Francesco Capodanno
 */
public class Main {
	
	private Logger logger;


	public static void main(String[] args) throws IOException {

		// Set Compatibility at system level with log4j 1.x
		Properties props = System.getProperties();
		props.setProperty("log4j1.compatibility", "true");

		// Configure the Logging-System
//		ChangeLoggingConfiguration gc = new ChangeLoggingConfiguration();
//    	gc.setLevelLog4j1(org.apache.log4j.Level.INFO);
//    	gc.setConsolePattern("%d{yyyy-MM-dd HH:mm:ss} from programmatic %-5p %c{1}:%L - %m%n");
//    	gc.setRollingFileNameFile("target/test-rolling-file.log");
//    	gc.setRollingFilePattern("target/test-%i.log");
//		gc.doConfigure();
		
		// Start logging system
		ConfigurationBuilder<BuiltConfiguration> builder = CustomConfigurationFactory.newConfigurationBuilder();
	    //LoggerContext ctx = LoggerContext.getContext(true);
	    Configuration config = CustomConfigurationFactory.createConfiguration("default-logger", builder);
	    //ctx.setConfiguration(config);
	    
	    Configurator.initialize(config);
	    Configurator.setLevel("default-logger", Level.ERROR);
	    //XML Configuration from programmatic
	    builder.writeXmlConfiguration(System.out);
	    
		//Logger logger = LogManager.getLogger(Main.class); // Log4j 2.x
	    Logger logger = Logger.getLogger(Main.class); // Log4j 1.x
		// Create some messages
		for (int i = 0; i <= 10; i++) {
			logger.info("info message");
			logger.debug("debug message");
			logger.error("error message");
			logger.trace("trace message!");
		}

	}

}
