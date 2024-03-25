package ca.jarvis.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.*;
import ca.jrvs.apps.jdbc.services.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuoteService_UnitTest {

    @Mock
    QuoteDao quoteDao;

    @Mock
    Quote quote;
    @Mock
    QuoteHttpHelper quoteHttpHelper;

    private QuoteService quoteService;

    @BeforeEach
    void setUp()
    {

        quoteService = new QuoteService(quoteDao,quoteHttpHelper);
    }

    @Test
    void fetchQuoteDataFromAPI() throws IOException {

        when(quoteHttpHelper.fetchQuoteInfo("asdasd")).thenReturn(quote);
        Optional<Quote> result = quoteService.fetchQuoteDataFromAPI("asdasd");
        assertTrue(!result.isPresent());
    }
}
