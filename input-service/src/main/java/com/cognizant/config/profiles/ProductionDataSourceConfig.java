package com.cognizant.config.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("Production")
public class ProductionDataSourceConfig implements DataSourceConfig{

    @Override
    public void setup() {
        System.out.println("Setting up DataSource for PRODUCTION environment");
    }

}
