package io.github.jisungbin.gitmessengerbot.domain.usecase.github

import io.github.jisungbin.gitmessengerbot.domain.repository.Result
import io.github.jisungbin.gitmessengerbot.domain.usecase.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.usecase.BaseUseCaseVararg
import kotlinx.coroutines.flow.Flow

typealias BaseGithubAouthServiceUseCase = BaseUseCaseVararg<String, Flow<Result<GithubAouthResponse>>>

class GithubAouthServiceUseCase(

) {
}