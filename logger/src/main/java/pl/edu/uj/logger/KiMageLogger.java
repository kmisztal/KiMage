package pl.edu.uj.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

public class KiMageLogger  {

    private static final String FQCN = KiMageLogger.class.getName();
    private ExtendedLoggerWrapper logger;

    public KiMageLogger(String name)
    {
        Logger log = LogManager.getLogger(name);
        logger = new ExtendedLoggerWrapper((ExtendedLogger) log, log.getName(), log.getMessageFactory());
    }

    public static KiMageLogger getLogger(Class c){
        return new KiMageLogger(c.getClass().getName());
    }

    private void log(Level level, String message, Throwable t){
        logger.logIfEnabled(FQCN, level, null, new SimpleMessage(message), t);
    }

    private void log(Level level, String message){
        logger.logIfEnabled(FQCN, level, null, new SimpleMessage(message), null);
    }

    public void logMessage(Level level, String message){
       log(level, message);
    }

    public void logException(Level level, String message, Throwable t){
        log(level, message, t);
    }

    public void logException(String message, Throwable t){
        log(Level.ERROR, message, t);
    }

    public void trace(String message){
        log(Level.TRACE, message);
    }

    public void warn(String message){
        log(Level.WARN, message);
    }

    public void error(String message){
        log(Level.ERROR, message);
    }

    public void fatal(String message){
        log(Level.FATAL, message);
    }

    public void debug(String message){
        log(Level.DEBUG, message);
    }

    public void info(String message){
        log(Level.INFO, message);
    }

}