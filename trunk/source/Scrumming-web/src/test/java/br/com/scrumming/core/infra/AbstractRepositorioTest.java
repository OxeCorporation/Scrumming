package br.com.scrumming.core.infra;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.metadata.ClassMetadata;
import org.junit.After;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
@Ignore
public class AbstractRepositorioTest extends AbstractTest {

    private final Logger log = Logger.getLogger(getClass()); // NOPMD

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    protected Session getCurrentSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public void save(Object... entity) {
        for (Object object : entity) {
            getCurrentSession().save(object);
        }
        flush();
    }

    protected void update(Object... entity) {
        for (Object object : entity) {
            getCurrentSession().update(object);
        }
        flush();
    }

    protected void delete(Object... entity) {
        for (Object object : entity) {
            getCurrentSession().delete(object);
        }
        flush();
    }

    protected void evict(Object... entity) {
        for (Object object : entity) {
            getCurrentSession().evict(object);
        }
    }

    protected void flush() {
        getCurrentSession().flush();
    }

    @SuppressWarnings("unchecked")
    protected <T> T findById(Serializable id, Class<T> clazz) {
        return (T) getCurrentSession().load(clazz, id);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findAll(Class<T> clazz) {
        return getCurrentSession().createCriteria(clazz).list();
    }

    @After
    public void tearDown() throws Exception {
        clearDatabase();
    }

    protected void clearDatabase() {
        Map<String, Integer> counter = new java.util.HashMap<String, Integer>();
        try {
            getCurrentSession().clear();
            // deleting data from mapped classes tables
            Stack<String> classes = getMappedClasses(getCurrentSession());
            int quantidadeClassesMapeadas = classes.size();
            while (!classes.isEmpty()) {
                String clazz = classes.pop();
                try {
                    getCurrentSession().createQuery("delete from " + clazz).executeUpdate();
                    getCurrentSession().flush();
                } catch (SQLGrammarException ex) {
                    log.error(ex);
                } catch (Exception e) {

                    // if this class cannot be delete due to other class
                    // dependencies then we put it back at the beginning of
                    // the
                    // class stack so we can try to delete later
                    classes.add(0, clazz);

                    Integer total = counter.get(clazz);
                    if (total == null) {
                        total = 0;
                    }
                    counter.put(clazz, ++total);

                    if (total.intValue() == quantidadeClassesMapeadas) {
                        classes.remove(clazz);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private Stack<String> getMappedClasses(Session session) {
        Map<String, ClassMetadata> metadata = session.getSessionFactory().getAllClassMetadata();
        Stack<String> classes = new Stack<String>();

        for (String clazz : metadata.keySet()) {
            classes.push(clazz);
        }

        return classes;
    }

}
