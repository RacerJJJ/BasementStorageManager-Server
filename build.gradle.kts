plugins {
    id("java")
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
}

tasks.test {
    useJUnitPlatform()
}
