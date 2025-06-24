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

    // 1. Content-Type JSON Validation (REQ-1)
    // 1.1 Valid JSON, correct CONTENT-TYPE `JSON`
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

    // 1.2 Valid JSON, correct CONTENT-TYPE `JSON` but blank
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

    // 1.3 Invalid, CONTENT-TYPE 'TEXT'
    @Test
    public void validateContentTypeText() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("1-3.json")).toURI());

        given().
                header("Accept", "text/plain").
                contentType(ContentType.TEXT).
                body(Files.readString(path)).
                when().
                post().
                then().
                assertThat().
                statusCode(415).
                body("message", equalTo("Unsupported media"));
    }

    // 2. Required Fields (REQ-2)
    // 2.1 Missing Field
    // 2.1.1 Missing Field / case_id
    @Test
    public void validateMissingFieldCaseId() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-1-1.json")).toURI());

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

    // 2.1.2 Missing Field / patient_id
    @Test
    public void validateMissingFieldPatientId() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-1-2.json")).toURI());

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

    // 2.1.3 Missing Field / patient_name
    @Test
    public void validateMissingFieldPatientName() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-1-3.json")).toURI());

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

    // 2.1.4 Missing Field / dob
    @Test
    public void validateMissingFieldDob() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-1-4.json")).toURI());

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

    // 2.1.5 Missing Field / tissue_type
    @Test
    public void validateMissingFieldTissueType() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-1-5.json")).toURI());

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

    // 2.2 Blank Field ("")
    // 2.2.1 Blank Field / case_id
    @Test
    public void validateBlankFieldCaseId() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-2-1.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid case_id"));
    }

    // 2.2.2 Blank Field / patient_id
    @Test
    public void validateBlankFieldPatientId() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-2-2.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 2.2.3 Blank Field / patient_name
    @Test
    public void validateBlankFieldPatientName() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-2-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_name"));
    }

    // 2.2.4 Blank Field / dob
    @Test
    public void validateBlankFieldDob() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-2-4.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid dob"));
    }

    // 2.2.5 Blank Field / tissue_type
    @Test
    public void validateBlankFieldTissueType() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-2-5.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid tissue_type"));
    }

    // 2.3 Unexpected Field
    @Test
    public void validateUnexpectedField() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("2-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_title"));
    }

    // 3. Field / Data Format Validations
    // 3.1 case_id (REQ-3)
    // 3.1.1 Valid UUID
    @Test
    public void validateCaseIdValidUUID() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-1-1.json")).toURI());

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

    // 3.1.2 Invalid UUID with special character(s)
    @Test
    public void validateCaseIdInvalidUUID() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-1-2.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid case_id"));
    }

    // 3.1.3 Invalid format UUID
    @Test
    public void validateCaseIdInvalidFormatUUID() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-1-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid case_id"));
    }

    // 3.2 patient_id (REQ-4)
    // 3.2.1 Valid data in all 5 components
    @Test
    public void validatePatientId5Components() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-1.json")).toURI());

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

    // 3.2.2 Missing component
    @Test
    public void validatePatientIdMissingComponent() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-2.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.2.3 Exceeding 15 digits in ID
    @Test
    public void validatePatientIdExceedingDigitInID() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.2.4 Invalid Digit Flag
    @Test
    public void validatePatientIdInvalidDigitFlag() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-4.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.2.5 Invalid Assigning Authority
    @Test
    public void validatePatientIdInvalidAssigningAuth() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-5.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.2.6 Invalid ID Type Code
    @Test
    public void validatePatientIdInvalidIDTypeCode() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-6.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.2.7 Wrong Assigning Facility
    @Test
    public void validatePatientIdWrongAssigningFacility() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-7.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.2.8 Missing delimiter '^'
    @Test
    public void validatePatientIdMissingDelimiter() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-2-8.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_id"));
    }

    // 3.3 patient_name (REQ-5)
    // 3.3.1 Valid data in 2 components
    @Test
    public void validatePatientName2Components() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-3-1.json")).toURI());

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

    // 3.3.2 Valid data in 3 components
    @Test
    public void validatePatientName3Components() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-3-2.json")).toURI());

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

    // 3.3.3 Invalid data in 1 component
    @Test
    public void validatePatientName1Component() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-3-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_name"));
    }

    // 3.3.4 Invalid data in 5 components
    @Test
    public void validatePatientName5Components() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-3-4.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_name"));
    }

    // 3.3.5 Missing delimiter '^'
    @Test
    public void validatePatientNameMissingDelimiter() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-3-5.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid patient_name"));
    }

    // 3.4 dob (REQ-6)
    // 3.4.1 Valid date format
    @Test
    public void validateDobValidDate() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-4-1.json")).toURI());

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

    // 3.4.2 Wrong date format
    @Test
    public void validateDobWrongDateFormat() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-4-2.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid dob"));
    }

    // 3.4.3 Invalid date
    @Test
    public void validateDobInvalidDate() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-4-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid dob"));
    }

    // 3.5 tissue_type (REQ-7)
    // 3.5.1 Valid tissue_type
    @Test
    public void validateTissueTypeValid() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-5-1.json")).toURI());

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

    // 3.5.2 Unacceptable tissue_type
    @Test
    public void validateTissueTypeUnacceptable() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-5-2.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid tissue_type"));
    }

    // 3.5.3 Multiple values of tissue_type
    @Test
    public void validateTissueTypeMultipleValues() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("3-5-3.json")).toURI());

        given().
                header("Accept", "application/json").
                contentType(ContentType.JSON).
                body(Files.readString(path)).
        when().
                post().
        then().
                assertThat().
                statusCode(422).
                body("message", equalTo("Invalid tissue_type"));
    }

    // 4. Valid Input Test (REQ-8)
    // 4.1 Valid Case JSON
    @Test
    public void validateValidCaseJSON() throws IOException, URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("4-1.json")).toURI());

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

}
