package com.goodworkalan.waste.mix;

import com.goodworkalan.go.go.Artifact;
import com.goodworkalan.mix.ProjectModule;
import com.goodworkalan.mix.builder.Builder;
import com.goodworkalan.mix.builder.JavaProject;

public class WasteProject extends ProjectModule {
    @Override
    public void build(Builder builder) {
        builder
            .cookbook(JavaProject.class)
                .produces(new Artifact("com.goodworkalan/waste/0.7"))
                .main()
                    .depends()
                        .artifact(new Artifact("org.freemarker/freemarker/2.3.13"))
                        .artifact(new Artifact("javax.mail/mail/1.4"))
                        .artifact(new Artifact("log4j/log4j/1.2.13"))
                        .artifact(new Artifact("org.slf4j/slf4j-log4j12/1.4.2"))
                        .end()
                    .end()
                .test()
                    .depends()
                        .artifact(new Artifact("org.testng/testng/5.10/jdk15"))
                        .end()
                    .end()
                .end()
            .end();
    }
}
