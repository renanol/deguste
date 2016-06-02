package br.com.deguste.dao;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

@Named
public class EntityManagerProduces implements Serializable {

	private static final long serialVersionUID = -6880367101549848149L;
	private static final String PERSISTENCE_UNIT = "degustePU";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

	@Produces @RequestScoped
	public EntityManager createEntityManager(){
		EntityManager em = factory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		return em;
	}

	public void closeEntityManager(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

	public EntityManagerProduces(){ }
}