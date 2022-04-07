
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {
    private CourierClient courierClient;
    private int courierID;
    private CourierCredentials courierCredentials = new CourierCredentials();
    Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
    }

    @After
    public void deleteCourier(){
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
        if (courierID > 0) {
            courierClient.delete(courierID);
        }
    }

    @Test
    @DisplayName("Authorization courier")
    public void loginCourierShouldAuthorization(){
        ValidatableResponse getLoginCourierResponse = courierClient.getLoginCourierResponse(courier.login, courier.password);
        getLoginCourierResponse.assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Authorization courier, without one important field, should return code 400")
    public void loginCourierForAuthorizationNeedAllFieldsReturnErrorCode(){
        ValidatableResponse getLoginCourierResponse = courierClient.getLoginCourierResponse(courier.login, "");
        getLoginCourierResponse.assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Authorization courier, with mistake in field, should return code 404")
    public void loginCourierBadRequestShouldReturnErrorCode() {
        ValidatableResponse getLoginCourierResponse = courierClient.getLoginCourierResponse("ADsd", courier.password);
        getLoginCourierResponse.assertThat().statusCode(404);
    }

    @Test
    @DisplayName("Authorization courier, without one important field, should return error message")
    public void loginCourierForAuthorizationNeedAllFieldsReturnErrorMessage(){
        ValidatableResponse getLoginCourierResponse = courierClient.getLoginCourierResponse(courier.login, "");
        getLoginCourierResponse.statusCode(400);
        getLoginCourierResponse.assertThat().body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Authorization courier, with mistake in field, should return error message")
    public void loginCourierBadRequestShouldReturnErrorMessage() {
        ValidatableResponse getLoginCourierResponse = courierClient.getLoginCourierResponse("Asdsd", courier.password);
        getLoginCourierResponse.statusCode(404);
        getLoginCourierResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Authorization courier, should return right ID")
    public void loginCourierShouldAuthorizationShouldReturnID(){
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
        ValidatableResponse getLoginCourierResponse = courierClient.getLoginCourierResponse(courier.login, courier.password);
        getLoginCourierResponse.statusCode(200);
        getLoginCourierResponse.assertThat().body("id",equalTo(courierID));
    }
}
