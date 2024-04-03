package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dao.QuoteHttpHelper;
import ca.jrvs.apps.jdbc.services.PositionService;
import ca.jrvs.apps.jdbc.services.QuoteService;
import com.sun.tools.javac.Main;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/properties.txt")) {
            properties.load(input);
            logger.debug("properties file read successfully!.");

        } catch (IOException e) {

            logger.error("Error reading Properties file.");
            throw new RuntimeException(e);
        }

        try {
            Class.forName(properties.get("db-class").toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = createDatabaseConnection(properties);
        QuoteDao quoteDao = new QuoteDao(connection);
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(properties.getProperty("api-key"),new OkHttpClient());
        QuoteService quoteService = new QuoteService(quoteDao, quoteHttpHelper);

        PositionDao positionDao = new PositionDao(connection);
        PositionService positionService = new PositionService(positionDao,quoteDao);
        logger.debug("DAO & Services created successfully!.");

        positionDao.deleteAll();
        logger.debug("All postions cleared!.");

        quoteDao.deleteAll();
        logger.debug("Existing data cleared!.");

        StockQuoteController controller = new StockQuoteController(quoteService,positionService);

        controller.initClient();
    }


    static Connection createDatabaseConnection(Properties properties) throws SQLException {
        String url = "jdbc:postgresql://"+properties.get("server")+":"+properties.get("port")+"/"+properties.get("database");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }
}
