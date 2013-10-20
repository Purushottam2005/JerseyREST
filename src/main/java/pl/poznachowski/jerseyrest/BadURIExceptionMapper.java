package pl.poznachowski.jerseyrest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.sun.jersey.api.NotFoundException;

public class BadURIExceptionMapper implements ExceptionMapper<NotFoundException> {

		public Response toResponse(NotFoundException exception){
		    return Response.status(Response.Status.BAD_REQUEST).entity("Unknown resource: " + exception.getNotFoundUri().toString()).build();
		}
}
