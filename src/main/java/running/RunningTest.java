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
			Product p = em.find(Product.class, 1);
			s.setProdId(p);
			tx.commit();
			
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