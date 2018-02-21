package cb_service.sample.service.controller;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/service/members")
public interface RestService {

	
	@GET
	public Response getMember(
			@HeaderParam("Authorization") String token,
			@QueryParam("memberId") String memberId,
			@QueryParam("name") String name);
	
	@PUT
	public Response updateMember(
			@HeaderParam("Authorization") String token,
			@QueryParam("name") String name,
			@QueryParam("phoneNumber") String phoneNumber,
			@QueryParam("email") String email);
	
	@DELETE
	public Response removeMember(
			@HeaderParam("Authorization") String token,
			@QueryParam("memberId") String memberId,
			@QueryParam("name") String name);
}
