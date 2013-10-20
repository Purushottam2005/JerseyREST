package pl.poznachowski.jerseyrest;

public interface TestConstants {

	String POST = "POST";
	String GET = "GET";
	String SOME_PAYLOAD = "Payload";
	String JERSEY_REST_FLOW = "JerseyRestFlow";
	String ACC_ID = "123";
	String USER_ID = "456";

	String PATH_PREFIX = "http://localhost:8080";
	String GOOD_PATH = "/client/" + ACC_ID + "/" + USER_ID + "/get";
	String HTTP_PATH = "http.request";
	String HTTP_METHOD = "http.method";
	String HTTP_STATUS = "http.status";
	String HTTP_CONTEXT_PATH = "http.context.path";
}
