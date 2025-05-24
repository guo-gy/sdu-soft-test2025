*** Settings ***
Library           RequestsLibrary

*** Variables ***
${BASE_URL}       http://211.87.232.162:8080/app
${CONTENT_TYPE}    application/json

*** Test Cases ***
Test User Registration - Username Exists
    Create Session    mysession    ${BASE_URL}
    &{headers}=    Create Dictionary    Content-Type=${CONTENT_TYPE}
    &{body}=    Create Dictionary    username=testtest1    password=123    repassword=123    code=1234
    ${response}=    POST On Session    mysession    appRegister    json=${body}    headers=${headers}
    Should Be Equal As Strings    ${response.status_code}    200
    ${json_response}=    Set Variable    ${response.json()}
    Should Be Equal As Strings    ${json_response['success']}    ${FALSE}
    Should Be Equal As Numbers    ${json_response['code']}    20002
    Should Be Equal As Strings    ${json_response['message']}    用户名已存在

Test User Login - Password Error
    Create Session    mysession    ${BASE_URL}
    &{headers}=    Create Dictionary    Content-Type=${CONTENT_TYPE}
    &{body}=    Create Dictionary    username=test    password=wrongpassword
    ${response}=    POST On Session    mysession    /appLogin    json=${body}    headers=${headers}
    Should Be Equal As Strings    ${response.status_code}    200
    ${json_response}=    Set Variable    ${response.json()}
    Should Be Equal As Strings    ${json_response['success']}    ${FALSE}
    Should Be Equal As Numbers    ${json_response['code']}    20002
    Should Be Equal As Strings    ${json_response['message']}    密码错误
