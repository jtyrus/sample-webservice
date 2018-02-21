package cb_service.sample.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cb_service.sample.dao.MemberCache;
import cb_service.sample.dao.MemberDAO;
import cb_service.sample.models.Member;
import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

	@InjectMocks
	MemberServiceImpl service;
	
	@Mock
	MemberDAO dao;
	
	@Mock
	MemberCache cache;
	
	static Member member1;
	
	@BeforeClass
	public static void setUp() {
		
		member1 = new Member(12345, "TestMember1", "123-456-7891", "testmember1@tripp.com");
		
	}
	
	@Test
	public void getMemberByIdDAOTest() {
		
		when(cache.getMember(12345L)).thenReturn(Observable.empty());
		when(dao.getMember("12345")).thenReturn(Observable.just(member1));
		
		Member found = service.getMember("12345", null);
		assertEquals("TestMember1", found.getName());
		
	}
	
	@Test
	public void getMemberByIdCacheTest() {
		
		when(cache.getMember(12345L)).thenReturn(Observable.just(member1));
		when(dao.getMember("12345")).thenReturn(Observable.error(new Exception()));
		
		Member found = service.getMember("12345", null);
		assertEquals("TestMember1", found.getName());
		
	}
	
	@Test
	public void getMemberByNameTest() {
		
		when(cache.getMember(any())).thenReturn(Observable.error(new Exception()));
		when(dao.getMember("12345")).thenReturn(Observable.just(member1));
		when(dao.getMemberRef("TestMember1")).thenReturn(Observable.just("12345"));
		
		Member found = service.getMember(null, "TestMember1");
		assertEquals("TestMember1", found.getName());
		
	}
}
