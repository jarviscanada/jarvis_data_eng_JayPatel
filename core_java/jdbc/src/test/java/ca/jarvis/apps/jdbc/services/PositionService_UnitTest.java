package ca.jarvis.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.*;
import ca.jrvs.apps.jdbc.services.PositionService;
import ca.jrvs.apps.jdbc.services.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositionService_UnitTest {

    @Mock
    private PositionDao positionDao;

    @Mock
    private QuoteDao quoteDao;
    private PositionService positionService;

    @BeforeEach
    void setUp()
    {

        positionService = new PositionService(positionDao,quoteDao);
    }

    @Test
    void buy()
    {
        String ticker = "AAPL";
        int numberOfShares = 10;
        double price = 150.0;
        int voulme =71160138;

        Quote quote = new Quote();
        quote.setVolume(voulme);
        quote.setTicker(ticker);
        quote.setPrice(price);
        Position expectedPosition = new Position(ticker, numberOfShares, price);
        when(positionDao.findById(ticker)).thenReturn(Optional.of(expectedPosition));
        when(quoteDao.findById(ticker)).thenReturn(Optional.of(quote));
        Position actualPosition = positionService.buy(ticker,numberOfShares,price);

        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    void sell()
    {
        // Given
        String ticker = "AAPL";

        // When
        positionService.sell(ticker);

        // Then
        verify(positionDao).deleteById(ticker);
    }
}
