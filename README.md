# People application - UI tests

UI tests with Selenium.

Tested application: https://github.com/alazarska/people-application

## Configuration

| Property         | Description                                                                     |
|------------------|---------------------------------------------------------------------------------|
| application.url  | URL to application.                                                             |
| browser.name     | Browser name used in tests. <br/> Options: chrome, firefox, edge.               |
| images.directory | This is path to the directory, where people's avatars used in tests are stored. |

Properties can be set in two ways:

1. Through config.properties file. (e.g. `application.url`)
2. Through environment variables according to the scheme `SELENIUMTEST_{property name}`, where property name with upper
   case separated by underscore. (e.g. `SELENIUMTEST_APPLICATION_URL`)
