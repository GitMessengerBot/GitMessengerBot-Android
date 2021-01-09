# API-Guide
GitMessengerBot Api 가이드

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
