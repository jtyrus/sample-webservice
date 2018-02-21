package cb_service.sample.service.controller;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import cb_service.sample.MemberUtils;
import cb_service.sample.models.Member;
import cb_service.sample.service.MemberService;

import io.jsonwebtoken.Jwts;

@Controller
public class RestServiceImpl implements RestService {

	@Autowired
	MemberService service;
	
	@Autowired
	MemberUtils utils;
	
	@Value("${jwt.secret}")
	private String SECRET;
	
	private static final String ADMIN = "admin";
	private static final String USER = "user";
	
	
	@Override
	public Response getMember(String token, String memberId, String name) {

		try {
			if (!verify(token, USER))
				return Response.status(403).build();
			Member responseObj = service.removeMember(memberId, name);
			return Response.ok(responseObj).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
		
	}

	@Override
	public Response updateMember(String token, String name, String phoneNumber, String email) {
		
		
		try {
			if (!verify(token, USER))
				return Response.status(403).build();
			Member member = new Member(utils.getMemberId(name), name, phoneNumber, email);
			Member responseObj = service.updateMember(member);
			return Response.ok(responseObj).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
		
	}

	@Override
	public Response removeMember(String token, String memberId, String name) {

		try {
			if (!verify(token, ADMIN))
				return Response.status(403).build();
			Member responseObj = service.removeMember(memberId, name);
			return Response.ok(responseObj).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}
		
	}
	
	private boolean verify(String token, String type) {
		if (token == null || token.isEmpty())
			return false;
		String userType = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
		
		if (userType.equalsIgnoreCase(ADMIN))
			return true;
		else
			return userType.equalsIgnoreCase(type);
		
	}

}
