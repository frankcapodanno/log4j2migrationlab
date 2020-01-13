package capodanno.click.lablog4j;

import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/**
 * This example show a hybrid configuration with log4j 1.x and log4j 2 to use in
 * case of migration
 * 
 * @author Francesco Capodanno
 */
public class Main {
	
	private static Logger logger;


	public static void main(String[] args) {

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
	    LoggerContext ctx = LoggerContext.getContext(false);
	    ctx.setConfiguration(CustomConfigurationFactory.createConfiguration("my-log", builder));
		
		Logger logger = LogManager.getLogger(Main.class);

		 logger = LogManager.getRootLogger();
		// Create some messages
		for (int i = 0; i <= 10; i++) {
			logger.info("info message");
			logger.debug("debug message");
			logger.error("error message");
			logger.trace("trace message!");
		}

	}

}
