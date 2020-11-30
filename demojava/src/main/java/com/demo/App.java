package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App 
{
    public static void main( final String[] args) {
        System.setProperty("log4j.configurationFile", "/workspace/demojava/demojava/src/main/resource/log4j2.xml");
        // System.setProperty("log4j.configurationFile", "/workspace/demojava/demojava/src/main/log4j2.json");
        final Logger log = LogManager.getLogger("myLogger");
        log.info("This is info log !!");
        log.debug("this is a debug log !");
        log.warn("this is a warn message!");
        log.error("this is a error message !");
        log.trace("trace message");
    }
}
