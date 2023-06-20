package mimingucci;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	protected SessionFactory sessionFactory=null;
    public void setUp() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    	        .configure() // configures settings from hibernate.cfg.xml
    	        .build();
    	try {
    	    this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	    StandardServiceRegistryBuilder.destroy(registry);
    	    ex.printStackTrace();
    	}
    }
    public void exit() {
    	sessionFactory.close();
    }
    
    private void persist() {
    	Session session=this.sessionFactory.openSession();
		session.getTransaction().begin();
		User user=new User();
        user.setEmail("gtvvunguye@gmail.com");
        user.setPassword("toivaban12345");
        user.setUsername("mimingucci");
        session.persist(user);
		session.getTransaction().commit();
		session.close();
    }
    
    private void persistManyToMany() {
    	Session session=this.sessionFactory.openSession();
		session.getTransaction().begin();
		
		Person person1=new Person();
		person1.setName("Nguyen Tien Vu");
		Person person2=new Person();
		person2.setName("Leo Messi");
		Address address=new Address();
		address.setName("67-Ngai Cau-An Khanh-Hoai Duc-Ha Noi");
		person1.addAddress(address);
		person2.addAddress(address);
		session.persist(person1);
		session.persist(person2);
		session.getTransaction().commit();
		session.close();
    }
    
    private void persistOneToMany() {
    	Session session=this.sessionFactory.openSession();
		session.getTransaction().begin();
		Book book1=new Book();
		book1.setTitle("Dac Nhan Tam");
		Book book2=new Book();
		book2.setTitle("Cha giau cha ngheo");
		Person person=session.get(Person.class, 1);
		person.addBooks(book1);
		person.addBooks(book2);
		session.persist(person);
		session.getTransaction().commit();
		session.close();
    }

    
	public static void main(String[] args) {
		HibernateUtil util=new HibernateUtil();
		util.setUp();
//		util.persist();
//		util.persistManyToMany();
		util.persistOneToMany();
		util.exit();
	}
}
