package com.goodworkalan.waste.mix;

import com.goodworkalan.mix.ProjectModule;
import com.goodworkalan.mix.builder.Builder;
import com.goodworkalan.mix.builder.JavaProject;

public class WasteProject extends ProjectModule {
    @Override
    public void build(Builder builder) {
        builder
            .cookbook(JavaProject.class)
                .produces("com.github.bigeasy.waste/waste/0.7")
                .main()
                    .depends()
                        .include("org.freemarker/freemarker/2.3.13")
                        .include("javax.mail/mail/1.4")
                        .include("log4j/log4j/1.2.13")
                        .include("org.slf4j/slf4j-log4j12/1.4.2")
                        .end()
                    .end()
                .test()
                    .depends()
                        .include("org.testng/testng-jdk15/5.10")
                        .end()
                    .end()
                .end()
            .end();
    }
}
