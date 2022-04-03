import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class creationOrderTest {
    private static final String ORDERS_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/orders/";
    private ArrayList<OrderCard> cards;

    @Before
    public void setUp(){
        cards = new ArrayList<OrderCard>();
    }

    @Test
    public void creatingOrderColorBlackShouldReturnCode201(){
        cards.add(new OrderCard("BLACK"));
        OrderCard orderCard = new OrderCard();
        given()
                .header("Content-type", "application/json")
                .body(orderCard.orderCredentials(cards))
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void creatingOrderColorGreyShouldReturnCode201(){
        cards.add(new OrderCard("GREY"));
        OrderCard orderCard = new OrderCard();
        given()
                .header("Content-type", "application/json")
                .body(orderCard.orderCredentials(cards))
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void creatingOrderColorGreyAndBlackShouldReturnCode201(){
        cards.add(new OrderCard("GREY"));
        cards.add((new OrderCard("BLACK")));
        OrderCard orderCard = new OrderCard();
        given()
                .header("Content-type", "application/json")
                .body(orderCard.orderCredentials(cards))
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void creatingOrderNoColorShouldReturnCode201(){
        cards.add(new OrderCard(""));
        OrderCard orderCard = new OrderCard();
        given()
                .header("Content-type", "application/json")
                .body(orderCard.orderCredentials(cards))
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(201);
    }
    @Test
    public void creatingOrderResponseShouldHaveTrack(){
        OrderCard orderCard = new OrderCard();
        given()
                .header("Content-type", "application/json")
                .body(orderCard.orderCredentials(cards))
                .post(ORDERS_PATH)
                .then()
                .assertThat()
                .statusCode(201).body("track",notNullValue());
    }
}
