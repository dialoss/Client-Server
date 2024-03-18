package Common.Models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModelField {
    boolean NULL() default false;
    int MIN() default Integer.MIN_VALUE;
    int MAX() default Integer.MAX_VALUE;
    boolean AUTO_GENERATE() default false;
    boolean UNIQUE() default false;
    boolean NOT_EMPTY() default false;
    int MAX_LENGTH() default Integer.MAX_VALUE;
    int MIN_LENGTH() default -1;
    boolean AUTO_INCREMENT() default false;
}