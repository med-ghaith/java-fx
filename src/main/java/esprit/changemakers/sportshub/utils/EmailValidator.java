package esprit.changemakers.sportshub.utils;


import java.util.function.Predicate;
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        boolean ok = email.matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");
                return ok;
    }
}
