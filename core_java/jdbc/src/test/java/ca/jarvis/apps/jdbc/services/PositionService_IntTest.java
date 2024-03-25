package ca.jarvis.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.*;
import ca.jrvs.apps.jdbc.services.PositionService;
import ca.jrvs.apps.jdbc.services.QuoteService;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PositionService_IntTest {

    private static final String URL = "jdbc:postgresql://localhost:5432/stock_quote";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private static final String SYMBOL = "MSFT";
    private static int numberOfShares = 10;
    private static double price = 150.0;
    private PositionDao dao;
    private QuoteDao quoteDao;


    @BeforeEach
    void setUp() throws SQLException {
        dao = new PositionDao(DriverManager.getConnection(URL, USER, PASSWORD));
        quoteDao = new QuoteDao(DriverManager.getConnection(URL, USER, PASSWORD));
    }

    @Test
    void buy()
    {
        Position resultPosition = new PositionService(dao,quoteDao).buy(SYMBOL,numberOfShares,price);
        Position ExpectedPosition = dao.findById(SYMBOL).get();

        assertEquals(resultPosition.getTicker(),ExpectedPosition.getTicker());
        assertEquals(resultPosition.getNumOfShares(),ExpectedPosition.getNumOfShares());
        assertEquals(resultPosition.getValuePaid(),ExpectedPosition.getValuePaid());
    }

    @Test
    void sell()
    {
        new PositionService(dao,quoteDao).sell(SYMBOL);

        assertThrowsExactly(NoSuchElementException.class, ()->{
            dao.findById(SYMBOL).get();
        });
    }
}
