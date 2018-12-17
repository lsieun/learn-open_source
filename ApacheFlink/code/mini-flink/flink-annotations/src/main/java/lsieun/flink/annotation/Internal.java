package lsieun.flink.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR })
@Public
public @interface Internal {
}