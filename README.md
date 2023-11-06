# Calculator Server & Client

## 1. Requirements
<pre><code>
  1. Using Java socket API with server-client network
  2. Four basic arithmetic operation (+, -, *, /)
  3. Server may be either answer or error message
  4. Server must handle multiple client at a time
  5. Server IP address & port number is stored with text file (e.g. server_info.dat) (if not exist, use default)
  6. Protocol must base on ASCII-code
</code></pre>
  

## 2. Protocol
### 2-1. This Protocol based on HTML protocol 

<https://developer.mozilla.org/en-US/docs/Web/HTTP/Status>


### 2-2. Protocol

Protocol have "Length:State:Checksum:Message".

State can expression
- 100 : calculation
- 200 : OK (Right Respond)
- 400 : Bad Request (Undefine Mathematical expressions form)
- 400 : divided by 0
- 502 : Checksum Error

Use Checksum to ensure you're sending the correct value.
- Convert and sum Message character by character to ASCII.
- If the total is more than 4 digits, Delete all numbers with 4 or more digits.

Message form must be "number operation number operation ..."


## 3 Report


## 4. Running


## 5. History
### 23.11.04


Build Protocol & Conceptual design


### 23.11.05

Draw class model & Start server making


## 6. Output


## 7. Reference
7-1. HTML protocol <https://developer.mozilla.org/en-US/docs/Web/HTTP/Status>
