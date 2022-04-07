import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    OrderCard card = new OrderCard();

    @Test
    @DisplayName("Get order list, list should be not null")
    public void getOrderListShouldNotNull() {
                card.getOrderListResponse().statusCode(200);
                card.getOrderListResponse().assertThat().body("orders",notNullValue());
    }
}
