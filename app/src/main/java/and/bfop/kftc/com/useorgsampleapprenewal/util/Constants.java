package and.bfop.kftc.com.useorgsampleapprenewal.util;

/**
 * Created by LeeHyeonJae on 2017-02-21.
 */
public interface Constants {

    String ACTIONBAR_TITLE = "ACTIONBAR_TITLE";

    /**
     * SharedPreferences 설정 이름
     */
    String APP_SETTING = "APP_SETTING"; // 이용기관샘플앱에서 사용하는 SharedPreference 의 namespace

    /**
     * 호출서버 환경의 선택지
     */
    String ENV_TEST = "TEST"; // 테스트서버
    String ENV_PRD = "PRD"; // 운영서버

    String ENV_NAME_TEST = "테스트서버";
    String ENV_NAME_PRD = "운영서버";

    /**
     * 기본 호출서버 환경
     *      - SharedPreferences 에 ENV가 저장되어 있지 않을 경우 사용할 기본값.
     */
    String ENV_DEFAULT = ENV_TEST ;

    /**
     * API 호출 BASE URI
     */
    String API_BASE_URI_TEST = "https://testapi.open-platform.or.kr"; // 테스트
    String API_BASE_URI_PRD = "https://openapi.open-platform.or.kr"; // 운영

    /**
     * 오픈플랫폼 APP 호출용 SCHEME
     *      - 20170320 현재, 오픈플랫폼앱에서는 테스트환경과 운영환경을 구분하지 않고 있다.
     *        (오픈플랫폼 앱의 테스트버전을 설치하면 테스트서버와 통신한다고 함)
     */
    String APP_SCHEME_TEST = "kftcbfop://"; // 테스트
    String APP_SCHEME_PRD = "kftcbfop://"; // 운영


    //================================================== 사용자정의 설정 기본값 - start ==================================================
    // 기본 값은 여기에 정의된 것을 사용하지만, SharedPreferences 를 사용하여 수정한 정보를 영구적으로 단말에 저장할 수 있다.
    /**
     * 오픈플랫폼에서 발급한 이용기관의 앱의 Key 기본값
     */
    String APP_KEY_TEST = "l7xx2387628cf42a4845b221f88029ea5a0a"; // kftcedu00
    String APP_KEY_PRD = "l7xx2387628cf42a4845b221f88029ea5a0a"; // kftcedu00

    /**
     * 오픈플랫폼에서 발급한 이용기관의 앱 Client Secret 기본값
     */
    String APP_SECRET_TEST = "75518cc321b841059889d0e40f4d1f0b"; // kftcedu00
    String APP_SECRET_PRD = "75518cc321b841059889d0e40f4d1f0b"; // kftcedu00

    /**
     * 오픈플랫폼에 등록된 이용기관 앱의 Redirect URL (웹 방식 호출의 경우) 기본값
     */
    //    String WEB_CALLBACK_URL_TEST = "http://localhost:8090/openapi/test/callback.html"; // kftcedu00
    String WEB_CALLBACK_URL_TEST = "https://developers.open-platform.or.kr/tpt/test/getAuthCode"; // kftcedu00
    String WEB_CALLBACK_URL_PRD = "http://localhost:8090/openapi/test/callback.html"; // kftcedu00

    /**
     * 오픈플랫폼에 등록된 이용기관 앱의 Redirect URL (앱 방식 호출의 경우) 기본값
     */
    String APP_CALLBACK_URL_TEST = "bfoptest://result"; // 테스트
    String APP_CALLBACK_URL_PRD = "bfoptest://result"; // 운영

    /**
     * 조회서비스 or 출금서비스 범위 지정자 기본값
     */
    String SCOPE_TEST = "inquiry"; // 테스트
    String SCOPE_PRD = "inquiry"; // 운영
    //================================================== 사용자정의 설정 기본값 - end ====================================================


    String BTN_NAME_FOLD = "접음";
    String BTN_NAME_UNFOLD = "펼침";
}