package BusinessLogic.Validators;

import Model.Client;

import java.util.regex.Pattern;

public class PhoneValidator implements Validator<Client>{
    private static final String PHONE_PATTERN = "[+]?\\d+";

    @Override
    public void validate(Client c) throws Exception {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (!pattern.matcher(c.getPhone()).matches() || c.getPhone().equals("")) {
            throw new Exception((c.getPhone().equals("") ? "This" : c.getPhone()) + " is not a valid phone number!");
        }
    }
}
