package pl.poznachowski.jerseyrest;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static pl.poznachowski.jerseyrest.TestConstants.ACC_ID;
import static pl.poznachowski.jerseyrest.TestConstants.GET;
import static pl.poznachowski.jerseyrest.TestConstants.GOOD_PATH;
import static pl.poznachowski.jerseyrest.TestConstants.HTTP_METHOD;
import static pl.poznachowski.jerseyrest.TestConstants.HTTP_STATUS;
import static pl.poznachowski.jerseyrest.TestConstants.PATH_PREFIX;
import static pl.poznachowski.jerseyrest.TestConstants.POST;
import static pl.poznachowski.jerseyrest.TestConstants.SOME_PAYLOAD;
import static pl.poznachowski.jerseyrest.TestConstants.USER_ID;

import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.google.common.collect.ImmutableMap;

@RunWith(JUnitParamsRunner.class)
public class JerseyRESTTest extends FunctionalTestCase {

	@Override
	protected String getConfigResources() {
		return "JerseyRest.xml";
	}

	@Test
	@Parameters(method = "badURLs")
	public void shouldReturn400StatusFor_GoodMethod_BadURL(Map<String, String> properties, String path) throws Exception {

		MuleClient client = new MuleClient(muleContext);
		MuleMessage result = client.send(path, SOME_PAYLOAD, properties);

		assertThat(result, is(notNullValue()));
		assertThat(Integer.valueOf(result.getInboundProperty(HTTP_STATUS).toString()), is(400));
		assertThat(result.getPayloadAsString(), is(notNullValue()));
		assertTrue(result.getPayloadAsString().contains("Unknown resource"));
	}

	@SuppressWarnings("unused")
	private Object[] badURLs() {
		return $($(ImmutableMap.of(HTTP_METHOD, POST), PATH_PREFIX + "/client_s/123/456/get"),
				$(ImmutableMap.of(HTTP_METHOD, POST), PATH_PREFIX + "/client/123/456"),
				$(ImmutableMap.of(HTTP_METHOD, GET), PATH_PREFIX + "/client/123/456/"),
				$(ImmutableMap.of(HTTP_METHOD, GET), PATH_PREFIX + "/client/123/ /"),
				$(ImmutableMap.of(HTTP_METHOD, GET), PATH_PREFIX + "/123/456/client/get"),
				$(ImmutableMap.of(HTTP_METHOD, GET), PATH_PREFIX + "/client//456/get"));
	}

	@Test
	@Parameters(method = "badMethods")
	public void shouldReturn405StatusFor_GoodURL_BadMethod(Map<String, Object> properties, String path) throws Exception {

		MuleClient client = new MuleClient(muleContext);
		MuleMessage result = client.send(path, SOME_PAYLOAD, properties);

		assertThat(result, is(notNullValue()));
		assertThat(Integer.valueOf(result.getInboundProperty(HTTP_STATUS).toString()), is(405));
	}

	@SuppressWarnings("unused")
	private Object[] badMethods() {
		return $($(ImmutableMap.of(HTTP_METHOD, "PUT"), PATH_PREFIX + GOOD_PATH),
				$(ImmutableMap.of(HTTP_METHOD, "DELETE"), PATH_PREFIX + GOOD_PATH),
				$(ImmutableMap.of(HTTP_METHOD, "PATCH"), PATH_PREFIX + GOOD_PATH));
	}
	
	@Test
	@Parameters({ "GET", "POST" })
	public void shouldProcessAndReturnProperPayload(String httpMethod) throws Exception {

		MuleClient client = new MuleClient(muleContext);
		MuleMessage result = client.send(PATH_PREFIX + GOOD_PATH, SOME_PAYLOAD, ImmutableMap.<String,String>of(HTTP_METHOD, httpMethod));

		assertThat(result, is(notNullValue()));
		assertThat(Integer.valueOf(result.getInboundProperty(HTTP_STATUS).toString()), is(200));
		assertThat(result.getPayloadAsString(), is(notNullValue()));
		
		assertTrue(result.getPayloadAsString().contains("Processing " + httpMethod));
		assertTrue(result.getPayloadAsString().contains(ACC_ID));
		assertTrue(result.getPayloadAsString().contains(USER_ID));
	}

}
