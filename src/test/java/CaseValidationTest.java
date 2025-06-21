import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CaseValidationTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://lucent-trifle-ba3d62.netlify.app";
        RestAssured.basePath = "/.netlify/functions/checkcase";
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

//    private String loadJsonFromClasspath() throws IOException, URISyntaxException {
//        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("Case1.json")).toURI());
//        return Files.readString(path);
//    }

    // 1.1 Content-Type JSON Validation (REQ-1)
    @Test
    public void validateContentTypeJSON() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("1-1.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                    statusCode(200).
                    body("message", equalTo("Case valid"));
    }

    // 1.2 Content-Type JSON Validation (REQ-1)
    @Test
    public void validateContentTypeJSONBlank() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("1-2.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                    statusCode(400).
                    body("message", containsString("Missing"));
    }

    // 1.3 Content-Type JSON Validation (REQ-1)
    @Test
    public void validateContentTypeText() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("1-3.xml")).toURI());

        given().
                header("Accept", "text/plain").
                contentType(ContentType.TEXT).
                body(Files.readString(path)).
                when().
                post().
                then().
                assertThat().
                statusCode(415).
                body("message", equalTo("Case invalid"));
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
