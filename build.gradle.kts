import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import org.gradle.api.plugins.quality.Checkstyle
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element

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

val complexityBadge by tasks.registering {
    group = "verification"
    description = "Writes badge data for missed cyclomatic complexity"

    dependsOn(tasks.jacocoTestReport)

    val reportFile = layout.buildDirectory.file(
        "reports/jacoco/test/jacocoTestReport.xml"
    )
    val outFile = layout.buildDirectory.file(
        "badges/complexity.json"
    )

    inputs.file(reportFile)
    outputs.file(outFile)

    doLast {
        val factory = DocumentBuilderFactory.newInstance().apply {
            isValidating = false
            setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false
            )
        }

        val document = factory.newDocumentBuilder().parse(
            reportFile.get().asFile
        )

        val excludedClasses = setOf(
            "Main",
            "domain/GameMode",
            "domain/piece/PieceColor",
            "domain/piece/PieceType"
        )

        var missed = 0
        var covered = 0

        val classes = document.getElementsByTagName("class")

        for (classIndex in 0 until classes.length) {
            val classElement = classes.item(classIndex) as Element
            val className = classElement.getAttribute("name")

            if (className.startsWith("view/")
                || className in excludedClasses) {
                continue
            }

            val children = classElement.childNodes

            for (childIndex in 0 until children.length) {
                val counter = children.item(childIndex) as? Element
                    ?: continue

                if (counter.tagName != "counter") {
                    continue
                }

                if (counter.getAttribute("type") == "COMPLEXITY") {
                    missed += counter.getAttribute("missed").toInt()
                    covered += counter.getAttribute("covered").toInt()
                }
            }
        }

        val total = missed + covered

        val color = when {
            missed == 0 -> "brightgreen"
            total == 0 -> "lightgrey"
            missed * 4 <= total -> "yellow"
            missed * 2 <= total -> "orange"
            else -> "red"
        }

        val json =
            """{"schemaVersion":1,"label":"missed complexity","message":"$missed / $total","color":"$color"}"""

        outFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(json)
        }

        println("Complexity badge: $json")
    }
}