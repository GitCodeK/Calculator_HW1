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
<img width="80%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/2f05a86f-b6c4-4ad0-8bae-c62330058f40">

### System overview
<pre><code>
1. Run “Server”
	- With “Address” get Address information in “server_info.dat”.
	- If not exist “server_info.dat”, “Address” is default(ip=127.0.0.1, port=1234).
	- Server take information with “Address.get_ip(), Address.get_port”
2. “Server” wait during coming to client with listener
3. Run “Client”
	- Same to “Server”, Run with “Address”
4. If “Server” listen “Client”, “Server create “thread” & match “Client” (”Server maintain listenning)
5. “Client” get Mathematical expression from user & Using “Translation.MathToMessage”, “Translation.MessagetoASCII” make that into Message (with protocol)
6. “Client” propagation Message to “thread”
7. “thread” get ASCII Message, Translation ASCII to String Message (Translation.ASCIItoString)
8. “thread” Translate Message & Calculate & Catch Exception & Make repond Message (Translatoin.MessagetoMessage)
9. “thread” using Translation.MessagetoASCII propagation Message to “Client”
10 “Client” get Message & Translation Message with “Translation.MessagetoMessage” & Display result
11. Close the connection
</code></pre>


## 4. Running
<img width="80%" src="https://github.com/GitCodeK/Calculator_HW1/assets/123552750/ef77c156-a622-45c2-9a54-b8146a09c621">


## 5. History
### 23.11.04
- Build Protocol & Conceptual design

### 23.11.05
- Draw class model & Start server making

### 23.11.06
- Make Server & Address class

### 23.11.07
- Make Client & Translation class

### 23.11.08
- Make Diagram & footnote


### 23.11.12
- Write annotation


## 6. Reference
6-1. HTML protocol <https://developer.mozilla.org/en-US/docs/Web/HTTP/Status>
