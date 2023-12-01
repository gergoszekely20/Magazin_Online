package BusinessLogic.Validators;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class IdValidator<T> implements Validator<T>{
    private static final String ID_PATTERN = "\\d+";


    @Override
    public void validate(T t) throws Exception {
        Pattern pattern = Pattern.compile(ID_PATTERN);
        for (Field field : t.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if (!fieldName.equalsIgnoreCase("id")) continue;
            try {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, t.getClass());
                Method getter = pd.getReadMethod();
                String id = getter.invoke(t).toString();
                if (!pattern.matcher(id).matches() || id.equals("")) {
                    throw new Exception((id.equals("") ? "This" : id) + " is not a valid id!");
                }
                break;
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
