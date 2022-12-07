package weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class WeatherMain {
    private static final Weather w = new Weather(); // 날씨 데이터를 받아오기 위한 w 객체

    public static void main(String[] args) {
        ArrayList<MemberDTO> memberList = new ArrayList<>(); // 회원 정보를 저장하기 위한 ArrayList타입의 memberList
        ArrayList<WeatherDTO> weatherList = new ArrayList<>(); // 회원의 날씨 정보를 저장하기 위한 ArrayList타입의 weatherList

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        w.checkWeather(); // w 객체의 checkWeather() 메소드를 작동시켜 날씨정보를 객체에 기입

        Scanner sc = new Scanner(System.in);

        boolean isUser = false;
        boolean isEnd = false;

        int index;

        try {
            // DBConnection_mysql 클래스의 getConnection() 메소드를 사용하여
            // Connection 객체에 DB 정보 삽입
            conn = DBConnection_mysql.getConnection();

            // PrepareStatement를 사용하여 테이블 데이터 조회 및 memberList 클래스에 MemberDTO형식으로 데이터 삽입
            pstmt = conn.prepareStatement("select id, pwd, mDate from member order by mDate asc");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                index = 1;
                String id = rs.getString(index++);
                String pwd = rs.getString(index++);
                Date mDate = rs.getDate(index);

                memberList.add(new MemberDTO(id, pwd, mDate));
            }

            // PrepareStatement를 사용하여 테이블 데이터 조회 및 weatherList 클래스에 WeatherDTO형식으로 데이터 삽입
            pstmt = conn.prepareStatement("select id, location, weather, temperature, wDate from weather");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                index = 1;
                String id = rs.getString(index++);
                String location = rs.getString(index++);
                String weather = rs.getString(index++);
                String temperature = rs.getString(index++);
                Date wDate = rs.getDate(index);

                weatherList.add(new WeatherDTO(id, location, weather, temperature, wDate));
            }

            // 주 기능을 반복하는 While문
            while (!isUser) {
                System.out.print("아이디 : ");
                String id = sc.next();
                System.out.print("비밀번호 : ");
                String pwd = sc.next();

                for (MemberDTO model : memberList) {
                    if (id.equals(model.getId())) {
                        if (pwd.equals(model.getPwd())) {
                            isUser = true;
                            System.out.println(id + "님 환영합니다.");

                            while (!isEnd) {
                                System.out.println("1. 현재 날씨 출력 / 2. 현재 날씨 저장 / 3. 계정 날씨 조회 / 4. 종료");
                                String input = sc.next();

                                // 사용자의 입력 정보에 따라 데이터를 출력
                                switch (input) {
                                    case "1":
                                        // w객체에 삽입되어 있는 정보를 Weather 클래스에 있는 get 메소드로 반환
                                        System.out.println("지역 : " + w.getCurrentLocation());
                                        System.out.println("날씨 : " + w.getCurrentWeather());
                                        System.out.println("기온 : " + w.getCurrentTemperature());
                                        break;
                                    case "2":
                                        // PrepareStatement를 사용하여 아래의 sql문을 기입한 후 DB에 데이터 삽입
                                        String sql = "insert into weather (id, location, weather, temperature, wDate) values(?, ?, ?, ?, ?)";

                                        pstmt = conn.prepareStatement(sql);

                                        pstmt.setString(1, id);
                                        pstmt.setString(2, w.getCurrentLocation());
                                        pstmt.setString(3, w.getCurrentWeather());
                                        pstmt.setString(4, w.getCurrentTemperature());
                                        pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

                                        pstmt.executeUpdate();
                                        break;
                                    case "3":
                                        // 계정정보를 조회하는 case
                                        // 구현 필요
                                        break;
                                    case "4":
                                        // 사용자의 입력에 따라 주 While문 탈출
                                        isEnd = true;
                                        break;
                                    default:
                                        // 사용자의 입력이 잘못되었을 때 실행됨
                                        System.out.println("잘못된 입력입니다.");
                                }
                            }
                        }
                    }
                }
                // 사용자 입력의 id나 비밀번호가 맞지 않았을 때 작동됨
                if (!isUser) {
                    System.out.println("아이디나 비밀번호가 잘못되었습니다.");
                    continue;
                }

                conn.close();
                pstmt.close();
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }
}
