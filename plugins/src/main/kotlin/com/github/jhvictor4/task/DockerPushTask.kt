package com.github.jhvictor4.task

import com.github.jhvictor4.task.models.dockerImageName
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class DockerPushTask : DefaultTask() {
    @TaskAction
    fun pushDocker() {
        project.exec {
            val buildImage = project.dockerImageName
            logger.error("pushing $buildImage ......")
            commandLine("docker", "push", buildImage)
        }
    }
}
