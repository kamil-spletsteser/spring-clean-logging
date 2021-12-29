package pl.k4mil.springcleanlogging;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "scl")
public class Logs {

    public Logs(List<LogSpecs> logs) {
        this.logs = logs;
    }

    private List<LogSpecs> logs;

    public List<LogSpecs> getLogs() {
        return logs;
    }
}
