package pl.k4mil.springcleanlogging;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({Autoconfiguration.class})
public @interface EnableSCL {
}
