import Entities.*;
import hasEntitiesExample.Article;
import hasEntitiesExample.Author;
import hasEntitiesExample.Motorcycle;
import hasEntitiesExample.PlateNumber;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("relations");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        //Vehicle car = new Car();
        //Vehicle truck = new Truck();
        //Vehicle bike = new Bike();
        //Vehicle plane = new Plane();
        //entityManager.persist(car);
        //entityManager.persist(truck);
        //entityManager.persist(bike);
        //entityManager.persist(plane);
//
        // Motorcycle motorcycle = new Motorcycle();
        // PlateNumber plateNumber = new PlateNumber();
        // entityManager.persist(motorcycle);
        // entityManager.persist(plateNumber);

        Article article = new Article("Title");
        Author author = new Author("Terry Pratchett");
        article.setAuthor(author);
        entityManager.persist(article);

        entityManager.persist(author);


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
