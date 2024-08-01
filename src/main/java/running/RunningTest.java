package running;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import model.domain.entity.Member;
import model.domain.entity.Product;
import model.domain.entity.Subscribe;
import util.DBUtil;

public class RunningTest {

//	@Test
	public void AllMember() {
		EntityManager em = DBUtil.getEntityManager();

		List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
		

		for(Member m : members) {
			System.out.println(m);

//		try {
//			em = DBUtil.getEntityManager();
//			tx = em.getTransaction();
//			tx.begin();
//			
//			tx.commit();
//			
//		}catch(Exception e) {
//			tx.rollback();
//			e.printStackTrace();
//		}finally {
//			if(em != null) {
//				em.close();
//				em = null;
//			}
//
//		}
		
		em.close();
		em = null;
		}
	}
//	@Test
	public void AllProduct() {
		EntityManager em = DBUtil.getEntityManager();

		List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
		
		for(Product p : products) {
			System.out.println(p);
		}
		
		em.close();
		em = null;
	}
	
	//@Test
	public void AllSubscribe() {
		EntityManager em = DBUtil.getEntityManager();

		List<Subscribe> subscribes = em.createQuery("SELECT s FROM Subscribe s", Subscribe.class).getResultList();
		
		for(Subscribe s : subscribes) {
			System.out.println(s);
		}
		
		em.close();
		em = null;
	}
	
	@Test
	public void findTest() {
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try {
			em = DBUtil.getEntityManager();
			tx = em.getTransaction();
			
			// Select: 첫 번째 구독 플랜의 유저 정보 조회
			Subscribe s = em.find(Subscribe.class, 1);
			System.out.println(s.getSubId() + " " + 
							   s.getMemId().getMemName() + " " + 
							   s.getProdId().getProdName() + " " + 
							   s.getProdId().getProdPrice());
			
			tx.begin();
			
			// Update: 첫 번째 구독 플랜의 상품 정보 변경
			Product p = em.find(Product.class, 2);
			s.setProdId(p);
			tx.commit();
			
			System.out.println(s.getSubId() + " " + 
					   s.getMemId().getMemName() + " " + 
					   s.getProdId().getProdName() + " " + 
					   s.getProdId().getProdPrice());
			
			// Delete: 첫 번째 구독 플랜 삭제
			em.remove(s);
			
			System.out.println(s.getSubId() + " " + 
					   s.getMemId().getMemName() + " " + 
					   s.getProdId().getProdName() + " " + 
					   s.getProdId().getProdPrice());
			
		} catch(Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if(em != null) {
				em.close();
				em = null;
			}
		}

	}
}