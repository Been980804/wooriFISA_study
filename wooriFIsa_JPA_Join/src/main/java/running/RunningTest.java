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
			
			tx.begin();

			Member m1 = new Member("이승준", "abc@gmail.com", "2000-01-01");
			Member m2 = new Member("이현빈", "def@gmail.com", "2000-01-01");
			Member m3 = new Member("조성현", "ghi@gmail.com", "2000-01-01");
			Member m4 = new Member("허예은", "jkl@gmail.com", "2000-01-01");
			em.persist(m1);
			em.persist(m2);
			em.persist(m3);
			em.persist(m4);

			Product p1 = new Product(m1, "휴지", 16000);
			Product p2 = new Product(m2, "생수", 12000);
			em.persist(p1);
			em.persist(p2);

			Subscribe s1 = new Subscribe(m1, p1, 20, 50, "20240730");
			Subscribe s2 = new Subscribe(m2, p2, 10, 60, "20240728");
			Subscribe s3 = new Subscribe(m3, p1, 50, 70, "20240710");
			em.persist(s1);
			em.persist(s2);
			em.persist(s3);
			
			// Select: 첫 번째 구독 플랜의 유저 정보 조회
			Subscribe s = em.find(Subscribe.class, 1);
			System.out.println(s.getSubId() + " " + 
							   s.getMemId().getMemName() + " " + 
							   s.getProdId().getProdName() + " " + 
							   s.getProdId().getProdPrice());
			
			// Update: 첫 번째 구독 플랜의 상품 정보 변경 : 휴지 -> 생수
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