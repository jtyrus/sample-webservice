package cb_service.sample.dao;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.util.LRUCache;

import cb_service.sample.models.Member;
import rx.Observable;

@Repository
public class MemberCacheImpl implements MemberCache {

	@Value("${cache.max.capacity}")
	private int maxCapacity;
	
	LRUCache<Long, Member> members;	
	
	@PostConstruct
	private void setUpCache() {
		
		members = new LRUCache<>(maxCapacity);
		
	}
	
	@Scheduled(fixedRate = 10000)
	private void cleanCache() {
		
		 members.clear();
		 
	}
	
	@Override
	public Observable<Member> getMember(Long memberId) {
		
		return Observable.just(members.get(memberId));
		
	}

	@Override
	public void cacheMember(Member member) {
		
		members.put(member.getMemberId(), member);
		
	}
	
}
