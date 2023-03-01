package com.cognizant.config.profiles;

import org.springframework.beans.factory.annotation.Autowired;

public class SpringProfilesIntegrationTest {

    @Autowired
    DataSourceConfig dataSourceConfig;

    public void setUpDataSource() {
        dataSourceConfig.setup();
    }
}
