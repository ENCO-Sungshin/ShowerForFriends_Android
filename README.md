# ShowerForFriends_Android
안드로이드 어플리케이션 개발

[storage]
UserTable-dev
DisplayTable-dev

[function]
putUsersLambda-dev
powerDisplayLambda-dev

[api]
showerforfriendsapis

#..210808
https://docs.amplify.aws/guides/functions/connecting-a-rest-api/q/platform/android (참고 문서)
생성한 api - showerforfriendsapis
리소스(/display)의 ANY 메서드에 대하여 프록시 통합 사용을 해제**
매핑템플릿 application/json 을 추가 **
[amplifyconfiguration.json] - showerforfriendsapis의 authorizationType을 NONE으로 변경**->이렇게 하면 값이 잘 들어감.. 
[ConfirmSignUpActivity.java] - 이메일 인증 성공 시 UserTable-dev에 rest 형식으로 post

#..todo
showerforfriendsapi의 권한 설정, 프록시 통합이 뭔지 파악하고 적절한 방법 적용.


