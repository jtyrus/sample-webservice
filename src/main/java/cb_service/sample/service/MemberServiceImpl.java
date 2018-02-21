package cb_service.sample.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import cb_service.sample.dao.MemberCache;
import cb_service.sample.dao.MemberDAO;
import cb_service.sample.models.Member;
import rx.Observable;

public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO dao;
	
	@Autowired
	MemberCache cache;
	
	@PostConstruct
	public void startDao() {
		dao.startDao();
	}
	
	@Override
	public Member getMember(String memberId, String name) {
		
		Observable<Member> getObservable;
		if (memberId == null || memberId.isEmpty()) {
			getObservable = getByName(name);
		} else {
			getObservable = cache.getMember(Long.parseLong(memberId))
								.switchIfEmpty(getById(memberId));
		}
		
		return getObservable.toBlocking().single();
		
	}

	@Override
	public Member removeMember(String memberId, String name) {
		
		Observable<String> removeObservable;
		if (memberId == null || memberId.isEmpty())
			removeObservable = dao.getMemberRef(name);
		else
			removeObservable = Observable.just(memberId);
		
		return removeObservable.flatMap(dao::removeMember).toBlocking().single();
		
	}

	@Override
	public Member updateMember(Member member) {
		
		return dao.getMember(Long.toString(member.getMemberId()))
				.flatMap(dao::updateMember)
				.switchIfEmpty(dao.createMember(member))
				.toBlocking()
				.single();
		
	}

	private Observable<Member> getByName(String name) {
		
		return dao.getMemberRef(name)
				.flatMap(dao::getMember);
		
	}
	
	private Observable<Member> getById(String memberId) {
		
		return dao.getMember(memberId);
		
	}
}
