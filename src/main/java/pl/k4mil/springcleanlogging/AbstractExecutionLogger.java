package pl.k4mil.springcleanlogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractExecutionLogger {

    private Logger logger;

    private ValueExtractionUtil valueExtractionUtil = new ValueExtractionUtil();

    protected void log(LogSpecs logSpecs, Method method, Object[] args) {
        log(logSpecs, method, args, null);
    }

    protected void log(LogSpecs logSpecs, Method method, Object[] args, Object returnValue) {
        if(logger == null) {
            logger = LoggerFactory.getLogger(method.getDeclaringClass());
        }
        try {
            List<String> values = valueExtractionUtil.extractValues(logSpecs.getPaths(), args, returnValue);
            switch (logSpecs.getLevel()) {
                case INFO:
                    logger.info(logSpecs.getMessage(), values.toArray());
                    break;
                case WARN:
                    logger.warn(logSpecs.getMessage(), values.toArray());
                    break;
                case ERROR:
                    logger.error(logSpecs.getMessage(), values.toArray());
                    break;
                case TRACE:
                    logger.trace(logSpecs.getMessage(), values.toArray());
                    break;
                default:
                    logger.debug(logSpecs.getMessage(), values.toArray());
                    break;
            }
        } catch (Exception e) {
            logger.warn("Logging message omitted due to error while extracting values: {}", e.getLocalizedMessage());
        }
    }
}
