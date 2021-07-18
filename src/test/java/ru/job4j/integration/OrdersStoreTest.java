package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class OrdersStoreTest {
    private final BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        executeDdl("./db/update_001.sql");
    }

    @After
    public void cleanUp() throws SQLException {
        executeDdl("./db/cleanUp.sql");
    }

    private void executeDdl(String script) throws SQLException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(script)))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertEquals(1, all.size());
        assertEquals("description1", all.get(0).getDescription());
        assertEquals(1, all.get(0).getId());
    }

    @Test
    public void whenSaveOrderAndUpdateFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        Order order = store.save(Order.of("name1", "description1"));

        Order newOrder = Order.of("name2", "description2");
        newOrder.setId(order.getId());
        store.update(newOrder);

        List<Order> all = (List<Order>) store.findAll();

        assertEquals(1, all.size());
        assertEquals("description2", all.get(0).getDescription());
        assertEquals(1, all.get(0).getId());
    }

    @Test
    public void whenSaveOrderAndFindById() {
        OrdersStore store = new OrdersStore(pool);

        Order order1 = store.save(Order.of("name1", "description1"));
        Order order2 = store.save(Order.of("name2", "description2"));

        Order res = store.findById(order1.getId());

        assertEquals("description1", res.getDescription());
    }

    @Test
    public void whenSaveOrderAndFindByNameWithManyResults() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));
        store.save(Order.of("name2", "description2"));

        List<Order> all = (List<Order>) store.findByName("%name%");

        assertEquals(2, all.size());
        assertEquals("description1", all.get(0).getDescription());
        assertEquals("description2", all.get(1).getDescription());
    }

    @Test
    public void whenSaveOrderAndFindByNameWithOneResult() {
        OrdersStore store = new OrdersStore(pool);

        Order order1 = store.save(Order.of("name1", "description1"));
        Order order2 = store.save(Order.of("name2", "description2"));

        List<Order> all = (List<Order>) store.findByName(order1.getName());

        assertEquals(1, all.size());
        assertEquals("description1", all.get(0).getDescription());
    }
}