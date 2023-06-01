package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.CustomUserDetailService;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
@Profile("hallplan")
public class HallPlanDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DataSource dataSource;

    private final CustomUserDetailService userDetailService;

    public HallPlanDataGenerator(DataSource dataSource, CustomUserDetailService userDetailService) {
        this.dataSource = dataSource;
        this.userDetailService = userDetailService;
    }

    @PostConstruct
    public void generateData() throws SQLException {
        LOGGER.info("Generating hall plan data...");
        try (var connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql/hallplanData.sql"));
            LOGGER.info("Finished generating data without error.");
        }
    }

}
