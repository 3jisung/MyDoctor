package com.example.googlemap;

import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NetworkUtils {
    static Hospital[] getXmlData(String area, String local, String type) {
        StringBuffer buffer=new StringBuffer();
        ArrayList<Hospital> arr = new ArrayList<>();
        try {
            String servicekey = "bobBJaslaT9ettYkWSulj9rn7G%2BVxbpw9JKF5CQgQrl%2BM8AyHKvfpS6KiFzrZt%2BEZepVW0zZqtbbc%2FpOQcM7HA%3D%3D";
            StringBuilder queryUrl = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire" +
                    "?ServiceKey=" + servicekey);
            queryUrl.append("&" + URLEncoder.encode("Q0","UTF-8") + "=" + area); /*주소(시도)*/
            queryUrl.append("&" + URLEncoder.encode("Q1","UTF-8") + "=" + local); /*주소(시도)*/
//            queryUrl.append("&" + URLEncoder.encode("Q0","UTF-8") + "=" + URLEncoder.encode(area, "UTF-8")); /*주소(시도)*/
//            queryUrl.append("&" + URLEncoder.encode("Q1","UTF-8") + "=" + URLEncoder.encode(local, "UTF-8")); /*주소(시군구)*/
            queryUrl.append("&" + URLEncoder.encode("QZ","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")); /*CODE_MST의'H000' 참조(B:병원, C:의원)*/
//            queryUrl.append("&" + URLEncoder.encode("QD","UTF-8") + "=" + URLEncoder.encode("D001", "UTF-8")); /*CODE_MST의'D000' 참조(D001~D029)*/
//            queryUrl.append("&" + URLEncoder.encode("QT","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*월~일요일(1~7), 공휴일(8)*/
//        queryUrl.append("&" + URLEncoder.encode("QN","UTF-8") + "=" + URLEncoder.encode("삼성병원", "UTF-8")); /*기관명*/
//            queryUrl.append("&" + URLEncoder.encode("ORD","UTF-8") + "=" + URLEncoder.encode("NAME", "UTF-8")); /*순서*/
//            queryUrl.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
//            queryUrl.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /*목록 건수*/
//                "&Q0=%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C" +
//                "&Q1=%EC%A2%85%EB%A1%9C%EA%B5%AC&QZ=A&ORD=ADDR&pageNo=1&numOfRows=30";

            Log.e("*************", queryUrl.toString());

            URL url= new URL(queryUrl.toString());//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            String[] temp = new String[8];
            Hospital hos;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("dutyAddr")){
                            buffer.append("주소 : ");
                            xpp.next();
                            temp[0] = xpp.getText().toString();
                            buffer.append(xpp.getText());//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("dutyDivNam")){
                            buffer.append("병원 분류 : ");
                            xpp.next();
                            temp[1] = xpp.getText().toString();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("dutyMapimg")){
                            buffer.append("간이약도 :");
                            xpp.next();
                            temp[2] = xpp.getText().toString();
                            buffer.append(xpp.getText());//cpId
                            buffer.append("\n");
                        }
                        else if(tag.equals("dutyName")){
                            buffer.append("병원 이름 :");
                            xpp.next();
                            temp[3] = xpp.getText().toString();
                            buffer.append(xpp.getText());//cpNm
                            buffer.append("\n");
                        }
                        else if(tag.equals("dutyTel1")){
                            buffer.append("대표 전화 :");
                            xpp.next();
                            temp[4] = xpp.getText().toString();
                            buffer.append(xpp.getText());//
                            buffer.append("\n");
                        }
                        else if(tag.equals("dutyTel3")){
                            buffer.append("응급실 번호 :");
                            xpp.next();
                            temp[5] = xpp.getText().toString();
                            buffer.append(xpp.getText());//
                            buffer.append("\n");
                        }
                        else if(tag.equals("wgs84Lat")){
                            buffer.append("병원 위도 :");
                            xpp.next();
                            temp[6] = xpp.getText().toString();
                            buffer.append(xpp.getText());//csId
                            buffer.append("\n");
                        }
                        else if(tag.equals("wgs84Lon")){
                            buffer.append("병원 경도 :");
                            xpp.next();
                            temp[7] = xpp.getText().toString();
                            hos = new Hospital();
                            hos.setAddress(temp[0]);
                            hos.setType(temp[1]);
                            hos.setSimpleMap(temp[2]);
                            hos.setName(temp[3]);
                            hos.setMainNumber(temp[4]);
                            hos.setEmergencyNumber(temp[5]);
                            hos.setLatitude(Double.parseDouble(temp[6]));
                            hos.setLongitude(Double.parseDouble(temp[7]));
                            arr.add(hos);
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");

        Hospital[] hospitals = arr.toArray(new Hospital[arr.size()]);
        return hospitals;
//        return buffer.toString();//StringBuffer 문자열 객체 반환
    }
}