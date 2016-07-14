# Rest SMS wrapper Spring Boot

## Configuration
| Key | Description |
|-----|----------|
| sms.rest.endpoint | The endpoint of the external SMS service |

| sms.rest.user | The username for the external SMS service |
| sms.rest.password | The password for the external SMS service |

----

These values are used to build the request sent to the external SMS service.
I works by using the endpoint and adding the parameters.
| Key | Description |
|-----|----------|
| sms.rest.mobileparameter | The query parameter that contains the phone number |
| sms.rest.messageparameter | The query parameter that contains the message |
| sms.rest.userparameter | The query parameter that contains the username |
| sms.rest.passwordparameter | The query parameter that contains the password |