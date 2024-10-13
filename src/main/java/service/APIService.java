package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.URL;

public class APIService {
    // Open API의 기본 URL
    private static String API_URL = "http://openapi.seoul.go.kr:8088/58744d6d676b686a3935625a637953/json/TbPublicWifiInfo/";
    // OkHttpClient 객체를 사용해 HTTP 요청을 보내기 위한 클라이언트 생성
    private static OkHttpClient okHttpClient = new OkHttpClient();


    // Public Wi-Fi의 총 개수를 구하는 메서드
    public static int WifiTotalCnt() throws IOException { // Wi-Fi 개수 구하기
        int cnt = 0;

        // API URL에서 1부터 1까지(첫 번째 레코드) 데이터를 요청함
        URL url = new URL(API_URL + "1/1");

        // URL 요청을 위한 Request 객체 생성 (GET 요청)
        Request.Builder builder = new Request.Builder().url(url).get();

        // Request를 OkHttpClient를 통해 실행하여 응답을 받음
        Response response = okHttpClient.newCall(builder.build()).execute();

        try {
            // 응답이 성공적일 경우 (HTTP 상태 코드 200)
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body(); // 응답 본문을 받음

                if (responseBody != null) {
                    // 응답 본문을 JSON으로 변환
                    JsonElement jsonElement = JsonParser.parseString(responseBody.string());

                    // JSON 데이터에서 "list_total_count" 필드를 통해 전체 Wi-Fi 개수를 추출
                    cnt = jsonElement.getAsJsonObject()
                            .get("TbPublicWifiInfo")
                            .getAsJsonObject()
                            .get("list_total_count")
                            .getAsInt();

                    System.out.println("찾은 와이파이 개수 = " + cnt); // Wi-Fi 개수 출력
                }

            } else {
                System.out.println("API 호출 실패: " + response.code()); // API 호출 실패 시 HTTP 상태 코드 출력
            }

        } catch (Exception e) {
            e.printStackTrace(); // 예외가 발생하면 에러 출력
        }

        return cnt; // 전체 Wi-Fi 개수 반환
    }

    // Public Wi-Fi 데이터를 가져와서 DB에 저장하는 메서드
    public static int getPublicWifiJson() throws IOException {
        int totalCnt = WifiTotalCnt(); // Wi-Fi의 전체 개수를 먼저 구함
        int start = 1, end = 1; // 데이터를 가져올 시작과 끝 인덱스
        int count = 0; // DB에 저장된 Wi-Fi 레코드 수

        try {
            // 전체 Wi-Fi 데이터를 1000개 단위로 나누어 가져옴
            for (int i = 0; i <= totalCnt / 1000; i++) {
                // start와 end 값을 1000개 단위로 설정
                start = 1 + (1000 * i);
                end = (i + 1) * 1000;

                // 해당 범위의 데이터를 가져올 URL 생성
                URL url = new URL(API_URL + start + "/" + end);

                // Request 객체를 생성하고, OkHttpClient로 요청을 실행
                Request.Builder builder = new Request.Builder().url(url).get();
                Response response = okHttpClient.newCall(builder.build()).execute();

                if (response.isSuccessful()) { // 응답이 성공적일 경우
                    ResponseBody responseBody = response.body();

                    if (responseBody != null) {
                        // 응답 본문을 JSON으로 파싱
                        JsonElement jsonElement = JsonParser.parseString(responseBody.string());

                        // JSON 배열을 추출 (Wi-Fi 정보가 배열로 담겨 있음)
                        JsonArray jsonArray = jsonElement.getAsJsonObject()
                                .get("TbPublicWifiInfo")
                                .getAsJsonObject()
                                .get("row")
                                .getAsJsonArray();

                        // Wi-Fi 데이터를 DB에 저장하고, 저장한 레코드 수를 누적
                        count += WifiService.insertWifi(jsonArray);

                    } else {
                        System.out.println("API 호출 실패: " + response.code()); // 응답 본문이 없을 경우 에러 메시지 출력
                    }
                } else {
                    System.out.println("API 호출 실패: " + response.code()); // 응답이 실패할 경우 상태 코드 출력
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외가 발생하면 에러 메시지 출력
        }

        return count; // 저장된 Wi-Fi 레코드 수 반환
    }
}