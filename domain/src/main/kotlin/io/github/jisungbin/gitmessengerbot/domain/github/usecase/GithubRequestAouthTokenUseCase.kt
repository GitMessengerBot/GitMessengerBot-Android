package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

private typealias BaseGithubRequestAouthTokenUseCase = BaseUseCase<String, Flow<Result<GithubAouth>>>

class GithubRequestAouthTokenUseCase(
    private val githubRepository: GithubRepository
) : BaseGithubRequestAouthTokenUseCase {
    override suspend fun invoke(parameter: String) =
        githubRepository.requestAouthToken(requestCode = parameter)
}
