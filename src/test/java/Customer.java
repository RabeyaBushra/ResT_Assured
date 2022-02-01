import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Customer {
    Properties pros= new Properties();;
    FileInputStream file=new FileInputStream("./src/test/resources/config.properties");;

    public Customer() throws FileNotFoundException {
    }

    public void CallingCustomerLoginInApi() throws ConfigurationException, IOException {

        pros.load(file);
        RestAssured.baseURI=pros.getProperty("baseURI");
        Response res =
                given()
                            .contentType("application/json")
                            .body("{\n" +
                                    "\"username\":\"salman\",\n" +
                                    "\"password\":\"salman1234\"\n" +
                                    "}")
                .when()
                            .post("/customer/api/v1/login")
                .then()
                            .assertThat().statusCode(200).extract().response();


        JsonPath resObj = res.jsonPath();
        String token = resObj.get("token");
        Utils.setEnvVariable("token",token);
        System.out.println("token: " + token);
    }


    public void callingCustomerListApi() throws IOException {

        pros.load(file);
        RestAssured.baseURI=pros.getProperty("baseURI");
        Response response =
                given()
                             .contentType("application/json")
                              .header("Authorization",pros.getProperty("token"))
                .when()
                              .get("/customer/api/v1/list")
                .then()
                               .assertThat().statusCode(200).extract().response();




        System.out.println( response.asString());
        JsonPath jsonObj = response.jsonPath();
        String id =jsonObj.get("Customers[0].id").toString();
        System.out.println( id);
        Assert.assertEquals("101",id);



    }

    public void callingSearchApi() throws IOException {

        pros.load(file);
        RestAssured.baseURI=pros.getProperty("baseURI");
        Response response =
                given()
                                   .contentType("application/json")
                                   .header("Authorization",pros.getProperty("token"))
                .when()
                                   .get("/customer/api/v1/get/102")
                .then()
                                    .assertThat().statusCode(200).extract().response();





        JsonPath jsonObj = response.jsonPath();
        String name =jsonObj.get("name");

        Assert.assertEquals("Mr. Jamal Islam",name);
        System.out.println( response.asString());



    }

    public int Id;
    public String Name;
    public String Email;
    public String Address;
    public String Phone_Number;


    public void GenerateCustomer() throws IOException, ConfigurationException {

        pros.load(file);
        RestAssured.baseURI="https://randomuser.me";
        Response response =
                given()
                        .contentType("application/json")
                        /*.header("Authorization",pros.getProperty("token"))*/
                        .when()
                        .get("/api")
                        .then()
                        .assertThat().statusCode(200).extract().response();





        JsonPath responseObj = response.jsonPath();
        Id =(int)Math.floor(Math.random() * (999999-100000)+1);
        Name = responseObj.get("results[0].name.first");
        Email = responseObj.get("results[0].email");
        Address = responseObj.get("results[0].location.state");
        Phone_Number = responseObj.get("results[0].cell");
        Utils.setEnvVariable("Id", String.valueOf(Id));
        Utils.setEnvVariable("Name",Name);
        Utils.setEnvVariable("Email",Email);
        Utils.setEnvVariable("Address",Address);
        Utils.setEnvVariable("Phone_Number",Phone_Number);

        System.out.println( response.asString());




    }

    public void CreateCustomer() throws IOException, ConfigurationException
    {
        pros.load(file);
        RestAssured.baseURI=pros.getProperty("baseURI");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization",pros.getProperty("token"))
                        .body("" +
                                "{\"id\":"+ pros.getProperty("Id") +",\n" +
                                "   \"name\":\""+ pros.getProperty("Name") +"\" ,\n" +
                                "   \"email\":\""+pros.getProperty("Email")+"\",\n" +
                                "   \"address\":\""+pros.getProperty("Address")+"\", \n" +
                                "    \"phone_number\":\""+pros.getProperty("Phone_Number")+ "\"}")
                        .when()
                        .post("/customer/api/v1/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        System.out.println( res.asString());

    }
    public void UpdateCustomer() throws IOException, ConfigurationException
    {
        pros.load(file);
        RestAssured.baseURI=pros.getProperty("baseURI");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization",pros.getProperty("token"))
                        .body("" +
                                "{\"id\":"+"101"+",\n" +
                                "   \"name\":\""+ pros.getProperty("Name") +"\" ,\n" +
                                "   \"email\":\""+pros.getProperty("Email")+"\",\n" +
                                "   \"address\":\""+pros.getProperty("Address")+"\", \n" +
                                "    \"phone_number\":\""+pros.getProperty("Phone_Number")+ "\"}")
                        .when()
                        .put("/customer/api/v1/update/101")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println( res.asString());

    }

    public void DeletedCustomer() throws IOException, ConfigurationException
    {
        pros.load(file);
        RestAssured.baseURI=pros.getProperty("baseURI");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization",pros.getProperty("token"))
                        .when()
                        .delete("/customer/api/v1/delete/"+pros.getProperty("Id")+"")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println( res.asString());

    }
}
