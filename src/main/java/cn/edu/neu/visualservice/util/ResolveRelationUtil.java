package cn.edu.neu.visualservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResolveRelationUtil {
    private static final String SQL_READ = "select inter_id as interId, rid as fRid, turn_dir_no_list as turnDirNoList from dwd_tfc_rltn_wide_inter_lane;";
    private static final String SQL_WRITE = "select inter_id as interId, rid as fRid, turn_dir_no_list as turnDirNoList from dwd_tfc_rltn_wide_inter_lane;";
    public static final String JDBC_URL = "jdbc:mysql://mysql-svc:3306/city_brain_hz?characterEncoding=UTF-8&useSSL=false&serverTimezone=Hongkong&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PWD = "123456";

    public static List<Object[]> resolve() {
        List<Object[]> relation = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
             PreparedStatement psRead = connection.prepareStatement(SQL_READ);
             PreparedStatement psWrite = connection.prepareStatement(SQL_WRITE)) {

            // 解析路口-车道-转向关系
            ResultSet resultSet = psRead.executeQuery();
            while (resultSet.next()) {
                String interId = resultSet.getString("interId");
                String fRid = resultSet.getString("fRid");
                String turnDirNoList = resultSet.getString("turnDirNoList");

                String[] turnDirNos = turnDirNoList.split(",");
                long maxTurnDriNo = Long.MIN_VALUE;
                for (String turnDirNoStr : turnDirNos) {
                    long turnDirNo = Long.parseLong(turnDirNoStr);
                    maxTurnDriNo = Long.max(maxTurnDriNo, turnDirNo);
                }

                relation.add(new Object[] {interId, fRid, maxTurnDriNo});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relation;
    }
}
