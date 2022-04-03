import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;


public class creationCourierTest {
    private CourierClient courierClient;
    private int courierID;
    private CourierCredentials courierCredentials = new CourierCredentials();
    private static final String COURIER_CREATION_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/courier/";

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
    public void creatingCourierShouldCreatedReturnTrue() {
        Courier courier = Courier.getRandom();
        boolean isCreated = courierClient.create(courier);
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
        Assert.assertTrue(isCreated);
    }

    @Test
    public void creatingTwoIdenticalCouriersCannotCreatedShouldReturnCode409(){
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATION_PATH)
                .then()
                .assertThat()
                .statusCode(409);
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));

    }

    @Test
    public void creatingCourierWithoutRequiredFieldCreationErrorShouldReturnCode400(){
        Courier courier = Courier.getRandom();
        String password = "";
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,password))
                .when()
                .post(COURIER_CREATION_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void creatingCourierRequestShouldReturnsСode201(){
        Courier courier = Courier.getRandom();
        given()
        .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATION_PATH)
                .then()
                .assertThat()
                .statusCode(201);
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
    }

    @Test
    public void creatingCourierRequestShouldReturnCorrectText(){
        Courier courier = Courier.getRandom();
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_CREATION_PATH)
                .then()
                .statusCode(201)
                .assertThat().body("ok",equalTo(true));
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
    }

    @Test
    public void creatingCourierRequestShouldReturnCorrectErrorText(){
        Courier courier = Courier.getRandom();
        String password = "";
        given()
                .header("Content-type", "application/json")
                .body(courierCredentials.credentials(courier.login,password))
                .when()
                .post(COURIER_CREATION_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void creatingDoubleCourierRequestShouldReturnCorrectResponseErrorText(){
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(COURIER_CREATION_PATH)
                .then()
                .assertThat()
                .statusCode(409)
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
        courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
    }
}
