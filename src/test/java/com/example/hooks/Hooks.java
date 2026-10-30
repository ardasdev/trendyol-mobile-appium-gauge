package com.example.hooks;

import com.example.base.Driver;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;

public class Hooks {

    @BeforeScenario
    public void setUp() {
        try {
             Driver.get().terminateApp("trendyol.com");
        } catch (Exception ignored) {
        }
    }

    @AfterScenario
    public void tearDown() {
        Driver.quit();
    }
}
