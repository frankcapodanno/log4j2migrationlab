package capodanno.click.lablog4j;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.zip.Deflater;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * This class is a General Logging Configuration that's work with log4j 2.x and
 * log4j 1.x
 * 
 * @author Francesco Capodanno
 */
public class ChangeLoggingConfiguration {

	public static final String CONFIG_NAME = "GeneralConfiguration";

	private Level level;
	private boolean hasConsole;
	private boolean hasRollingFile;
	private String consolePattern;
	private String rollingFilePattern;
	private String rollingFileNameFile;

	public ChangeLoggingConfiguration() {
	}

	// cast levels from log4j 1.x in log4j 2.x
	public void setLevelLog4j1(org.apache.log4j.Level level) {
		this.level = Level.toLevel(level.toString());
	}

	public void setLevelByString(String lvl) {
		this.level = Level.getLevel(lvl);
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	private void createRollingFileAppender(Configuration config) {
		TriggeringPolicy fileCompositePolicy = CompositeTriggeringPolicy.createPolicy(
                SizeBasedTriggeringPolicy.createPolicy("3 M"),
                OnStartupTriggeringPolicy.createPolicy(1));
		
		final DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
				.withMax("9")	
				.withMin("1")
				.build();
		
		Layout<? extends Serializable> fileLayout = PatternLayout.newBuilder()
                .withPattern(consolePattern)
                .withPatternSelector(null)
                .withConfiguration(config)
                .withRegexReplacement(null)
                .withCharset(Charset.defaultCharset())
                .withAlwaysWriteExceptions(false)
                .withNoConsoleNoAnsi(false)
                .withHeader(null)
                .withFooter(null)
                .build();
		
		
//		Appender rfa = RollingFileAppender.newBuilder()
//				.withAdvertise(false)
//				.withAdvertiseUri(null)
//				.withAppend(true)
//				.withBufferedIo(true)
//				.withBufferSize(8192)
//				.setConfiguration(config)
//				.withFileName(rollingFileNameFile)
//				.withFilePattern(rollingFilePattern)
//				.withImmediateFlush(true)
//				.setLayout(fileLayout)
//				.withCreateOnDemand(false)
//				.withLocking(false)
//				.setName("error_file_web")
//				.withPolicy(fileCompositePolicy)
//				.withStrategy(rolloverStrategy)
//				.build();
		
		Appender rfa = RollingFileAppender.createAppender(
				rollingFileNameFile, 
				rollingFilePattern, 
				"true", 
				"error_file_web", 
				"true", 
				"8192", 
				"true", 
				fileCompositePolicy, 
				rolloverStrategy, 
				fileLayout, 
				null, 
				"true", 
				"false", 
				"false", 
				config);
		
		       System.out.println(rfa);

	}
	
	public void setConsolePattern(String consolePattern) {
		this.consolePattern = consolePattern;
	}
	
	public void setRollingFileNameFile(String rollingFileName) {
		this.rollingFileNameFile = rollingFileName;
	}

	public void doConfigure() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		Configurator.setRootLevel(level);
		// RollingFile
		createRollingFileAppender(config);

		ctx.updateLoggers();
		Configurator.reconfigure();
	}

	public void setRollingFilePattern(String rollingFilePattern) {
		this.rollingFileNameFile = rollingFilePattern;
	}
}