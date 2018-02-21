package cb_service.sample.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;

import cb_service.sample.MemberUtils;
import cb_service.sample.models.Member;
import cb_service.sample.models.NameRef;
import rx.Observable;

public class MemberDAOImpl implements MemberDAO {
	
	@Value("${couchbase.host}")
	private String couchbaseHost;
	
	@Value("${couchbase.bucket}")
	private String couchbaseBucket;
	
	@Autowired
	private MemberUtils utils;
	
	public AsyncBucket dao;
	
	@Override
	public void startDao() {
		Cluster cluster = CouchbaseCluster.create(couchbaseHost);
        dao = cluster.openBucket(couchbaseBucket).async();
	}
	
	@Override
	public Observable<Member> createMember(Member member) {
		
		NameRef memberRef = new NameRef();
		memberRef.setName(member.getName());
		memberRef.setMemberId(member.getMemberId());
		
		String docKey = Long.toString(member.getMemberId());
		// Will fail if name already exists
		return dao.insert(JsonDocument.create(docKey, utils.objectToJsonObject(memberRef)))
					.flatMap(notUsed -> dao.insert(JsonDocument.create(docKey, utils.objectToJsonObject(member))))
					.map(doc -> utils.jsonDocumentToObject(doc, Member.class));
			
	}

	@Override
	public Observable<Member> getMember(String memberId) {
		return dao.get(memberId)
					.map(doc -> utils.jsonDocumentToObject(doc, Member.class));
	}
	
	@Override
	public Observable<String> getMemberRef(String name) {
		return dao.get(name)
					.map(doc -> doc.content().getString("memberId"));
	}

	@Override
	public Observable<Member> removeMember(String memberId) {
		
		return dao.remove(memberId)
					.map(doc -> utils.jsonDocumentToObject(doc, Member.class));
		
	}

	@Override
	public Observable<Member> updateMember(Member member) {
		
		String docKey = Long.toString(member.getMemberId());
		return dao.replace(JsonDocument.create(docKey, utils.objectToJsonObject(member)))
					.map(doc -> utils.jsonDocumentToObject(doc, Member.class));
		
	}

	
}
