package io.quarkus.perf;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;

import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class ExampleResourceTest {


    //    JDBC
    @Test
    public void testJdbcAllDataEndpoint() {
        given()
                .when().get("/jdbc")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(containsString("Bar"));
    }


    @Test
    public void testJdbcFindFoo() {
        given()
                .when().get("/jdbc/Foo")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(not(containsString("Bar")));
    }

    @Test
    public void testJdbcFindBar() {
        given()
                .when().get("/jdbc/Bar")
                .then()
                .statusCode(200)
                .body(not(containsString("Foo")))
                .body(containsString("Bar"));
    }

    //JPA
    @Test
    public void testJpaAllDataEndpoint() {
        given()
                .when().get("/jpa")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(containsString("Bar"));
    }


    @Test
    public void testJpaFindFoo() {
        given()
                .when().get("/jpa/Foo")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(not(containsString("Bar")));
    }

    @Test
    public void testJpaFindBar() {
        given()
                .when().get("/jpa/Bar")
                .then()
                .statusCode(200)
                .body(not(containsString("Foo")))
                .body(containsString("Bar"));
    }

    //Panache
    @Test
    public void testPanacheAllDataEndpoint() {
        given()
                .when().get("/panache")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(containsString("Bar"));
    }


    @Test
    public void testPanacheFindFoo() {
        given()
                .when().get("/panache/Foo")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(not(containsString("Bar")));
    }

    @Test
    public void testPanacheFindBar() {
        given()
                .when().get("/panache/Bar")
                .then()
                .statusCode(200)
                .body(not(containsString("Foo")))
                .body(containsString("Bar"));
    }

    //Stateless
    @Test
    public void testStatlessAllDataEndpoint() {
        given()
                .when().get("/stateless")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(containsString("Bar"));
    }


    @Test
    public void testStatlessFindFoo() {
        given()
                .when().get("/stateless/Foo")
                .then()
                .statusCode(200)
                .body(containsString("Foo"))
                .body(not(containsString("Bar")));
    }

    @Test
    public void testStatlessFindBar() {
        given()
                .when().get("/stateless/Bar")
                .then()
                .statusCode(200)
                .body(not(containsString("Foo")))
                .body(containsString("Bar"));
    }

}
