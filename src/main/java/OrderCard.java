import java.util.ArrayList;

public class OrderCard {
    private String color;

    public OrderCard(){

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public OrderCard(String color) {
        this.color = color;
    }
    public String orderCredentials(ArrayList<OrderCard> color){
       return "{\"firstName\":\"" + "English" + "\","
                + "\"lastName\":\"" + "Man" +"\","
    + "\"addres\":\"" + "London, Elisabeth str." +"\","
    + "\"metroStation\":\""+ 5 + "\","
    + "\"phone\":\"" + "+7 800 355 35 35" + "\","
    + "\"rentTime\":\"" + 6 + "\","
    + "\"deliveryDate\":\"" + "2020-06-06" + "\","
    + "\"comment\":\"" + "pogoooooonim" +"\","
    + "\"comment\":\"" + color + "\"}";

    }
}
