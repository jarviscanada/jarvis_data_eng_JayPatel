package ca.jarvis.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dao.Position;
import ca.jrvs.apps.jdbc.dao.PositionDao;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class PositionDao_Test {

    private PositionDao positionDao;

    private Position position;
    private static final String URL = "jdbc:postgresql://localhost:5432/stock_quote";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private static final String SYMBOL = "MSFT";

    @BeforeEach
    void setUp() throws SQLException, IOException {
        positionDao = new PositionDao(DriverManager.getConnection(URL, USER, PASSWORD));
        position = new Position("MSFT",10,1520.6);
    }

    @Test()
    void save()
    {
        Position putQ =positionDao.save(position);
        Position  getQ=  positionDao.findById(position.getTicker()).get();
        assertEquals(getQ.getTicker(),putQ.getTicker());
    }

    @Test()
    void findById()
    {
        Position putQ =positionDao.findById(SYMBOL).get();
        assertEquals(position.getTicker(),putQ.getTicker());
    }

    @Test()
    void findAll()
    {
        List<Position> quotes = new ArrayList<>();
        positionDao.findAll().forEach(quotes::add);
        assertEquals(1, quotes.size());
    }

    @Test()
    void deleteById()
    {
        positionDao.deleteById(SYMBOL);

        assertThrowsExactly(NoSuchElementException.class, ()->{
            positionDao.findById(SYMBOL).get();
        });
    }

    @Test
    void deletAll()
    {
        positionDao.deleteAll();
        List<Position> quotes = new ArrayList<>();
        positionDao.findAll().forEach(quotes::add);
        assertEquals(0, quotes.size());
    }

}
