package BusinessLogic.Validators;

import Model.Client;

import java.util.regex.Pattern;

public class AddressValidator implements Validator<Client>{
    private static final String ADDRESS_PATTERN = "[a-zA-Z0-9.,\\- ]+";

    @Override
    public void validate(Client c) throws Exception {
        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
        if (!pattern.matcher(c.getAddress()).matches() || c.getAddress().equals("") ||
                Pattern.compile("(.*)  (.*)").matcher(c.getAddress()).matches()) {
            throw new Exception((c.getAddress().equals("") ? "This" : c.getAddress()) + " is not a valid address!");
        }
    }
}
