import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CaseValidationRestAssuredTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://lucent-trifle-ba3d62.netlify.app";
        RestAssured.basePath = "/.netlify/functions/checkcase";
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    private String loadJsonFromClasspath(String filename) throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader().getResource(filename).toURI());
        return Files.readString(path);
    }

    @Test
    public void testValidCaseShouldReturn200() throws IOException, URISyntaxException {
        String jsonBody = loadJsonFromClasspath("Case1.json");

        given()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("message", equalTo("Case valid"));
    }

    @Test
    public void getHeaders(){
        String endpoint = "https://lucent-trifle-ba3d62.netlify.app/.netlify/functions/checkcase";
        given().
        when().
                get(endpoint).
        then().
                log().
                headers();

    }
//    @Test
//    public void testInvalidPatientIdShouldReturn422() throws IOException, URISyntaxException {
//        String jsonBody = loadJsonFromClasspath("Case_invalid_patient_id.json");
//
//        given()
//                .header("Accept", "application/json")
//                .contentType(ContentType.JSON)
//                .body(jsonBody)
//                .when()
//                .post()
//                .then()
//                .statusCode(422);
//    }
}
