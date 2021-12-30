package com.github.jhvictor4.plugin

import com.github.jhvictor4.common.Utils.validOrNull
import com.github.jhvictor4.task.DockerBuildTask
import com.github.jhvictor4.task.DockerPushTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType

class DockerPlugIn : Plugin<Project> {
    companion object {
        private const val JAVA_VERSION = "11"
        private const val GRADLE_VERSION = "7.0.2"
        private const val DEFAULT_KOTLIN_VERSION = "1.5.21"

        private const val TASK_GROUP_NAME = "docker"
        private const val DOCKER_BUILD_TASK_NAME = "buildDocker"
        private const val DOCKER_PUSH_TASK_NAME = "pushDocker"
    }

    override fun apply(project: Project) {
        val dockerBuildTask = project.createTask(DOCKER_BUILD_TASK_NAME, DockerBuildTask::class.java, group = TASK_GROUP_NAME)
        val dockerPushTask = project.createTask(DOCKER_PUSH_TASK_NAME, DockerPushTask::class.java, group = TASK_GROUP_NAME)
        project.run {
            repositories {
                mavenCentral()
            }
            tasks.withType<Wrapper> {
                gradleVersion = GRADLE_VERSION
            }
            apply<JavaPlugin>()
            apply(plugin = "kotlin")
            afterEvaluate {
                val buildTask = tasks.findByName("build")
                dockerBuildTask.dependsOn(buildTask)
            }
        }
    }
}

private fun <T : Task> Project.createTask(name: String, type: Class<T>, group: String? = null): T =
    tasks.create(name, type)
        .apply { setGroup(group) }