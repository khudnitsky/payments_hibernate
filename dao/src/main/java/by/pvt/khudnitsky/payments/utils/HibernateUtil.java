package by.pvt.khudnitsky.payments.utils;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by: khudnitsky
 * Date: 05.02.2016
 * Time: 16:21
 */
public class HibernateUtil {
    private static Logger logger = Logger.getLogger(HibernateUtil.class);
    private static HibernateUtil util = null;
    private SessionFactory sessionFactory = null;
    private final ThreadLocal <Session> sessions = new ThreadLocal<Session>();

    private HibernateUtil(){
        try {
            Configuration configuration = new Configuration();
            configuration.setNamingStrategy(new CustomNamingStrategy());
            configuration.configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        catch(Throwable e){
            logger.error("Initial session factory creation failed ", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static HibernateUtil getInstance(){
        if (util == null){
            util = new HibernateUtil();
        }
        return util;
    }

    public Session getSession(){
        Session session = sessions.get();
        if(session == null){
            session = sessionFactory.openSession();
            sessions.set(session);
        }
        return session;
    }
}
