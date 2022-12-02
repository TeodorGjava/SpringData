package exam.util;

import javax.xml.validation.Validator;

public interface ValidationsUtil {
    <E> boolean isValid(E entity);
}
