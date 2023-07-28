package com.skshieldus.esecurity.config.datasource.annotation;

import org.springframework.stereotype.Component;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SmartTagMapper {

    String value() default "";

}