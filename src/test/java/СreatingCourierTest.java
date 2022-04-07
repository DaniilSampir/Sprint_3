import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;


public class СreatingCourierTest {
    private CourierClient courierClient;
    private int courierID;
    private CourierCredentials courierCredentials = new CourierCredentials();
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

     @After
     public void deleteCourier(){
        if (courier.login.length() != 0 && courier.password.length() != 0){
         courierID = courierClient.login(courierCredentials.credentials(courier.login,courier.password));
        courierClient.delete(courierID);
        }
    }

    @Test
    @DisplayName("Creating courier")
    public void creatingCourierShouldCreatedReturnTrue() {
        boolean isCreated = courierClient.create(courier);
        Assert.assertTrue(isCreated);
    }

    @Test
    @DisplayName("Creating two identical couriers, should return code 409")
    public void creatingTwoIdenticalCouriersCannotCreatedShouldReturnCode409(){
        courierClient.create(courier);
        ValidatableResponse createCourierResponse = courierClient.getCreateCourierResponse(courier);
        createCourierResponse.assertThat();
        createCourierResponse.statusCode(409);
    }

    @Test
    @DisplayName("Создание курьера без одного из параметров, должен вернуться код 400")
    public void creatingCourierWithoutRequiredFieldCreationErrorShouldReturnCode400(){
        courier.password = "";
        ValidatableResponse createCourierResponse = courierClient.getCreateCourierResponse(courier);
        createCourierResponse.assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера, должен вернуться код 201")
    public void creatingCourierRequestShouldReturnsСode201(){
        ValidatableResponse createCourierResponse = courierClient.getCreateCourierResponse(courier);
        createCourierResponse.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Creating courier, should return ok:true")
    public void creatingCourierRequestShouldReturnCorrectText(){
        ValidatableResponse createCourierResponse = courierClient.getCreateCourierResponse(courier);
        createCourierResponse.statusCode(201);
        createCourierResponse.assertThat().body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Creating courier without parameter, should return error message")
    public void creatingCourierRequestShouldReturnCorrectErrorText(){
        courier.password = "";
        ValidatableResponse createCourierResponse = courierClient.getCreateCourierResponse(courier);
        createCourierResponse.statusCode(400);
        createCourierResponse.assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating two identical couriers, should return error message")
    public void creatingDoubleCourierRequestShouldReturnCorrectResponseErrorText(){
        courierClient.create(courier);
        ValidatableResponse createCourierResponse = courierClient.getCreateCourierResponse(courier);
        createCourierResponse.statusCode(409);
        createCourierResponse.assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
