package capodanno.click.lablog4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class ConfigurationGenerator {

	private void ConfigurationGenerator() {
	}

	public static ConfigurationBuilder<BuiltConfiguration> getConfigurationBuilderStructure(String name) {
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

	   builder.setConfigurationName(name);
       builder.setStatusLevel(Level.ERROR);
	   AppenderComponentBuilder console = builder.newAppender("stdout", "Console");

		builder.add(console);

		AppenderComponentBuilder file = builder.newAppender("log", "File");
		file.addAttribute("fileName", "target/logging.log");

		builder.add(file);

		ComponentBuilder triggeringPolicies = builder.newComponent("Policies")
				.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));

		AppenderComponentBuilder rollingFile = builder.newAppender("rolling", "RollingFile");
		rollingFile.addAttribute("fileName", "rolling.log");
		rollingFile.addAttribute("filePattern", "rolling-%d{MM-dd-yy}.log.gz");
		rollingFile.addComponent(triggeringPolicies);

		builder.add(rollingFile);

		FilterComponentBuilder flow = builder.newFilter("MarkerFilter", Filter.Result.ACCEPT, Filter.Result.DENY);
		flow.addAttribute("marker", "FLOW");

		console.add(flow);

		LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
		standard.addAttribute("pattern", "%d[%t]%-5level example: %msg%n%throwable");

		console.add(standard);
		file.add(standard);
		rollingFile.add(standard);
		// RootLogger
		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.ERROR);
		rootLogger.add(builder.newAppenderRef("stdout"));

		builder.add(rootLogger);

//		LoggerComponentBuilder loggerPlus = builder.newLogger("com", Level.DEBUG);
//		loggerPlus.add(builder.newAppenderRef("log"));
//		loggerPlus.addAttribute("additivity", false);
//
//		builder.add(loggerPlus);
		
		return builder;
	}
}