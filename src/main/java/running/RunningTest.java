package running;


import java.util.List;

import javax.persistence.EntityManager;

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
	
	@Test
	public void AllSubscribe() {
		EntityManager em = DBUtil.getEntityManager();

		List<Subscribe> subscribes = em.createQuery("SELECT s FROM Subscribe s", Subscribe.class).getResultList();
		
		for(Subscribe s : subscribes) {
			System.out.println(s);
		}
		
		em.close();
		em = null;
	}
	
//	@Test
	public void findTest() {
		EntityManager em = DBUtil.getEntityManager();

		Subscribe s = em.find(Subscribe.class, 2);
		System.out.println(s.getSubId() + " " + 
						   s.getMemId().getMemName() + " " + 
						   s.getProdId().getProdName() + " " + 
						   s.getProdId().getProdPrice());
		
		em.close();
		em = null;
	}
}