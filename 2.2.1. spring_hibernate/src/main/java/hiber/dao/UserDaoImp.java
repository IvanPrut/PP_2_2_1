package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   // Предполагается, что у разных юзеров могут быть авто с одинаковыми моделью и серией, но разными ID.
   // Поэтому прямой поиск по модели и серии может выдать несколько юзеров.
   // По этой причине поиск ведется по объекту Авто.
   @Override
   public User getUserByCar(Car car) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where car=:car");
      query.setParameter("car", car);
      return query.getSingleResult();
   }
}
