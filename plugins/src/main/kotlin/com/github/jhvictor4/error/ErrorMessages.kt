package com.github.jhvictor4.error

object ErrorMessages {

    const val REPOSITORY_NOT_FOUND =
        "레포지토리 이름을 찾을 수 없습니다. gradle.properties 내부 docker.repository 안에 정보가 담겨야 합니다."

    const val CURRENT_BRANCH_NOT_FOUND =
        "현재 브랜치명을 찾을 수 없습니다."
}