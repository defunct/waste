package com.goodworkalan.waste.mix;

import com.goodworkalan.mix.ProjectModule;
import com.goodworkalan.mix.builder.Builder;
import com.goodworkalan.mix.cookbook.JavaProject;

/**
 * Builds the project definition for Waste.
 *
 * @author Alan Gutierrez
 */
public class WasteProject implements ProjectModule {
    /**
     * Build the project definition for Waste.
     *
     * @param builder
     *          The project builder.
     */
    public void build(Builder builder) {
        builder
            .cookbook(JavaProject.class)
                .produces("com.github.bigeasy.waste/waste/0.7")
                .depends()
                    .production("org.freemarker/freemarker/2.3.13")
                    .production("javax.mail/mail/1.4")
                    .production("log4j/log4j/1.2.13")
                    .production("org.slf4j/slf4j-log4j12/1.4.2")
                    .development("org.testng/testng-jdk15/5.10")
                    .end()
                .end()
            .end();
    }
}
