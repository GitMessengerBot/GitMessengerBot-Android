// 360
<!-- <image src="https://raw.githubusercontent.com/GitMessengerBot/GitMessengerBot-Android/stable/app/src/main/res/mipmap-xxhdpi/ic_launcher.png" align="right" /> -->

# GitMessengerBot [![wakatime](https://wakatime.com/badge/github/GitMessengerBot/GitMessengerBot-Android.svg)](https://wakatime.com/badge/github/GitMessengerBot/GitMessengerBot-Android) ![ci](https://github.com/GitMessengerBot/GitMessengerBot-Android/actions/workflows/android-ci.yml/badge.svg)

타입스크립트, V8 엔진의 자바스크립트, 파이썬 그리고 `Git`을 지원하는 **최첨단 메신저 봇!**

> 9월 말까지 완성 <br/>
> 스타 100개를 향해,,,~~

---

# 프로젝트 죽기 일보직전 -> ~~멱살잡고 살리는 중~~ -> 부활 성공!!

### 리펙토링 직전 커밋: [f2ab6d2](https://github.com/GitMessengerBot/GitMessengerBot-Android/tree/f2ab6d28cd2dc22babc39f0269bb2c5e27bf4b3b)

-----

# 미리보기

> 준비중...

# 다운로드 (알파버전 업로드 상태)

정식 버전: [Google Playstore](https://play.google.com/store/apps/details?id=com.sungbin.gitkakaobot&hl=ko)<br/>
개발 버전: [Github Release](https://github.com/GitMessengerBot/GitMessengerBot-Android/releases)

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
1. 100% pure function
2. Jetpack Compose
3. MVVM+MVI/Repository Pattern (MVI framework: [Orbit](https://github.com/orbit-mvi/orbit-mvi))
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
   * `presentation` 계층을 제외한 다른 계층은 `Android` 프레임워크를 알려서는 안됨? (`LiveData` -> `Flow` 변경 필요)
   * 일부 `context`를 갖는 Api 클래스에서 **메모리 누수** 발생 (해결 가능?)
2. Side-Effect
   * `composable`에서 Side-Effect 발생
3. MVVM -> MVI 변경 필요

# 라이선스

GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.<br/>
`GitMessengerBot`은 [GPL-3.0](https://github.com/jisungbin/GitMessengerBot/blob/master/LICENSE) 라이선스를 따릅니다.

-----

# Happy Coding :)
