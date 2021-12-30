package com.github.jhvictor4.task.models

import com.github.jhvictor4.common.Utils.result
import com.github.jhvictor4.common.Utils.validOrNull
import com.github.jhvictor4.error.ErrorMessages.CURRENT_BRANCH_NOT_FOUND
import com.github.jhvictor4.error.ErrorMessages.REPOSITORY_NOT_FOUND
import org.gradle.api.Project as GradleProject
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream

val GradleProject.dockerImageName: String
    get() = dockerInfo.getImageNameOf(this)

val GradleProject.dockerInfo: DockerInfo
    get() {
        val repository: String? = (project.properties["docker.repository"] as String?).takeIf { it?.isNotEmpty() ?: false }
        val tag: String? = (project.properties["docker.tag"] as String?).takeIf { it?.isNotEmpty() ?: false }

        return DockerInfo(repo = repository, tag = tag)
    }

data class DockerInfo(
    val repo: String?,
    val tag: String?,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val stdOut = ByteArrayOutputStream()

    fun getImageNameOf(project: GradleProject): String {
        requireNotNull(repo) { REPOSITORY_NOT_FOUND }
        return "${repo}:${tag.validOrNull ?: currentBranchNameOf(project)}"
    }

    private fun currentBranchNameOf(project: GradleProject): String =
        runCatching {
            project.exec {
                standardOutput = stdOut
                commandLine("git", "branch", "--show-current")
            }
            stdOut.result
        }.onFailure {
            logger.error(CURRENT_BRANCH_NOT_FOUND)
        }.getOrElse {
            "latest"
        }
}