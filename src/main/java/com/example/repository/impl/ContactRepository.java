package com.example.repository.impl;

import com.example.config.HibernateUtil;
import com.example.domain.Contact;
import com.example.repository.AppRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ContactRepository implements AppRepository<Contact> {

    private static final Logger LOGGER = Logger.getLogger(ContactRepository.class.getName());

    @Override
    public void create(Contact obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "INSERT INTO Contact (name, phone) VALUES (:name, :phone)";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("name", obj.getName());
            query.setParameter("phone", obj.getPhone());
            query.executeUpdate();

            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.warning(e.getMessage());
        }
    }

    @Override
    public Optional<List<Contact>> fetchAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Contact";
            List<Contact> contacts = session.createQuery(hql, Contact.class).list();

            transaction.commit();
            return Optional.of(contacts);
        }catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contact> fetchById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Contact WHERE id = :id";

            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("id", id);
            query.setMaxResults(1);
            Contact contact = query.uniqueResult();

            transaction.commit();
            return Optional.ofNullable(contact);
        }catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, Contact obj) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            String hql = "UPDATE Contact SET name = :name, phone = :phone WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("name", obj.getName());
            query.setParameter("phone", obj.getPhone());
            query.setParameter("id", id);
            query.executeUpdate();

            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.warning(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            String hql = "DELETE FROM Contact WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();

            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.warning(e.getMessage());
        }
    }

    public Optional<List<Contact>> fitchByName(String name){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            String hql = "FROM Contact WHERE name = :name";
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("name", name);
            List<Contact> contacts = query.list();

            transaction.commit();
            return Optional.of(contacts);
        }catch (Exception e){
            LOGGER.warning(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Contact> getLastEntity(){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query<Contact> query = session.createQuery("FROM Contact ORDER BY id DESC", Contact.class);
            query.setMaxResults(1);
            Contact contact = query.uniqueResult();

            transaction.commit();
            return Optional.of(contact);
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.warning(e.getMessage());
            return Optional.empty();
        }
    }
}
