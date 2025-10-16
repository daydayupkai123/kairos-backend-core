plugins {
    id("java")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.kairos"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    // jwt
    implementation ("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.12.5")

    // lombok
    compileOnly  ("org.projectlombok:lombok:1.18.30")
    annotationProcessor ("org.projectlombok:lombok:1.18.30")

    // swagger
    implementation ("org.springdoc:springdoc-openapi-ui:1.7.0")

    // redis
    implementation ("org.springframework.boot:spring-boot-starter-data-redis")

    runtimeOnly ("org.postgresql:postgresql")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

springBoot {
    mainClass = "com.kairos.KairosApplication"
}