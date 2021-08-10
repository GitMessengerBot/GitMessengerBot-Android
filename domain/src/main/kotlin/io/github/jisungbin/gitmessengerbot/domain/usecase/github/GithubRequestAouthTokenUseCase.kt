package io.github.jisungbin.gitmessengerbot.domain.usecase.github

import io.github.jisungbin.gitmessengerbot.domain.model.github.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.repository.Result
import io.github.jisungbin.gitmessengerbot.domain.repository.github.GithubRepository
import io.github.jisungbin.gitmessengerbot.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow

private typealias BaseGithubRequestAouthTokenUseCase = BaseUseCase<String, Flow<Result<GithubAouth>>>

class GithubRequestAouthTokenUseCase(
    private val githubRepository: GithubRepository
) : BaseGithubRequestAouthTokenUseCase {
    override suspend fun invoke(parameter: String) =
        githubRepository.requestAouthToken(requestCode = parameter)
}
