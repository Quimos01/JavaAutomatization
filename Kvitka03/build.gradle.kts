plugins {
    id("java")
}

group = "ua.com.kvitka"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.14.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

apply<MyPlugin>()

tasks.register("cleanBuildCache") {
    group = "custom"
    description = "Видаляє папку build/ і показує скільки місця звільнено"

    doLast {
        val buildDir = layout.buildDirectory.asFile.get()

        if (buildDir.exists()) {
            val totalBytes = buildDir.walkTopDown()
                .filter { it.isFile }
                .sumOf { it.length() }

            buildDir.deleteRecursively()

            val sizeStr = when {
                totalBytes >= 1_048_576 -> "%.1f MB".format(totalBytes / 1_048_576.0)
                totalBytes >= 1_024     -> "%.1f KB".format(totalBytes / 1_024.0)
                else                    -> "$totalBytes bytes"
            }

            println("Deleted build/ cleared $sizeStr")
        } else {
            println("build/ is absent")
        }
    }
}