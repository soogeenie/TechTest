import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CaseValidationTest_Temp {

    @Test
    public void testValidCaseShouldReturn200() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("Case1.json")).toURI());
        String jsonBody = Files.readString(path);
//        String validCaseJson = """
//        {
//          "case_id": "a474e3e6-89ad-4bb9-be00-cba347e2a001",
//          "patient_id": "1234567^1^ISO^NN123^MC",
//          "patient_name": "Smith^John",
//          "dob": "19700401",
//          "tissue_type": "prostate"
//        }
//        """;

        RestAssured.baseURI = "https://lucent-trifle-ba3d62.netlify.app/";
        RestAssured.basePath = ".netlify/functions/checkcase";

        given().
                contentType(ContentType.JSON).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(jsonBody).
        when().
                post("").
        then().
                statusCode(200).
                body("message", equalTo("Case valid"));
    }
}
