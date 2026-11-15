import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import org.gradle.api.plugins.quality.Checkstyle

plugins {
    id("java")
    checkstyle
    id("com.github.spotbugs") version "6.0.25"
    jacoco
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

checkstyle {
    isIgnoreFailures = false
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
    }
}

spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.DEFAULT
    reportLevel = Confidence.DEFAULT
    maxHeapSize = "1g"
    extraArgs = listOf("-nested:false")
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = layout.buildDirectory.file(
            "reports/spotbugs/spotbugs.html"
        )
        setStylesheet("fancy-hist.xsl")
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required = true
        csv.required = false
        html.required = true
        html.outputLocation = layout.buildDirectory.dir(
            "reports/jacoco"
        )
    }
}

pitest {
    targetClasses = setOf(
        "controller.*",
        "domain.*",
        "view.*",
        "Main"
    )

    targetTests = setOf(
        "controller.*",
        "domain.*",
        "view.*"
    )

    junit5PluginVersion = "1.2.1"
    pitestVersion = "1.15.0"

    threads = 4
    outputFormats = setOf("HTML")
    timestampedReports = false

    testSourceSets.set(
        listOf(sourceSets.test.get())
    )

    mainSourceSets.set(
        listOf(sourceSets.main.get())
    )

    jvmArgs.set(
        listOf("-Xmx1024m")
    )

    useClasspathFile.set(true)
    fileExtensionsToFilter.addAll("xml")
    exportLineCoverage = true
}