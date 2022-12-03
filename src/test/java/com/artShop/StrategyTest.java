package com.artShop;

import com.artShop.DataBases.Strategy;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@SpringBootTest
public class StrategyTest {
    @Test(expected = Exception.class)
    public void strategyWrongDatabaseTest() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("USE_DATABASE", "NoDatabase");
        Strategy.initialize(prop);
    }

    @Test
    public void strategySQLDatabaseTest() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("USE_DATABASE", "MYSQL");
        Strategy.initialize(prop);
    }

    @Test
    public void strategyMongoDatabaseTest() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("MONGODB_URL","mongodb://localhost:27017");
        prop.setProperty("MONGODB_DATABASE","shop");
        prop.setProperty("USE_DATABASE", "MONGODB");
        Strategy.initialize(prop);
    }
}
