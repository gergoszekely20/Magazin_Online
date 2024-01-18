package BusinessLogic.Validators;

import Model.Client;
import Model.Product;

import java.util.regex.Pattern;

public class PriceValidator implements Validator<Product> {
    private static final String PRICE_PATTERN = "\\d+(\\.(\\d+))?";

    @Override
    public void validate(Product p) throws Exception {
        Pattern pattern = Pattern.compile(PRICE_PATTERN);
        String price = "" + p.getPrice();
        if (!pattern.matcher(price).matches() || price.equals("")) {
            throw new Exception((price.equals("") ? "This" : price) + " is not a valid price!");
        }
    }
}
