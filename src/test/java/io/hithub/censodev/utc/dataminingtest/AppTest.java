package io.hithub.censodev.utc.dataminingtest;

import io.github.censodev.utc.dataminingtest.App;
import io.github.censodev.utc.dataminingtest.IApp;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class AppTest {
    IApp app = new App();

    public AppTest() throws URISyntaxException, IOException {
    }

    @Test
    void testMeanMediumMode() {
        app.runCalcMeanMediumModeJob();
    }
}
