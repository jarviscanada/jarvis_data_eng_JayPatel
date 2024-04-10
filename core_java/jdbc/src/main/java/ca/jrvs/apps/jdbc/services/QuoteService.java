package ca.jrvs.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.Quote;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dao.QuoteHttpHelper;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.Optional;

public class QuoteService {
    private QuoteDao dao;
    private QuoteHttpHelper httpHelper;

    /**
     * Fetches latest quote data from endpoint
     * @param ticker
     * @return Latest quote information or empty optional if ticker symbol not found
     */

    public QuoteService(QuoteDao dao, QuoteHttpHelper httpHelper)
    {
    this.dao = dao;
    this.httpHelper = httpHelper;
    }
    public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
        //TO DO
        Quote quote;
        try
        {
            quote = httpHelper.fetchQuoteInfo(ticker);
            if(quote.getTicker() == null)
            {
                return Optional.empty();
            }
            dao.save(quote);
            return Optional.of(quote);
        }catch (Exception e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        QuoteDao quoteDao = new QuoteDao();
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("aab1c66410mshe7b7b20598d8ad2p1386c6jsn09fd6e3aa699", new OkHttpClient());
        QuoteService quoteService = new QuoteService(quoteDao,quoteHttpHelper);
        quoteService.fetchQuoteDataFromAPI("asdas").isEmpty();
    }
}
