import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.hamcrest.Matchers.notNullValue;

public class CreatingOrderTest {
    private ArrayList<OrderCard> cards;
    OrderCard orderCard;

    @Before
    public void setUp(){
        cards = new ArrayList<OrderCard>();
        orderCard = new OrderCard();
    }

    @Test
    @DisplayName("Creating order, with color parameter Black, should return code 201")
    public void creatingOrderColorBlackShouldReturnCode201(){
        cards.add(new OrderCard("BLACK"));
        ValidatableResponse createOrderResponse = orderCard.getCreateOrderResponse(cards);
        createOrderResponse.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Creating order, with color parameter Grey, should return code 201")
    public void creatingOrderColorGreyShouldReturnCode201(){
        cards.add(new OrderCard("GREY"));
        ValidatableResponse createOrderResponse = orderCard.getCreateOrderResponse(cards);
        createOrderResponse.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Creating order, with color parameters Black and Grey, should return code 201")
    public void creatingOrderColorGreyAndBlackShouldReturnCode201(){
        cards.add(new OrderCard("GREY"));
        cards.add((new OrderCard("BLACK")));
        ValidatableResponse createOrderResponse = orderCard.getCreateOrderResponse(cards);
        createOrderResponse.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Creating order, without color parameter, should return code 201")
    public void creatingOrderNoColorShouldReturnCode201(){
        cards.add(new OrderCard(""));
        ValidatableResponse createOrderResponse = orderCard.getCreateOrderResponse(cards);
        createOrderResponse.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Creating order, should return track")
    public void creatingOrderResponseShouldHaveTrack(){
        ValidatableResponse createOrderResponse = orderCard.getCreateOrderResponse(cards);
        createOrderResponse.assertThat().statusCode(201).body("track",notNullValue());
    }
}
