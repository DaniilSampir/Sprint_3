
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class CourierClient {
   private static final String contentType = "Content-type";
   private static final String json = "application/json";
   private static final String COURIER_PATH ="https://qa-scooter.praktikum-services.ru/api/v1/courier/";

    public boolean create(Courier courier){
         return given()
                .header(contentType, json)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                 .then()
                 .assertThat()
                 .statusCode(201)
                 .extract()
                 .path("ok");
    }

    public int login(String courierPartData){
        return  given()
                .header(contentType, json)
                .body(courierPartData)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public boolean delete(int id){
        return given()
                .header(contentType, json)
                .when()
                .delete(COURIER_PATH + id)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

}
