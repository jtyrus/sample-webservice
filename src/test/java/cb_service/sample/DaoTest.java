package cb_service.sample;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;

import cb_service.sample.dao.MemberDAOImpl;
import cb_service.sample.models.Member;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class DaoTest {

	@InjectMocks
	MemberDAOImpl memberDao;
	
	@Mock
	AsyncBucket async;
	
	@Spy
	MemberUtils utils;
	
	@Before
	public void setUp() {
		memberDao.dao = async;
	}
	
	@Test(expected = DocumentAlreadyExistsException.class)
	public void saveNonUniqueNameTest() {
		
		when(async.insert(any())).thenReturn(Observable.error(new DocumentAlreadyExistsException()));
		memberDao.createMember(new Member(12345, "TestMember1", "123-456-7891", "testmember1@tripp.com")).toBlocking().subscribe();
		assertTrue(false);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void saveUniqueNameTest() {
		
		when(async.insert(any()))
				.thenReturn(Observable.just(JsonDocument.create("just a test")))
				.thenReturn(Observable.just(JsonDocument.create("just a test")));
		
		memberDao.createMember(new Member(12345, "TestMember1", "123-456-7891", "testmember1@tripp.com")).toBlocking().subscribe();
		assertTrue(false);
		
	}
}
