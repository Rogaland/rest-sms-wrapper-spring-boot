# Rest SMS wrapper Spring Boot

[![Build Status](https://travis-ci.org/Rogaland/rest-sms-wrapper-spring-boot.svg?branch=master)](https://travis-ci.org/Rogaland/rest-sms-wrapper-spring-boot)

## Installation

_build.gradle_
```
compile('no.rogfk:rest-sms-wrapper-spring-boot:0.0.3')
```

## Usage

Enable the Sms Wrapper by adding `@EnableSmsWrapper` on the same class containing the `@SpringBootApplication` annotation.
You can configure a the mode by either setting the property `sms.mode` or in the `@EnableSmsWrapper` annotation.  
The options are:
* __SEND_IMMEDIATELY__ - Will send each SMS immediately as they are received
* __MANUAL_QUEUE__ - Queues up all the requests until a send command (on the `ManualQueueStrategy` bean) is called.

### Configuration
| Key | Description |
|-----|----------|
| sms.rest.endpoint | The endpoint of the external SMS service |
| sms.rest.user | The username for the external SMS service |
| sms.rest.password | The password for the external SMS service |
| sms.rest.mobileparameter | The query parameter that contains the phone number |
| sms.rest.messageparameter | The query parameter that contains the message |
| sms.rest.userparameter | The query parameter that contains the username |
| sms.rest.passwordparameter | The query parameter that contains the password |
| jasypt.encryptor.password | Encrypts/decrypts the password property. Is usually set as an environment variable when starting the application |
| sms.mode | Options: SEND_IMMEDIATELY (default), MANUAL_QUEUE |