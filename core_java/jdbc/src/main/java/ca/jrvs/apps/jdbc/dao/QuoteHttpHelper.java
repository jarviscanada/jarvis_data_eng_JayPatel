package ca.jrvs.apps.jdbc.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.IOException;
import java.sql.Timestamp;

public class QuoteHttpHelper {
    private String apiKey;
    private OkHttpClient client;

    public QuoteHttpHelper(String apiKey, OkHttpClient client)
    {
        this.apiKey = apiKey;
        this.client = client;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException, IOException {

        Quote quote = null;
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        String response = client.newCall(request).execute().body().string();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Parse the JSON string
            JsonNode root = mapper.readTree(response);

            // Get the "Global Quote" object
            String globalQuote = root.get("Global Quote").toString();

             quote = mapper.readValue(globalQuote, Quote.class);
             quote.setTimestamp(new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quote;
    }
}
