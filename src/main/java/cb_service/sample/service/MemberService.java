package cb_service.sample.service;

import cb_service.sample.models.Member;

public interface MemberService {
	
	public Member getMember(String memberId, String name);
	public Member removeMember(String memberId, String name);
	public Member updateMember(Member member);
	
}
