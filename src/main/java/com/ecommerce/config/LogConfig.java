package com.ecommerce.config;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class LogConfig {
	private static final String LOG_PATH = "E:/logger";
	private static final String LOG_FILE = LOG_PATH + "/app.log";
	
	  @PostConstruct
	    public void configureLogger() {
	        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	        rootLogger.setLevel(Level.INFO);

	        // Console Appender
	        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
	        consoleAppender.setContext(rootLogger.getLoggerContext());
	        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
	        consoleEncoder.setContext(rootLogger.getLoggerContext());
	        consoleEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n");
	        consoleEncoder.start();
	        consoleAppender.setEncoder(consoleEncoder);
	        consoleAppender.start();
	        rootLogger.addAppender(consoleAppender);

	        // File Appender
	        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
	        fileAppender.setContext(rootLogger.getLoggerContext());
	        fileAppender.setFile(LOG_FILE);

	        // Log Rotation Policy (daily rotation)
	        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
	        rollingPolicy.setContext(rootLogger.getLoggerContext());
	        rollingPolicy.setParent(fileAppender);
	        rollingPolicy.setFileNamePattern(LOG_PATH + "/app-%d{yyyy-MM-dd}.log");
	        rollingPolicy.setMaxHistory(30);
	        rollingPolicy.start();

	        fileAppender.setRollingPolicy(rollingPolicy);

	        // File Encoder
	        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
	        fileEncoder.setContext(rootLogger.getLoggerContext());
	        fileEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n");
	        fileEncoder.start();

	        fileAppender.setEncoder(fileEncoder);
	        fileAppender.start();
	        rootLogger.addAppender(fileAppender);
	    }
	}
