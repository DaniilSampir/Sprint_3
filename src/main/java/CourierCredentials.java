import io.qameta.allure.Step;

public class CourierCredentials {
    @Step ("Create credentials")
    public String credentials(String login, String password) {
        return "{\"login\":\"" + login + "\","
                + "\"password\":\"" + password +"\"}";
    }
}
