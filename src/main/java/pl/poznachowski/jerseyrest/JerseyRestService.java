package pl.poznachowski.jerseyrest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path(value = "/")
public class JerseyRestService {

	private FlowProcessing flowProcessing;

	@GET
	@Path(value = "/client/{accountID}/{userID}/get")
	public Response processGET(@PathParam("accountID") String accountId, @PathParam("userID") String userId) {
		String result = flowProcessing.processGET(accountId, userId);
		return Response.ok(result).build();
	}

	@POST
	@Path(value = "/client/{accountID}/{userID}/get")
	public Response processPOST(@PathParam("accountID") String accountId, @PathParam("userID") String userId, String body) {
		String result = flowProcessing.processPOST(accountId, userId, body);
		return Response.ok(result).build();
	}

	public void setFlowProcessing(FlowProcessing flowProcessing) {
		this.flowProcessing = flowProcessing;
	}
}
