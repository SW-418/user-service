import org.gradle.kotlin.dsl.testImplementation

plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.flywaydb.flyway") version "11.10.2"
}

group = "io.samwells"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

flyway {
    url = System.getenv("SPRING_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/user_service"
    user = System.getenv("SPRING_DATASOURCE_USERNAME") ?: "postgres"
    password = System.getenv("SPRING_DATASOURCE_PASSWORD") ?: "postgres"
    locations = arrayOf("classpath:db/migration")
    baselineOnMigrate = true
    validateOnMigrate = true
	validateMigrationNaming = true
	driver = "org.postgresql.Driver"
}

configurations {
	all {
		// Exclude default logger (Logback) - This is included in base starter and web starter
		exclude(module = "spring-boot-starter-logging")
		exclude(module = "logback-classic")
	}
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Logging - Use SL4J wrapper over the Log4J2 implementation
	implementation("org.slf4j:slf4j-api:2.0.17")
	implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.25.0")
	implementation("org.apache.logging.log4j:log4j-api:2.25.0")
	implementation("org.apache.logging.log4j:log4j-core:2.25.0")

	// DB
	implementation("org.flywaydb:flyway-core")
	implementation("org.postgresql:postgresql:42.7.7")
	implementation("com.h2database:h2")

	// JWTs
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
