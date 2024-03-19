import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	war
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}

group = "com.tui"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}
val mockkVersion = "1.13.4"
val springMockkVersion = "4.0.2"
val springCloudStubRunnerVersion = "4.0.2"

dependencies {
	implementation("org.springdoc:springdoc-openapi-webflux-core:1.7.0")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.7.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")


	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation(kotlin("test"))
	testImplementation(kotlin("test-junit5"))
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
	testImplementation("com.github.tomakehurst:wiremock:2.27.2")
	testImplementation("javax.servlet:javax.servlet-api:4.0.1") // Add servlet API dependency
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:$springCloudStubRunnerVersion")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
