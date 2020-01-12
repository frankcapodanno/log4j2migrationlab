package capodanno.click.lablog4j;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.zip.Deflater;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * This class is a General Logging Configuration that's work with log4j2
 * 
 * @author Francesco Capodanno
 */
public final class GeneralLoggingConfiguration {
	
	public static String software_name = "click.capodanno";
    public static String file_pattern_layout = "%n[%d{yyyy-MM-dd HH:mm:ss}] [%-5p] [%l]%n\t%m%n%n";
    public static String log_file_name = "logger-example-out.log";
    public static String log_file_name_pattern = "logger-example-out-%i.log";
    public static String file_path = "logs/";
    
//    //rolling file properties
//    @Plugin(category = ConfigurationFactory.CATEGORY, name = "GeneralLoggingConfigurationFactory")
//    @Order(1)
//    public static class GeneralLoggingConfigurationFactory  extends ConfigurationFactory {
//        public static final String[] SUFFIXES = new String[] {".json", "*"};
//
//        @Override
//        protected String[] getSupportedTypes() {
//            return SUFFIXES;
//        }
//
//		@Override
//		public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
//			return null;
//		}
//    }
    
    public static void configure() {
    	Log4j2Configuration.runConfiguration();
		//Configurator.setLevel(Logger.GLOBAL_LOGGER_NAME, Level.DEBUG);
		Configurator.reconfigure();
    }
    
    public static void configure(String softwareName, String filePatternLayout, String logFileName, String logFileNamePattern) {
    	software_name = softwareName;
    	file_pattern_layout = filePatternLayout;
    	log_file_name = logFileName;
    	log_file_name_pattern = logFileNamePattern;
    	Log4j2Configuration.runConfiguration();
		//Configurator.setLevel(Logger.GLOBAL_LOGGER_NAME, Level.DEBUG);
		Configurator.reconfigure();
    }

    private static class Log4j2Configuration extends DefaultConfiguration {
    	
         public static void runConfiguration() {
        	 Log4j2Configuration conf = new Log4j2Configuration(ConfigurationSource.COMPOSITE_SOURCE);
         }
        public Log4j2Configuration(ConfigurationSource source) {
            super.doConfigure();
            setName(LogManager.ROOT_LOGGER_NAME);

            String logFilePath = file_path;

            // LOGGERS
            AppenderRef[] refs = new AppenderRef[] {};
            Property[] properties = new Property[] {};
            LoggerConfig generalLoggerConfig = LoggerConfig.createLogger(true, Level.INFO, software_name, "true", refs, properties, this, null);
            addLogger(software_name, generalLoggerConfig);


            // APPENDERS
            final Charset charset = Charset.forName("UTF-8");

            // ROLLING FILE
            TriggeringPolicy mpFileCompositePolicy = CompositeTriggeringPolicy.createPolicy(
                    SizeBasedTriggeringPolicy.createPolicy("3 M"),
                    OnStartupTriggeringPolicy.createPolicy(1));
            final DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.createStrategy("9", "1", "max", Deflater.NO_COMPRESSION + "", null, true, this);
            Layout<? extends Serializable> mpFileLayout = PatternLayout.newBuilder()
                    .withPattern(file_pattern_layout)
                    .withPatternSelector(null)
                    .withConfiguration(this)
                    .withRegexReplacement(null)
                    .withCharset(charset)
                    .withAlwaysWriteExceptions(isShutdownHookEnabled)
                    .withNoConsoleNoAnsi(isShutdownHookEnabled)
                    .withHeader(null)
                    .withFooter(null)
                    .build();
            Appender rollFileAppender = RollingFileAppender.newBuilder()
                    .withAdvertise(Boolean.parseBoolean(null))
                    .withAdvertiseUri(null)
                    .withAppend(true)
                    .withBufferedIo(true)
                    .withBufferSize(8192)
                    .setConfiguration(this)
                    .withFileName(logFilePath + log_file_name)
                    .withFilePattern(logFilePath + log_file_name_pattern)
                    .withFilter(null)
                    .withIgnoreExceptions(true)
                    .withImmediateFlush(true)
                    .withLayout(mpFileLayout)
                    .withCreateOnDemand(false)
                    .withLocking(false)
                    .withName("error_file_web")
                    .withPolicy(mpFileCompositePolicy)
                    .withStrategy(rolloverStrategy)
                    .build();
            rollFileAppender.start();
            addAppender(rollFileAppender);
            getLogger(software_name).addAppender(rollFileAppender, Level.DEBUG, null);
        }}
}