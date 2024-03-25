package ca.jrvs.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.Position;
import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.Quote;
import ca.jrvs.apps.jdbc.dao.QuoteDao;

import java.util.Optional;

public class PositionService {

    private PositionDao dao;
    private QuoteDao quoteDao;

    public PositionService(PositionDao dao,QuoteDao quoteDao)
    {
            this.dao = dao;
            this.quoteDao = quoteDao;
    }
    /**
     * Processes a buy order and updates the database accordingly
     * @param ticker
     * @param numberOfShares
     * @param price
     * @return The position in our database after processing the buy
     */
    public Position buy(String ticker, int numberOfShares, double price) {
        //TO DO

        double totalPrice = numberOfShares*price;
        Optional<Position> positionOptional = dao.findById(ticker);
        Optional<Quote> quoteOptional = quoteDao.findById(ticker);
        Position position;
        if(positionOptional.isPresent())
        {
            position = positionOptional.get();
            if(quoteOptional.get().getVolume() <= position.getNumOfShares() + numberOfShares)
            {
            position.setNumOfShares(position.getNumOfShares() + numberOfShares);
            position.setValuePaid(position.getValuePaid() + totalPrice);
            }

        }else
        {
            position = new Position(ticker,numberOfShares,totalPrice);
        }

        dao.save(position);

        return position;
    }

    /**
     * Sells all shares of the given ticker symbol
     * @param ticker
     */
    public void sell(String ticker) {
        //TO DO
        dao.deleteById(ticker);
    }

}
