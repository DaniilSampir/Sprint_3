import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class orderListTest {
    private static final String ORDERS_LIST_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/orders";

    @Test
    public void getOrderListShouldNotNull() {
        given()
                .header("Content-type", "application/json")
                .get(ORDERS_LIST_PATH)
                .then()
                .assertThat()
                .statusCode(200).body("orders",notNullValue());
    }
}
