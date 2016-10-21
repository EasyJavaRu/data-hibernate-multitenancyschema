package ru.easyjava.data.hibernate.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;

public class QueryTest {
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }

    protected void writeAUPerson() {
        Session session = sessionFactory
                .withOptions()
                .tenantIdentifier("au")
                .openSession();
        session.beginTransaction();

        Passport p = new Passport();
        p.setSeries("AS");
        p.setNo("123456");
        p.setIssueDate(LocalDate.now());
        p.setValidity(Period.ofYears(20));

        Address a = new Address();
        a.setCity("Kickapoo");
        a.setStreet("Main street");
        a.setBuilding("1");

        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Testoff");
        person.setDob(LocalDate.now());
        person.setPrimaryAddress(a);
        person.setPassport(p);

        Company c = new Company();
        c.setName("Acme Ltd");

        p.setOwner(person);
        person.setWorkingPlaces(Collections.singletonList(c));

        session.merge(person);

        session.getTransaction().commit();
        session.close();
    }

    protected void writeDEPerson() {
        Session session = sessionFactory
                .withOptions()
                .tenantIdentifier("de")
                .openSession();
        session.beginTransaction();

        Passport p = new Passport();
        p.setSeries("RY");
        p.setNo("654321");
        p.setIssueDate(LocalDate.now());
        p.setValidity(Period.ofYears(20));

        Address a = new Address();
        a.setCity("Oberdingeskirchen");
        a.setStreet("Hbf Platz");
        a.setBuilding("1");

        Person person = new Person();
        person.setFirstName("Johan");
        person.setLastName("von Testoff");
        person.setDob(LocalDate.now());
        person.setPrimaryAddress(a);
        person.setPassport(p);

        Company c = new Company();
        c.setName("Acme Ltd");

        p.setOwner(person);
        person.setWorkingPlaces(Collections.singletonList(c));

        session.merge(person);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGreeter() {
        writeAUPerson();
        writeDEPerson();

        Session session = sessionFactory
                .withOptions()
                .tenantIdentifier("au")
                .openSession();
        session.beginTransaction();

        session
                .createCriteria(Person.class)
                .list()
                .stream()
                .forEach(System.out::println);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory
                .withOptions()
                .tenantIdentifier("de")
                .openSession();
        session.beginTransaction();

        session
                .createCriteria(Person.class)
                .list()
                .stream()
                .forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
    }
}
