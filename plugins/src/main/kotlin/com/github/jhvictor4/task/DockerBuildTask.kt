package com.github.jhvictor4.task

import com.github.jhvictor4.task.models.dockerImageName
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.getByName
import java.io.File

/***
 *  buildDocker command will automatically execute bootJar and build docker container with the output from it.
 *  Container's tag will be customized with docker.tag in gradle.properties
 */
open class DockerBuildTask : DefaultTask() {
    @TaskAction
    fun buildDocker() {
        val tempDir = project.mkdir(File(project.buildDir, "tmp"))
        val tempDockerFile = File(tempDir, "Dockerfile")
        tempDockerFile.writeText(dockerfileTemplate)

        project.copy {
            val jarTask = project.tasks.getByName<Jar>("bootJar")
            from(jarTask.archiveFile) { rename { "app.jar" } }
            into(tempDir)
        }

        val buildImage = project.dockerImageName
        project.exec {
            workingDir(tempDir)
            commandLine("docker", "build", "-t", buildImage, ".")
        }
    }
}

private val dockerfileTemplate =
    DockerBuildTask::class.java.classLoader
        .getResourceAsStream("Dockerfile")!!
        .use { it.reader().readText() }