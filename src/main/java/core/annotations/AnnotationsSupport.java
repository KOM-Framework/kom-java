package core.annotations;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.lang.annotation.Annotation;

/**
 * Created by olehkuzovkov on 2017-07-04.
 */
public class AnnotationsSupport {


    public static By buildBy(Annotation annotation) {
        FindBy findBy = (FindBy) annotation;
        if (!"".equals(findBy.className()))
            return By.className(findBy.className());

        if (!"".equals(findBy.css()))
            return By.cssSelector(findBy.css());

        if (!"".equals(findBy.id()))
            return By.id(findBy.id());

        if (!"".equals(findBy.linkText()))
            return By.linkText(findBy.linkText());

        if (!"".equals(findBy.name()))
            return By.name(findBy.name());

        if (!"".equals(findBy.partialLinkText()))
            return By.partialLinkText(findBy.partialLinkText());

        if (!"".equals(findBy.tagName()))
            return By.tagName(findBy.tagName());

        if (!"".equals(findBy.xpath()))
            return By.xpath(findBy.xpath());

        // Fall through
        return null;
    }

}
