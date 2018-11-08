package za.co.global.domain.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GIRSAExCelColumnHeader {
    String columnHeader();

    String dataType() default "string";

    String defaultValue() default "";

    boolean isContain() default false;
}
