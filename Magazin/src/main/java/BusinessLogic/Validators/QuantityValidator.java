package BusinessLogic.Validators;

import Model.Product;

import java.util.regex.Pattern;

public class QuantityValidator implements Validator<Product>{
    private static final String QUANTITY_PATTERN = "\\d+(\\.(\\d+))?";

    @Override
    public void validate(Product p) throws Exception {
        System.out.println("aici");
        Pattern pattern = Pattern.compile(QUANTITY_PATTERN);
        String quantity = "" + p.getQuantity();
        if (!pattern.matcher(quantity).matches() || quantity.equals("")) {
            throw new Exception((quantity.equals("") ? "This" : quantity) + " is not a valid quantity!");
        }
    }
}
