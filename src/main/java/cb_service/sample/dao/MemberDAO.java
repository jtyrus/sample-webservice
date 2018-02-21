package cb_service.sample.dao;

import cb_service.sample.models.Member;
import rx.Observable;

public interface MemberDAO {

	public void startDao();
	public Observable<Member> createMember(Member member);
	public Observable<Member> getMember(String memberId);
	public Observable<String> getMemberRef(String name);
	public Observable<Member> removeMember(String memberId);
	public Observable<Member> updateMember(Member member);
	
}
