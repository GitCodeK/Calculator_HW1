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
- 400 : divided by 0
- 401 : Bad Request (Undefine Mathematical expressions form)
- 502 : Checksum Error

Use Checksum to ensure you're sending the correct value.
- Convert and sum Message character by character to ASCII.
- If the total is more than 4 digits, Delete all numbers with 4 or more digits.

Message form must be "number operation number operation ..."


If there is exception, Server send message without edit to Client

## 3 Report


## 4. Running
<img width="45%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/ab814901-3079-4cf5-82a0-bfae31a2f0a1">
<img width="45%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/5a6be12f-49c8-4dcb-9b30-1cf683f2f90a">



<img width="30%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/ebf4588a-9285-4d22-aceb-2adfeeba95d3">
<img width="30%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/76e51cde-4dcd-4896-aa54-db7f4fca11fe">
<img width="30%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/85e11774-f1f5-496f-9b9b-83028cf268e8">

## 5. History
### 23.11.04
- Build Protocol & Conceptual design

### 23.11.05
- Draw class model & Start server making

### 23.11.06
- Make Server & Address class

### 23.11.07
- Make Client & Translation class


## 6. Reference
6-1. HTML protocol <https://developer.mozilla.org/en-US/docs/Web/HTTP/Status>
