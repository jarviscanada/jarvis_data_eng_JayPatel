package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.Quote;
import ca.jrvs.apps.jdbc.services.PositionService;
import ca.jrvs.apps.jdbc.services.QuoteService;

import java.util.Optional;
import java.util.Scanner;

public class StockQuoteController {

    private QuoteService quoteService;
    private PositionService positionService;

    public StockQuoteController(QuoteService quoteService,PositionService positionService)
        {
            this.quoteService = quoteService;
            this.positionService = positionService;
        }

    public void initClient() {

        Scanner in = new Scanner(System.in);

        //TO DO
        while(true)
        {

            System.out.println("Enter Choice : ");
            System.out.println("1. Fetch Data");
            System.out.println("2. Buy ");
            System.out.println("3. Sell ");
            System.out.println("4. Stop ");
            int ch = in.nextInt();
            in.nextLine();
            switch (ch)
            {
                case  1:
                    System.out.println("Enter Ticker:");
                    String ticker = in.nextLine();
                    Optional<Quote> quote =quoteService.fetchQuoteDataFromAPI(ticker);
                    if(quote.get().getTicker() != null)
                    {
                        System.out.println(quote.get());
                    }else {
                        System.out.println("NO data found for ticker");
                    }
                    continue;
                case  2:
                    System.out.println("Enter Ticker:");
                    ticker = in.nextLine();
                    System.out.println("Enter Number Of Shares:");
                    Integer num = in.nextInt();
                    positionService.buy(ticker,num,152);
                    continue;
                case  3:
                    System.out.println("Enter Ticker:");
                    ticker = in.nextLine();
                            positionService.sell(ticker);
                    continue;
                case 4:
                    break;

            }

            if(ch == 4)
            {
                break;
            }
        }

//        positionService.buy("MSFT",10,152);
//
//        positionService.buy("MSFT",40,151);
//        positionService.buy("MSFT",20,153);
////
////
////        positionService.sell("MSFT");


    }

}
