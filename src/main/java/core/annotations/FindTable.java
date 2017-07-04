package core.annotations;

import core.datatypes.Pager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by olehk on 02/02/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindTable {

    String xpath();
    Class structure();
    String pagerXpath() default "";
    String currentPageXpath() default "";
    Pager.PagerType pagerType() default Pager.PagerType.STANDARD;

}
