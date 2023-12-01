package BusinessLogic.Validators;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class NameValidator<T> implements Validator<T>{
    private static final String NAME_PATTERN = "[a-zA-Z ]+";

    @Override
    public void validate(T t) throws Exception {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        for (Field field : t.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if (!fieldName.equalsIgnoreCase("name")) continue;
            try {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, t.getClass());
                Method getter = pd.getReadMethod();
                String name = (String) getter.invoke(t);
                if (!pattern.matcher(name).matches() || name.equals("") ||
                        Pattern.compile("(.*)  (.*)").matcher(name).matches()) {
                    throw new Exception((name.equals("") ? "This" : name) + " is not a valid name!");
                }
                break;
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
