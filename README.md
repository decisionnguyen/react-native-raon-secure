# react-native-raon-secure

## Getting started

`$ npm install react-native-raon-secure --save`

### iOS

1. 프로젝트 폴더 내 수신한 RSKSW2_license.rsl 을 넣습니다.
2. 프로젝트 선택 후 Signing & Capabilities 에서 Keychain Sharing을 추가 후 패키지 등록합니다.
3. plist에 하단과 같이 서버 정보를 입력합니다.
```
<key>RAON_SERVER_IP</key>
<string>서버아이피</string>
<key>RAON_SERVER_PORT</key>
<integer>포트번호</integer>
```

### Android

1. 프로젝트 폴더 내 app/src/main/assets 폴더내에 RSKSW2_license.rsl 을 넣습니다.
2. AndroidManifest.xml 에 하단과 같이 서버 정보를 입력합니다.
```
<meta-data android:name="RAON_SERVER_IP" android:value="서버아이피" />
<meta-data android:name="RAON_SERVER_PORT" android:value="포트번호" />
```


## Usage
```javascript
import RaonSecure from 'react-native-raon-secure';

...

await RaonSecure.clearTempDir();

```


|명령어|아규먼트|비고|
|----|------|---|
|clearTempDir| - |임시 폴더내 파일 제거|
|getItems| - |보유 인증서 출력|
|removeItem|subjectDn:string|인증서 삭제|
|getPath|subjectDn:string|인증서의 저장된 경로 가져오기|
|checkPassword|subjectDn:string, password:string|비밀번호가 일치하는지 확인|
|getReceiveCode| - |인증서 수신을 위한 코드발급 12자리|
|importCertify| - |인증서 수신처리|
