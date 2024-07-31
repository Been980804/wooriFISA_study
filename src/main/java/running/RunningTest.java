package running;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import model.domain.entity.Member;
import model.domain.entity.Product;
import model.domain.entity.Subscribe;
import util.DBUtil;

public class RunningTest {

	@Test
	public void step01Test() {
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try {
			em = DBUtil.getEntityManager();
			tx = em.getTransaction();
			tx.begin();
			
			Member m1 = new Member("이승준", "abc@gmail.com", new Date("1997/02/11"));
			Member m2 = new Member("이현빈", "def@gmail.com", new Date("1997/02/11"));
			Member m3 = new Member("조성현", "ghi@gmail.com", new Date("1997/02/11"));
			Member m4 = new Member("허예은", "jkl@gmail.com", new Date("1997/02/11"));
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
			
			tx.commit();
			
		}catch(Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally {
			if(em != null) {
				em.close();
				em = null;
			}
		}
	}
}