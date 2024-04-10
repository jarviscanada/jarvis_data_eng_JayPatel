package ca.jarvis.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dao.Quote;
import ca.jrvs.apps.jdbc.dao.QuoteHttpHelper;
import ca.jrvs.apps.jdbc.sampleTest.NotSoSimpleCalculatorImpl;
import ca.jrvs.apps.jdbc.sampleTest.SimpleCalculator;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class quoteHttpHelperTest {

    @Mock
    OkHttpClient client;

    QuoteHttpHelper quoteHttpHelper;
    @Mock
    Request request;

    @BeforeEach
    void init() {
        client = new OkHttpClient();
        request =  new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?interval=5min&function=TIME_SERIES_INTRADAY&symbol=MSFT&datatype=json&output_size=compact")
                .get()
                .addHeader("X-RapidAPI-Key", "aab1c66410mshe7b7b20598d8ad2p1386c6jsn09fd6e3aa699")
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();
    }

    @Test
    void fetchQuoteInfo() throws IOException {
        String responseBody = "{\"Global Quote\": {\"01. symbol\": \"MSFT\",\"02. open\": \"420.2400\",\"03. high\": \"427.8200\",\"04. low\": \"417.9900\",\"05. price\": \"425.2200\",\"06. volume\": \"33955263\",\"07. latest trading day\": \"2024-03-14\",\"08. previous close\": \"415.1000\",\"09. change\": \"10.1200\",\"10. change percent\": \"2.4380%\"}}";

        // Create a mock Response object
        Response response = new Response.Builder()
                .request(new Request.Builder().url("http://example.com").build())
                .protocol(okhttp3.Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(MediaType.parse("application/json"), responseBody))
                .build();

        when(client.newCall(request).execute()).thenReturn(response);
        Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");

        // Test return type of each getter method
        assertNotNull(quote.getTicker());
        assertNotNull(quote.getOpen());
        assertNotNull(quote.getHigh());
        assertNotNull(quote.getLow());
        assertNotNull(quote.getPrice());
        assertNotNull(quote.getVolume());
        assertNotNull(quote.getLatestTradingDay());
        assertNotNull(quote.getPreviousClose());
        assertNotNull(quote.getChange());
        assertNotNull(quote.getChangePercent());
        assertNotNull(quote.getTimestamp());
    }
}
