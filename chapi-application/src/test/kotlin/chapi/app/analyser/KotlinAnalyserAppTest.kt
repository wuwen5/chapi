package chapi.app.analyser

import chapi.app.analyser.config.ChapiConfig
import chapi.app.analyser.config.Language
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.test.assertEquals

internal class KotlinAnalyserAppTest {
    @Test
    fun `should return nodes of the sample code file`() {
        val config = ChapiConfig(language = Language.KOTLIN)
        val resource = this.javaClass.classLoader.getResource("languages/kotlin")!!
        val path = Paths.get(resource.toURI()).toFile()

        val nodeInfos = KotlinAnalyserApp(config).analysisNodeByPath(path.absolutePath)

        Assertions.assertEquals(nodeInfos.size, 1)
        Assertions.assertEquals(nodeInfos[0].FilePath.endsWith("resources/test/languages/kotlin/Hello.kt"), true)
    }

    @Nested
    inner class DryRun {
        private val relativePath = Paths.get("../../archguard-backend/src/main/kotlin")

        @Test
        fun `should parse all the files in ArchGuard`() {
            val config = ChapiConfig(language = Language.KOTLIN)
            val path = relativePath.resolve("com/thoughtworks/archguard/metrics/controller").toFile()
            val nodeInfos = KotlinAnalyserApp(config).analysisNodeByPath(path.absolutePath)

            assertEquals(nodeInfos.size, 2)
        }
    }
}
