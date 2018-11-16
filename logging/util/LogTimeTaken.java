package org.cms.hios.common.logging.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 
public @interface LogTimeTaken {
    
   public boolean logArguments() default false;
    public String logLevel() default "DEBUG";
}
