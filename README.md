# Calculator Server & Client

## 1. Requirements
1-1. Using Java socket API with server-client network

1-2. Four basic arithmetic operation (+, -, *, /)

1-3. Server may be either answer or error message

1-4. Server must handle multiple client at a time

1-5. Server IP address & port number is stored with text file (e.g. server_info.dat) (if not exist, use default)

1-6. Protocol must base on ASCII-code


## 2. Report & Protocol
### 2-1. This Protocol based on HTML protocol (ex. 200 ok) <https://developer.mozilla.org/en-US/docs/Web/HTTP/Status>

### 2-2. Protocol

Protocol have "Number / Message".

Number can expression state like them
- 000 : divided by 0
- 100 : calculation
- 200 : OK (Right Respond)
- 400 : Bad Request (Undefine Message form)

Message form must be "number operation number"

## 3. Running


## 4. History
23.11.04


Build Protocol & Conceptual design


## 5. Output


## 6. Reference
6-1. HTML protocol <https://developer.mozilla.org/en-US/docs/Web/HTTP/Status>
