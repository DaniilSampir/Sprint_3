import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    public String login;
    public String password;
    public String firstName;

    public Courier(String login, String password, String firstName) {
    this.login = login;
    this.password = password;
    this.firstName = firstName;
    }

    @Step("Creating random courier")
    public static Courier getRandom(){
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        Allure.attachment("Логин: ", login);
        Allure.attachment("Пароль: ", password);
        Allure.attachment("Имя: ", firstName);
        return new Courier(login,password,firstName);
    }
}
