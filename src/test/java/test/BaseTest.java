package test;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import br.sicred.core.Constantes;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseTest implements Constantes {
	
	public Response response;
	public static StringWriter requestWriter;
	public static PrintStream requestCapture;
	public static StringWriter responseWriter;
	public static PrintStream responseCapture;
	public static StringWriter errorWriter;
	public static PrintStream errorCapture;
	private static Logger log = Logger.getLogger(BaseTest.class);

	
	@Rule
	public TestName name = new TestName();
	
	
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.baseURI = BASE_URL_SICRED;
		
	

	}
	
	@Before
	public void eachTest() {

		response = null;
		RestAssured.requestSpecification = defaultRequestSpecification();
		log.info("START -> "+name.getMethodName());

	}
	
	@After
	public void afterTest() {

		log.info(requestWriter.toString());
		log.info(" RESPONSE: \n" + responseWriter);
		log.info("FINISH -> " + name.getMethodName());
	}
	
	@AfterClass
	public static void tearDown() {

		RestAssured.responseSpecification = defaultResponseSpecification();

	}
	
	protected static RequestSpecification defaultRequestSpecification() {

		captureLogPrintStream();

		List<Filter> filters = new ArrayList<>();
		filters.add(new RequestLoggingFilter(requestCapture));
		filters.add(new ResponseLoggingFilter(responseCapture));
		filters.add(new AllureRestAssured());
		filters.add(new ErrorLoggingFilter(errorCapture));

		return new RequestSpecBuilder()
				.addFilters(filters)
				.log(LogDetail.ALL)
				.setContentType(ContentType.JSON)
				.build();

	}
	
	protected static ResponseSpecification defaultResponseSpecification() {

		return new ResponseSpecBuilder()
				.log(LogDetail.ALL)
				.expectResponseTime(Matchers.lessThan(1000l))
				.build();

	}
	
	private static void captureLogPrintStream() {

		requestWriter = new StringWriter();
		requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);

		responseWriter = new StringWriter();
		responseCapture = new PrintStream(new WriterOutputStream(responseWriter), true);

		errorWriter = new StringWriter();
		errorCapture = new PrintStream(new WriterOutputStream(errorWriter), true);
	}
	
	

}
