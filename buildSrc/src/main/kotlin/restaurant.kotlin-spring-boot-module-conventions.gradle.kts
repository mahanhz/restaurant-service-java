import org.gradle.kotlin.dsl.*
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("restaurant.kotlin-common-module-conventions")
}

val versions = Versions.fromProperties(project.rootDir)

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:${versions["cdiVersion"]}")
    implementation("org.apache.commons:commons-lang3:${versions["apacheCommonsLangVerion"]}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    compileOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation(project(":main-app"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.tngtech.archunit:archunit-junit5:${versions["archUnitVersion"]}")
    testImplementation("org.testcontainers:junit-jupiter:${versions["testcontainersVersion"]}")
    testImplementation("org.testcontainers:mysql:${versions["testcontainersVersion"]}")

    testImplementation("org.liquibase:liquibase-core:${versions["liquibaseVersion"]}")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
    archiveClassifier.set("")
    archiveBaseName.set(project.parent!!.name)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(true)
    }
}

tasks.withType<Test> {
    finalizedBy(tasks.jacocoTestReport)
}

