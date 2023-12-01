package BusinessLogic.Validators;

import Model.Client;

import java.util.regex.Pattern;

public class EmailValidator implements Validator<Client>{
    private static final String EMAIL_PATTERN = "[a-z]+[0-9a-z]+@[a-z]+\\.[a-z]+";

    @Override
    public void validate(Client c) throws Exception {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(c.getEmail()).matches() || c.getEmail().equals("")) {
            throw new Exception((c.getEmail().equals("") ? "This" : c.getEmail()) + " is not a valid email!");
        }
    }
}
