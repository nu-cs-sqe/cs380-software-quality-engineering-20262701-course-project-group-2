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
    testImplementation("org.easymock:easymock:5.2.0")
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
    toolVersion = "10.12.4"
    isIgnoreFailures = false
    maxErrors = 0
    maxWarnings = 0
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = true
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

    reports.create("xml") {
        required = true
        outputLocation = layout.buildDirectory.file(
            "reports/spotbugs/spotbugs.xml"
        )
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude(coverageExclusions)
        }
    )

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
    outputFormats = setOf("HTML", "XML")
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

val mutationBadge by tasks.registering {
    group = "verification"
    description = "Writes badge data for the PIT mutation score"

    dependsOn("pitest")

    val reportFile = layout.buildDirectory.file(
        "reports/pitest/mutations.xml"
    )
    val outFile = layout.buildDirectory.file(
        "badges/mutation.json"
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

        val mutations = document.getElementsByTagName("mutation")
        val total = mutations.length
        var detected = 0

        for (index in 0 until mutations.length) {
            val mutation = mutations.item(index) as Element

            if (mutation.getAttribute("detected").toBoolean()) {
                detected++
            }
        }

        val score = if (total == 0) {
            100
        } else {
            detected * 100 / total
        }

        val color = when {
            score == 100 -> "brightgreen"
            score >= 80 -> "yellowgreen"
            score >= 60 -> "yellow"
            score >= 40 -> "orange"
            else -> "red"
        }

        val json =
            """{"schemaVersion":1,"label":"mutation score","message":"$score% ($detected / $total)","color":"$color"}"""

        outFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(json)
        }

        println("Mutation badge: $json")
    }
}

val coverageExclusions = listOf(
    "view/**",
    "Main.class",
    "domain/GameMode.class",
    "domain/piece/PieceColor.class",
    "domain/piece/PieceType.class"
)

val checkstyleBadge by tasks.registering {
    group = "verification"
    description = "Writes badge data for Checkstyle violations"

    val mainReport = layout.buildDirectory.file(
        "reports/checkstyle/main.xml"
    )
    val testReport = layout.buildDirectory.file(
        "reports/checkstyle/test.xml"
    )
    val outFile = layout.buildDirectory.file(
        "badges/checkstyle.json"
    )

    outputs.file(outFile)

    doLast {
        val reportFiles = listOf(
            mainReport.get().asFile,
            testReport.get().asFile
        ).filter { it.exists() }

        if (reportFiles.isEmpty()) {
            throw GradleException(
                "No Checkstyle XML reports were found."
            )
        }

        var violations = 0

        for (reportFile in reportFiles) {
            val factory = DocumentBuilderFactory.newInstance().apply {
                isValidating = false
                setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                    false
                )
            }

            val document = factory.newDocumentBuilder().parse(reportFile)
            violations += document.getElementsByTagName("error").length
        }

        val color = when {
            violations == 0 -> "brightgreen"
            violations <= 5 -> "yellow"
            violations <= 20 -> "orange"
            else -> "red"
        }

        val message = if (violations == 1) {
            "1 violation"
        } else {
            "$violations violations"
        }

        val json =
            """{"schemaVersion":1,"label":"Checkstyle","message":"$message","color":"$color"}"""

        outFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(json)
        }

        println("Checkstyle badge: $json")
    }
}

val spotbugsBadge by tasks.registering {
    group = "verification"
    description = "Writes badge data for SpotBugs findings"

    val reportFile = layout.buildDirectory.file(
        "reports/spotbugs/spotbugs.xml"
    )
    val outFile = layout.buildDirectory.file(
        "badges/spotbugs.json"
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

        val findings = document
            .getElementsByTagName("BugInstance")
            .length

        val color = when {
            findings == 0 -> "brightgreen"
            findings <= 5 -> "yellow"
            findings <= 20 -> "orange"
            else -> "red"
        }

        val message = if (findings == 1) {
            "1 finding"
        } else {
            "$findings findings"
        }

        val json =
            """{"schemaVersion":1,"label":"SpotBugs","message":"$message","color":"$color"}"""

        outFile.get().asFile.apply {
            parentFile.mkdirs()
            writeText(json)
        }

        println("SpotBugs badge: $json")
    }
}