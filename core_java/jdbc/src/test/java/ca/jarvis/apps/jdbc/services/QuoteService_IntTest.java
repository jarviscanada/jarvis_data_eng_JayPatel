package ca.jarvis.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.Quote;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dao.QuoteHttpHelper;
import ca.jrvs.apps.jdbc.services.QuoteService;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;import static org.junit.jupiter.api.Assertions.*;


public class QuoteService_IntTest {

    private QuoteDao dao;
    private QuoteHttpHelper httpHelper;

    private static final String URL = "jdbc:postgresql://localhost:5432/stock_quote";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    private static final String SYMBOL = "MSFT";


    @BeforeEach
    void setUp() throws SQLException {
        dao = new QuoteDao(DriverManager.getConnection(URL, USER, PASSWORD));
        httpHelper = new QuoteHttpHelper("aab1c66410mshe7b7b20598d8ad2p1386c6jsn09fd6e3aa699",new OkHttpClient());
    }

    @Test
    void fetchQuoteDataFromAPI()
    {
        Optional<Quote> resultQuote = new QuoteService(dao,httpHelper).fetchQuoteDataFromAPI("asdasd");
        assertTrue( resultQuote.isEmpty());
    }
}
