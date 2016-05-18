package pl.edu.uj.sample.use.logs;

import org.apache.logging.log4j.Level;
import pl.edu.uj.logger.KiMageLogger;

public class LogUsage {

    public static void main(String[] args) {
        KiMageLogger logger = KiMageLogger.getLogger(LogUsage.class);

        logger.debug("debug");
        logger.warn("warn");
        logger.trace("trace");
        logger.error("error");
        logger.fatal("fatal");
        logger.info("info");
        logger.logMessage(Level.INFO, "manual level");
        logger.logException(Level.ERROR, "exception", new ArrayIndexOutOfBoundsException());
        logger.logException("exception", new ArrayIndexOutOfBoundsException());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    logger.info("im in thread");
                }
            }).start();
        }


    }

}
