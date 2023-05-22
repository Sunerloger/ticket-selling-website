package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

@Component
@Profile("datagen")
public class HallPlanGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DataSource dataSource;

    public HallPlanGenerator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void generateHallPlanData() throws SQLException {
        LOGGER.info("Generating hall plan data...");
        try (var connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/insertHallPlanData.sql"));
            LOGGER.info("Finished generating data without error.");
        }
    }
}
