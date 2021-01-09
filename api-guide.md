# API-Guide
GitMessengerBot Api 가이드
> 아직 모든 Api의 테스트가 진행되지 않았습니다! <br/>
> Api 사용에 있어서 버그가 발생할 수 있습니다.

-----
# V8
## Api
runRhino(source: String) <br/><br/>

## Bot
reply(room: String, message: String) <br/>
replyShowAll(room: String, message: String, message2: String)<br/><br/>

## File
save(title: String, content: String) <br/>
read(title: String, default: String) <br/><br/>

## Image
getLastImage() <br/>
getProfileImage() <br/><br/>

## Log
test(any: Any) <br/>
e(value: String) <br/>
d(value: String) <br/>
i(value: String) <br/><br/>

## UI
toast(message: String) <br/>
notification(title: String, message: String, id: Int) <br/><br/>

# Rhino
## App
getContext()
