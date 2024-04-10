package ca.jarvis.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dao.Quote;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dao.QuoteHttpHelper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteDao_Test {


    private QuoteDao quoteDao;

    private Quote quote;
    private static final String URL = "jdbc:postgresql://localhost:5432/stock_quote";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private static final String SYMBOL = "MSFT";

    @BeforeEach
    void setUp() throws SQLException, IOException {
       quoteDao = new QuoteDao(DriverManager.getConnection(URL, USER, PASSWORD));
       quote = new QuoteHttpHelper("aab1c66410mshe7b7b20598d8ad2p1386c6jsn09fd6e3aa699", new OkHttpClient()).fetchQuoteInfo(SYMBOL);
    }

    @Test()
    void save()
    {
        Quote putQ =quoteDao.save(quote);
        Quote  getQ=  quoteDao.findById(quote.getTicker()).get();
        assertEquals(getQ.getTicker(),putQ.getTicker());
        assertEquals(getQ.getTimestamp(),putQ.getTimestamp());
    }

    @Test()
    void findById()
    {
        Quote putQ =quoteDao.findById(SYMBOL).get();
        assertEquals(quote.getTicker(),putQ.getTicker());
    }

    @Test()
    void findAll()
    {
        List<Quote> quotes = new ArrayList<>();
        quoteDao.findAll().forEach(quotes::add);
        assertEquals(1, quotes.size());
    }

    @Test()
    void deleteById()
    {
        quoteDao.deleteById(SYMBOL);

        assertThrowsExactly(NoSuchElementException.class, ()->{
            quoteDao.findById(SYMBOL).get();
        });
    }

    @Test
    void deletAll()
    {
        quoteDao.deleteAll();
        List<Quote> quotes = new ArrayList<>();
        quoteDao.findAll().forEach(quotes::add);
        assertEquals(0, quotes.size());
    }

}
