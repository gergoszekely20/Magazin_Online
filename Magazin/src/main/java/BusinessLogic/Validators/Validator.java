package BusinessLogic.Validators;

/**
 * Interfata Validator defineste o metoda prin intermediul
 * careia se verifica datele introduse de utilizator.
 * <p>
 * Fiecare clasa care implementeaza aceasta interfata va rescrie metoda
 * si o modifica in functie de datele care trebuie validate.
 * @param <T> Obiectul care trebuie validat
 * @author Denis Puscas
 * @since 18-05-2023
 */
public interface Validator<T> {
    public void validate(T t) throws Exception;
}
