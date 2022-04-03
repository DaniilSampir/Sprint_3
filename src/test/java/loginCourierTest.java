
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class loginCourierTest {
    private CourierClient courierClient;
    private int courierID;
    private CourierCredentials courierCredentials = new CourierCredentials();
    private static final String COURIER_LOGIN_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/courier/login/";

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void deleteCourier(){
        if (courierID > 0) {
            courierClient.delete(courierID);
        }
    }
    @Test
    public void loginCourierShouldAuthorization(){
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,courier.password))
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .assertThat()
                .statusCode(200);
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
    }
    @Test
    public void loginCourierForAuthorizationNeedAllFieldsReturnErrorCode(){
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        String password = courier.password;
        password = "";
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,password))
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .assertThat()
                .statusCode(400);
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
    }
    @Test
    public void loginCourierBadRequestShouldReturnErrorCode() {
        Courier courier = Courier.getRandom();
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,courier.password))
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void loginCourierForAuthorizationNeedAllFieldsReturnErrorMessage(){
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        String password = courier.password;
        password = "";
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,password))
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для входа"));
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
    }
    @Test
    public void loginCourierBadRequestShouldReturnErrorMessage() {
        Courier courier = Courier.getRandom();
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login, courier.password))
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    public void loginCourierShouldAuthorizationShouldReturnID(){
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,courier.password))
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(courierID));
    }
}
