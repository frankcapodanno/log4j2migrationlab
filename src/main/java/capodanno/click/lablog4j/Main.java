package capodanno.click.lablog4j;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

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
		
		GeneralLoggingConfiguration.configure();
		



		for (int i = 0; i <= 150; i++) {
			logger.info("info message");
			logger.debug("debug message");
			logger.error("error message");
			logger.trace("trace message!");
		}

	}

}
