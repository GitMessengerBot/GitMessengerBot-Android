<image src="https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/dev/compose/presentation/src/main/res/drawable/ic_round_logo_150.png?raw=true" align="right" />

# GitMessengerBot 🚀 [![wakatime](https://wakatime.com/badge/github/GitMessengerBot/GitMessengerBot-Android.svg)](https://wakatime.com/badge/github/GitMessengerBot/GitMessengerBot-Android) ![ci](https://github.com/GitMessengerBot/GitMessengerBot-Android/actions/workflows/android-ci.yml/badge.svg)

타입스크립트, V8 엔진의 자바스크립트, 파이썬 그리고 `Git`을 지원하는 **최첨단 메신저 봇!**

-----

# 미리보기

Google Playstore에서 미리보기 스크린샷 확인 가능합니다.

# dev-0.1.1 다운로드

[dev/compose](https://github.com/GitMessengerBot/GitMessengerBot-Android/) 버전: [Google Playstore](https://play.google.com/store/apps/details?id=io.github.jisungbin.gitmessengerbot&hl=ko) (현재 업로드 검토중)

~~[alpha/xml](https://github.com/GitMessengerBot/GitMessengerBot-Android/tree/alpha/xml) 버전: [Google Playstore](https://play.google.com/store/apps/details?id=com.sungbin.gitkakaobot&hl=ko)~~ (작동 안할 가능성 높음, 비추천)

<!-- 개발 버전: [Github Release](https://github.com/GitMessengerBot/GitMessengerBot-Android/releases) -->

# dev-0.1.1

dev-0.1.1 버전입니다. **product 버전이 아닌, 개발중 버전이므로 모든 기능이 작동하지 않거나 미숙할 수 있습니다.**

iOS 공부와 취준을 위해 모든 안드로이드 프로젝트를 일시적으로 중단합니다. 따라서 dev 버전으로 미리 업로드 하게 되었습니다.

스크립트 에디터 입장시 발생하는 에러는 Git 연동이 완료 되면 더 이상 발생하지 않습니다. (추후 수정 예정)

이 프로젝트는 카톡봇이 망하지 않는 한, 무조건 끝까지 완성시킬 계획입니다.

# 기능

+ **다중 스크립트 및 기능**
  + 디버그
  + 수정
  + 전원
  + 로그켓
  + 마지막 작동 시간
+ **다양한 언어**
  + 타입스크립트
  + 자바스크립트 ([V8 엔진](https://chromium.googlesource.com/v8/v8))
  + 파이썬
  + 간편 자동응답 (블럭코딩)
+ **Git**
  + Commit and Push
  + Merge
+ **카카오톡 봇 전용 유사 깃허브** (kaven)

# 개발 스킬

0. 100% Kotlin [![awesome-kotlin](https://kotlin.link/awesome-kotlin.svg)](https://kotlin.link)
1. ~~100% pure function~~
2. Jetpack Compose
3. MVW Pattern
4. Coroutines, Flow
5. Retrofit, OkHttp
6. Dagger2 - Hilt
7. [ktlint](https://github.com/GitMessengerBot/GitMessengerBot-Android#ktlint-)
8. Room
9. Clean architecture
10. multi-module

# ktlint [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

- `import ordering` 프로젝트 단위 비활성화

-----

# API 가이드

> 준비중...

# 알려진 이슈

1. 아키텍처 구조 문제
   * ~~ViewModel은 platform independence 해야함~~ -> `LiveData` 대신에 `StateFlow`를 사용함으로써 해결
   * 일부 `context`를 갖는 Api 클래스에서 **메모리 누수** 발생 (해결 가능?)
2. Side-Effect
   * ~~`composable`에서 Side-Effect 발생~~?
3. ~~MVVM -> MVI 변경 필요~~ -> [#21](https://github.com/GitMessengerBot/GitMessengerBot-Android/pull/21) 완료

# 개발 아티클

1. [LiveData 대신에 StateFlow/SharedFlow를 사용해보자](https://jisungbin.medium.com/livedata-%EB%8C%80%EC%8B%A0%EC%97%90-stateflow-sharedflow%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90-c78b87ea4248)
2. [코틀린 inline에 대해 한 번에 알아보자](https://jisungbin.medium.com/%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-%EC%9D%B8%EB%9D%BC%EC%9D%B8%EC%97%90-%EB%8C%80%ED%95%B4-%ED%95%9C-%EB%B2%88%EC%97%90-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90-591c7da73219)
3. [아직도 MVVM? 이젠 MVI 시대!](https://jisungbin.medium.com/%EC%95%84%EC%A7%81%EB%8F%84-mvvm-%EC%9D%B4%EC%A0%A0-mvi-%EC%8B%9C%EB%8C%80-319990c7d60)
4. [모바일 UI 개발의 새로운 혁명(Jetpack Compose와 Swift UI를 이용한 새로운 UI 제작)](https://jisungbin.medium.com/%EB%AA%A8%EB%B0%94%EC%9D%BC-ui-%EA%B0%9C%EB%B0%9C%EC%9D%98-%EC%83%88%EB%A1%9C%EC%9A%B4-%ED%98%81%EB%AA%85-739c76a501b1)
5. [Composable끼리 ViewModel 공유하기](https://jisungbin.medium.com/composable%EB%81%BC%EB%A6%AC-viewmodel-%EA%B3%B5%EC%9C%A0%ED%95%98%EA%B8%B0-32ef53b24e8c)

# 개발 라이브러리
1. [ComposeTimeLineView](https://github.com/jisungbin/ComposeTimeLineView)
2. [Erratum](https://github.com/jisungbin/erratum)
3. [Logeukes](https://github.com/jisungbin/logeukes)

# 라이선스

개발: [지성빈](https://github.com/jisungbin) / 디자인: 구환 

GitMessengerBot © 2021 지성빈 & 구환. all rights reserved. 

GitMessengerBot은 [GPL-3.0](https://github.com/jisungbin/GitMessengerBot/blob/master/LICENSE) 라이선스를 따릅니다.

-----

# Happy Coding :)
