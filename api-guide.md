# API-Guide
GitMessengerBot Api 가이드

-----
# V8
## Api
runRhino(source: String)

## Bot
reply(room: String, message: String)
replyShowAll(room: String, message: String, message2: String)

## File
save(title: String, content: String)
read(title: String, default: String)

## Image
getLastImage()
getProfileImage()

## Log
test(any: Any)
e(value: String)
d(value: String)
i(value: String)

## UI
toast(message: String)
notification(title: String, message: String, id: Int)

# Rhino
## App
getContext()
