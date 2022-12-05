package weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class WeatherMain {
    private static final Weather w = new Weather();

    public static void main(String[] args) {
        ArrayList<MemberDTO> memberList = new ArrayList<>();
        ArrayList<WeatherDTO> weatherList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        w.checkWeather();

        Scanner sc = new Scanner(System.in);

        boolean isUser = false;
        boolean isEnd = false;

        int index;

        try {
            conn = DBConnection_mysql.getConnection();

            pstmt = conn.prepareStatement("select id, pwd, mDate from member order by mDate asc");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                index = 1;
                String id = rs.getString(index++);
                String pwd = rs.getString(index++);
                Date mDate = rs.getDate(index);

                memberList.add(new MemberDTO(id, pwd, mDate));
            }

            pstmt = conn.prepareStatement("select id, location, weather, temperature, wDate from weather order by wDate asc");
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
                                switch (input) {
                                    case "1":
                                        System.out.println("지역 : " + w.getCurrentLocation());
                                        System.out.println("날씨 : " + w.getCurrentWeather());
                                        System.out.println("기온 : " + w.getCurrentTemperature());
                                        break;
                                    case "2":
//                                        insertDB(conn, id, w.getCurrentLocation(), w.getCurrentWeather(), w.getCurrentTemperature() + "");
                                        String sql = "insert into weather (id, location, weather, temperature, wDate) values(?, ?, ?, ?, ?)";

                                        pstmt = conn.prepareStatement(sql);

                                        pstmt.setString(1, id);
                                        pstmt.setString(2, w.getCurrentLocation());
                                        pstmt.setString(3, w.getCurrentWeather());
                                        pstmt.setString(4, w.getCurrentTemperature());
                                        pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

                                        pstmt.executeUpdate(sql);
                                        break;
                                    case "3":
                                        break;
                                    case "4":
                                        isEnd = true;
                                        break;
                                    default:
                                        System.out.println("잘못된 입력입니다.");
                                }
                            }
                        }
                    }
                }
                if (!isUser) {
                    System.out.println("아이디나 비밀번호가 잘못되었습니다.");
                    continue;
                }

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

        // 기본 정보 출력
//        for (memberDTO model : memberList) {
//            System.out.println("아이디 : " + model.getId() +
//                    ", 비밀번호 : " + model.getPwd() +
//                    ", 갱신일 : " + model.getmDate());
//        }
    }
}
