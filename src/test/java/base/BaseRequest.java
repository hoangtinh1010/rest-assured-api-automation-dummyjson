package base;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class BaseRequest {
    private static RequestSpecification noAuthSpec;
    public static RequestSpecification getNoAuth() {
        noAuthSpec = new RequestSpecBuilder()
                .setContentType("application/json")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
        return noAuthSpec;
    }

}
