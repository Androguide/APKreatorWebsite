###
Created by Louis Teboul (a.k.a Androguide.fr)
admin@androguide.fr
http://androguide.fr // http://pimpmyrom.org

Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
###

port = 5000
express = require("express")
nodemailer = require("nodemailer")
storage = require("node-persist")
exec = require("child_process").exec
app = express()
APP_NAME = undefined
PACKAGE_NAME = undefined
YOUTUBE_NAME = undefined
COLOR_SCHEME = undefined
ICON_LINK = undefined
WEBSITE = undefined
TWITTER = undefined
FACEBOOK = undefined
GPLUS = undefined
WELCOME_TITLE = undefined
WELCOME_DESC = undefined
API_KEY = undefined
response = undefined
randomHolder = undefined

storage.initSync()

###
The actual API to which we pass all the user input.
We use the passed-in values to the shell script which modifies a copy of the container and builds the user's APK.

We then redirect the user to a waiting page containing a loading spinner until the build is completed.

The API will generate a unique URL and pass it to the build script, which in turn will make a GET request at this URL
once the build is complete and pass it the unique name of the apk.
When this GET request is received by the API, it will redirect the user to the unique url of the apk, thus launching the download.
###

#  + '/website/:website'

###
This function generates the content of the config.xml file using the user input values
###
generateConfigXML = (appName, colorScheme, tabsAmount, tabNamesArray, youtubeUser, socialLinksArray, welcomeTitle, welcomeDesc, apiKey) ->
    configBegin = "<?xml version='1.0' encoding='utf-8' ?>" + "\n"
    configBegin += "<plugin>" + "\n" + "<config app-name='" + appName + "' \napp-color='" + colorScheme + "' \ntabs-amount='" + (tabsAmount + 1) + "'\n"
    i = 0

    while i <= tabsAmount
        configBegin += "tab" + i + "='" + tabNamesArray[i] + "' \n"
        i++
    configBegin += "youtube-user='" + youtubeUser + "' \n"
    configBegin += "welcome-title='" + welcomeTitle + "' \nwelcome-desc='" + welcomeDesc + "' \n"
    configBegin += "developer-key='" + apiKey + "'\n"
    configBegin += "website='" + socialLinksArray[0] + "' \ntwitter='" + socialLinksArray[1] + "' \nfacebook='" + socialLinksArray[2] + "' \ng-plus='http://plus.google.com/u/0/" + socialLinksArray[3] + "/posts' />" + "\n" + "</plugin>"
    console.log configBegin
    configBegin

###
Function to get the standard output of a shell command
###
puts = (error, stdout, stderr) ->
    console.log stdout
    return

###
This function will redirect the user to his compiled apk when the build is complete
###
done = (error, stdout, stderr) ->
    console.log stdout
    response.json
        apkUrl: "out/" + randomHolder + "/" + PACKAGE_NAME + ".apk"
        stdOut: stdout
        stdErr: stderr
    response.end()

    exec "rm -f " + randomHolder + ".sh"
    return

###
This function generates a filename-compliant random string used as the unique id of the request
@returns {string} -> the generated random string
###
getCompliantRandomness = ->
    random = Math.random().toString(36) + Math.random().toString(36).slice(-8).replace("/", "")
    random.replace ".", ""

###
@param string
@returns {XML}
@constructor
###
XMLizeString = (string) ->
    string = string.replace(/&/g, "&amp;")
    string = string.replace(/'/g, "&apos;")
    string = string.replace(/"/g, "&quot;")
    string

app.configure ->
    app.use express.static(__dirname + "/www")
    return

app.get "/app/:appname" + "/package/:package" + "/color/:color" + "/icon/:icon" + "/youtube/:youtube" + "/gplus/:gplus" + "/twitter/:twitter" + "/facebook/:facebook" +  "/website/:website" + "/welcome_title/:welcome_title" + "/welcome_desc/:welcome_desc" + "/api_key/:api_key", (req, res) ->
    res.set "Access-Control-Allow-Origin", "*"
    APP_NAME = req.params.appname
    PACKAGE_NAME = req.params.package
    YOUTUBE_NAME = req.params.youtube
    COLOR_SCHEME = req.params.color
    ICON_LINK = req.params.icon
    WEBSITE = req.params.website
    TWITTER = req.params.twitter
    FACEBOOK = req.params.facebook
    GPLUS = req.params.gplus
    WELCOME_TITLE = XMLizeString(req.params.welcome_title)
    WELCOME_DESC = XMLizeString(req.params.welcome_desc)
    API_KEY = req.params.api_key
    response = res
    configXml = generateConfigXML(APP_NAME, COLOR_SCHEME, 2, [
        "Welcome"
        "YouTube"
        "Google+"
    ], YOUTUBE_NAME, [
        WEBSITE
        TWITTER
        FACEBOOK
        GPLUS
    ], WELCOME_TITLE, WELCOME_DESC, API_KEY)
    random = getCompliantRandomness()
    iconurl = ICON_LINK.replace(/\//g, "\\/")
    randomHolder = random
    exec "echo \"" + configXml + "\" > " + random + ".xml", puts
    exec "sed -i 's/android:value=\"YOUR_API_KEY\"/android:value=\"" + API_KEY + "\"/g build/CONTAINER/APKreator/AndroidManifest.xml"
    exec "cp -f build_apk.sh " + random + ".sh", puts
    exec "sed -i 's/appname=\"placeholder\"" + "/appname=\"" + req.params.appname + "\"/g' " + random + ".sh", puts
    exec "sed -i 's/package=\"placeholder\"" + "/package=\"" + PACKAGE_NAME + "\"/g' " + random + ".sh", puts
    exec "sed -i 's/colorscheme=\"placeholder\"" + "/colorscheme=\"" + COLOR_SCHEME + "\"/g' " + random + ".sh", puts
    exec "sed -i 's/url=\"placeholder\"" + "/url=\"" + iconurl + "\"/g' " + random + ".sh", puts
    exec "sed -i 's/random=\"placeholder\"" + "/random=\"" + random + "\"/g' " + random + ".sh", puts
    exec "./" + random + ".sh", (error, stdout, stderr) ->
        console.log stdout
        response.json
            apkUrl: "out/" + randomHolder + "/" + PACKAGE_NAME + ".apk"
            stdOut: stdout
            stdErr: stderr
        response.end()

        exec "rm -f " + randomHolder + ".sh"

###
Sends an email with an account confirmation link/token
###
app.get "/send_confirmation/:email", (req, res) ->
    res.set "Access-Control-Allow-Origin", "*"
    rn = getCompliantRandomness()
    storage.setItem req.params.email, {token: rn, confirmed: false}

    mailOptions =
        from: "APKreator <mail.apkreator@gmail.com>" # sender address
        to: req.params.email # list of receivers
        subject: "Please Confirm Your Email Address ✔" # Subject line
        text: "Welcome to APKreator! To start creating your amazing app(s), please click on the link below to confirm your email address: http://localhost:5000/confirm_account/" + req.params.email + "/" + rn # plaintext body
        html: "<b>Welcome to APKreator!</b><br>To start creating your amazing app(s), please click on the link below to confirm your email address:
                         <br/><br/><center><a href=\"#\">http://localhost:5000/confirm_account/" + req.params.email + "/" + rn + "</a></center>"

    responseHolder =
        message: "Nope!"
    sent = false
    smtpTransport.sendMail mailOptions, (error, response) ->
        if error
            console.log error
        else
            responseHolder = response
            console.log "Message sent: " + response.message
            sent = true
        smtpTransport.close()

    res.json(email: "sent: " + sent)
    res.end()

###
Confirm an email by passing the email & token in the request, if the token matches the one stored for that email, the account is confirmed
###
app.get "/confirm_account/:email/:token", (req, res) ->
    stored = storage.getItem(req.params.email)
    unless stored
        res.set "Access-Control-Allow-Origin", "*"
        res.json error: "unknown email", status: "-404"
    else if stored.confirmed == true
        res.set "Access-Control-Allow-Origin", "*"
        res.json error: "account already confirmed", status: "-900"
    else if stored.token == req.params.token
        # the token sent by email matches the stored token for this particular email address, it's a win.
        storage.setItem req.params.email, {token: stored.token, confirmed: true}
        res.redirect "http://localhost:6004/#/"
    else
        res.set "Access-Control-Allow-Origin", "*"
        res.json error: "bad request (email & token don't match)", status: "-403"

###
Returns true if the user has confirmed his email, returns false otherwise
###
app.get "/is_confirmed/:email", (req, res) ->
    res.set "Access-Control-Allow-Origin", "*"
    stored = storage.getItem req.params.email
    if stored
        res.json(confirmed: stored.confirmed)
    else
        res.json(error: "unknown email", status: "-404")

# create reusable transport method (opens pool of SMTP connections)
smtpTransport = nodemailer.createTransport("SMTP",
    service: "Gmail"
    auth:
        user: "mail.apkreator@gmail.com"
        pass: "c4nn4815"
)

###
Launching the server on the defined port
###
app.listen port
console.log "Listening on port " + port