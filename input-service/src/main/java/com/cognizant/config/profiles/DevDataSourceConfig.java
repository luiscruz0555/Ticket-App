package com.cognizant.config.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("dev")
public class DevDataSourceConfig implements DataSourceConfig {

    @Override
    public void setup() {
        System.out.println("Setting up datasource for dev environment.");
    }

}
