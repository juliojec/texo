package br.com.lyncas;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class BooksResourceTest {

    @Test
    @Order(1) 
    public void favoriteEndoint() {
    	given()
    	.header("Authorization", "Basic YWRtaW46MTIzNDU=")
        .when().post("/books/VtCCDwAAQBAJ/favorite")
        .then()
           .statusCode(200)
           .body(is("Livro: Quem pensa enriquece foi adicionado com sucesso a lista de favoritos!"));
    }
    
    @Test
    @Order(2)
    public void whitStarsEndoint() {
    	given()
    	.header("Authorization", "Basic YWRtaW46MTIzNDU=")
        .when().get("/books/whit-stars")
        .then()
           .statusCode(200)
           .body("size()", is(1), "title", hasItem("Quem pensa enriquece"));
    }
    
    @Test
    @Order(3)
    public void removeFavoriteEndoint() {
    	given()
    	.header("Authorization", "Basic YWRtaW46MTIzNDU=")
        .when().delete("/books/VtCCDwAAQBAJ/favorite")
        .then()
           .statusCode(200)
           .body(is("Livro removido com sucesso a lista de favoritos!"));
    }
    
    @Test
    @Order(4)
    public void getBooksEndoint() {
    	given()
    	.header("Authorization", "Basic YWRtaW46MTIzNDU=")
        .when().get("/books/Quem pensa enriquece")
        .then()
           .statusCode(200)
           .body("title", hasItem("Quem pensa enriquece"));
    }

}