public class CourierCredentials {
    public String credentials(String login, String password) {
        return "{\"login\":\"" + login + "\","
                + "\"password\":\"" + password +"\"}";
    }
}
