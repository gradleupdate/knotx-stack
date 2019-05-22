plugins {
    `java-library`
}

repositories {
    jcenter()
    maven { url = uri("https://oss.sonatype.org/content/groups/staging/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    testImplementation(platform("io.knotx:knotx-dependencies:${project.version}"))

    testImplementation(group = "io.vertx", name = "vertx-core")
    testImplementation(group = "io.vertx", name = "vertx-service-proxy")
    testImplementation(group = "io.vertx", name = "vertx-rx-java2")
    testImplementation(group = "io.vertx", name = "vertx-codegen")
    testImplementation(group = "io.vertx", name = "vertx-junit5")
    testImplementation(group = "io.vertx", name = "vertx-unit")
    testImplementation(group = "com.github.tomakehurst", name = "wiremock")

    testImplementation("io.knotx:knotx-commons:${project.version}")
    testImplementation("io.knotx:knotx-launcher:${project.version}")
    testImplementation("io.knotx:knotx-junit5:${project.version}")
    testImplementation("io.knotx:knotx-fragment-api:${project.version}")
    testImplementation("io.knotx:knotx-server-http-core:${project.version}")
    testImplementation("io.knotx:knotx-splitter-html:${project.version}")
    testImplementation("io.knotx:knotx-assembler:${project.version}")
    testImplementation("io.knotx:knotx-repository-connector-fs:${project.version}")
    testImplementation("io.knotx:knotx-repository-connector-http:${project.version}")
    testImplementation("io.knotx:knotx-fragments-handler-core:${project.version}")
    testImplementation("io.knotx:knotx-action-http:${project.version}")
    testImplementation("io.knotx:knotx-template-engine-core:${project.version}")
    testImplementation("io.knotx:knotx-template-engine-handlebars:${project.version}")
}

apply(from = "gradle/javaAndUnitTests.gradle.kts")
apply(from = "gradle/distribution.gradle.kts")

