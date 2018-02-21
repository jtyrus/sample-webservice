package cb_service.sample.dao;

import cb_service.sample.models.Member;
import rx.Observable;

public interface MemberCache {

	public Observable<Member> getMember(Long memberId);
	public void cacheMember(Member member);
	
}
