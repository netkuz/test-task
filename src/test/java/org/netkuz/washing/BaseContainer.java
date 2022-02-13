package org.netkuz.washing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class BaseContainer {
    private static final PostgresTestContainer postgres = PostgresTestContainer.getInstance();

    @BeforeAll
    static void startContainer() {
        postgres.start();
    }

    @AfterAll
    static void destroyContainer() {
        postgres.stop();
    }
}
