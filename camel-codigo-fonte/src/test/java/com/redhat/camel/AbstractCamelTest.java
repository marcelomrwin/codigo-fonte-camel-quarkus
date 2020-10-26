package com.redhat.camel;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCamelTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    CamelContext context;

    @Inject
    ProducerTemplate producerTemplate;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        logger.info("test started: {}\n------------------------", testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        logger.info("test finished: {}\n========================", testInfo.getDisplayName());
    }
}
