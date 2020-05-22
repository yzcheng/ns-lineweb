# Read Me First
The project integrated LINE message-api and LINE web login to present process flow of company.


# Getting Started
When you clone the project, please import it with MAVEN project in eclipse or other IDEs.

function separated to several parts:

`src/main/java`
* idv.tw.nslineweb.robot.controller.CallbackController : the handler of the LINE message-api.
* idv.tw.nslineweb.application.controller.WebController the MVC Controller that copy from line-login-starter project.
* idv.tw.nslineweb.application.controller.APIController the API Controller that copy from line-login-starter project.
* idv.tw.nslineweb.infra.* : the infrastructures that copy from line-login-starter project.

`src/main/resource`
* application.properties : record the data source configuration, and the data source is use the heroku postgresql, it will ignore those properties.
     * spring.datasource.url
     * spring.datasource.username
     * spring.datasource.password 

# How to Deploy?
the project is base on heroku platform to deploy the project.
if you are Newbie, please follow below step by step to deploy for display normally.


### Method 1 ## 
 
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

### Method 2 ##
 
entry the <strong>Heroku platform</strong> web site.
1. register account in [heroku](https://dashboard.heroku.com/).
2. in heroku dashboard, click 'New' button and select 'Create new app', naming example is <strong>ns-lineweb-{????}</strong>.
3. entry app <strong>ns-lineweb-{????}</strong>, click <strong>Resources</strong> and add a add-ons <strong>Heroku Postgres(Hobby Dev - Free)</strong>

entry the <strong>LINE Developers</strong> web site.

1. register account in [LINE Developers](https://developers.line.biz/zh-hant/).

2. create Providers, naming is not important(it seems a name space for grouping all channel?).

3. create a <strong>Messaging API</strong>:
     * Set the <strong>{Webhook URL}</strong> as heroku app url:
          * ex: <strong>https://ns-lineweb-{????}.herokuapp.com/callback</strong>
     * Get <strong>{Channel secret}</strong> and save it.
     * Get <strong>{Channel access token}</strong> and save it.
     
4. create a <strong>LINE Login</strong>
     * Set the <strong>{Callback URL}</strong> as heroku app url:
          * ex: <strong>https://ns-lineweb-{????}.herokuapp.com/auth</strong>
     * Get <strong>{Channel ID}</strong> and save it.
     * Get <strong>{Channel secret}</strong> and save it.

go back to the <strong>Heroku platform</strong>

1. entry app <strong>ns-lineweb-{????}</strong>, click <strong>Settings</strong> to add <strong>Config Vars</strong> :
     * LINE_BOT_CHANNELSECRET : {Channel secret} 
     * LINE_BOT_CHANNELTOKEN :  {Channel access token}
     * LINECORP_PLATFORM_CHANNEL_CHANNELID : {Channel ID}
     * LINECORP_PLATFORM_CHANNEL_CHANNELSECRET : {Channel secret}
     * LINECORP_PLATFORM_CHANNEL_CALLBACKURL : {Callback URL}
     
2. install [The Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) and follow the [Heroku CLI Authentication](https://devcenter.heroku.com/articles/authentication) guide to get <strong>API token</strong><br>
(Use heroku <strong>authorizations:create</strong> for production apps, use <strong>heroku auth:token</strong> for development.) 

finally back to <strong>Eclipse IDE</strong>

1.  open <strong>pom.xml</strong> and find below block to modify heroku.appname as your actual appname. 

```xml
...
	<properties>
		<java.version>1.8</java.version>
		<retrofit2.groupId>com.squareup.retrofit2</retrofit2.groupId>
		<retrofit2.version>2.5.0</retrofit2.version>
		<heroku.appname>ns-lineweb</heroku.appname> <!-- change me-->
	</properties>
...
```
2. Run as <strong>Maven build...</strong>, in the dialog that appears, enter `heroku:deploy-war` in the “Goals” field, see the [link](https://devcenter.heroku.com/articles/deploying-java-applications-to-heroku-from-eclipse-or-intellij-idea)
3. you will also need to set the `HEROKU_API_KEY` environment variable for the task. Click the Environment tab, and select New... to create a new variable. Enter “HEROKU_API_KEY” for the name and your API key for the value. You can get your API key by running <strong>heroku auth:token</strong> at the command-line. 
4. After deployment, verify 
     1. you can open browser to see `https://ns-lineweb-{????}.herokuapp.com/` to try line login.
     2. scan Line Messaging API QR code to join channel, try enter `?` to trigger robot response.   

### Reference Documentation
For further reference, please consider the following sections:

* [Heroku: Cloud Application Platform](https://www.heroku.com/)
* [LINE Developers](https://developers.line.biz/zh-hant/)
* [Deploying Spring Boot Applications to Heroku](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku)
* [Deploying Java Applications to Heroku from Eclipse or IntelliJ IDEA](https://devcenter.heroku.com/articles/deploying-java-applications-to-heroku-from-eclipse-or-intellij-idea)

