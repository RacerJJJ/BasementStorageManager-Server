plugins {
    id("java")
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "me.racer.jjj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("pl.coderion:openfoodfacts-java-wrapper:0.9.3")
    implementation("org.jsoup:jsoup:1.18.1")
    implementation("com.codeborne:phantomjsdriver:1.5.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.4.1")
    implementation("org.apache.commons:commons-lang3:3.16.0")
    implementation("org.apache.clerezza.ext:org.json.simple:0.4")
    implementation("org.springframework:spring:5.3.39")
    implementation("org.springframework.boot:spring-boot-starter")

}

tasks.test {
    useJUnitPlatform()
}
