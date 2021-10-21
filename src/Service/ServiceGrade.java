/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author BUI QUANG HIEU
 */
import GUI.*;
import New_Class.Grade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ServiceGrade {

    New_Class.Grade grade = new Grade();
    Connection con;
    Statement st;
    List<Grade> _ListGrade = new ArrayList<>();
    Grade _Grade;
//    ServiceStudent _ServiceStudent = new ServiceStudent();

    public ServiceGrade() {
        con = Connect.ketNoi("ASSIGNMENT");
    }

    public List<Grade> getListGrade() {
        return _ListGrade;
    }

    public List<Grade> getListGradeDB() throws SQLException {
        try {
            List<Grade> list = new ArrayList<>();
            String sql = "SELECT * FROM GRADE";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                _ListGrade.add(new Grade(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5)));
            }
            rs.close();
            st.close();
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi loadDataToList");
        }
        return null;
    }

    public List<Grade> findTOP(int top) throws SQLException {
        String sql = "SELECT TOP " + top + "  *, (TIENGANH + TINHOC + GDTC)/3 AS DTB FROM GRADE ORDER BY DTB desc";
        try (PreparedStatement pstm = con.prepareStatement(sql);) {

            try (ResultSet rs = pstm.executeQuery();) {
                List<Grade> list = new ArrayList<>();
                while (rs.next()) {
                    Grade g = new Grade();
                    g.setId(rs.getInt("ID"));
                    g.setmsv(rs.getString("MASV"));
                    g.setTiengAnh(rs.getFloat("TIENGANH"));
                    g.setTinHoc(rs.getFloat("TINHOC"));
                    g.setGDTC(rs.getFloat("GDTC"));
                    list.add(g);
                }
                rs.close();
//                st.close();
                return list;
            }
        }
    }

    public boolean addGrade(Grade grade) throws SQLException {

        //1. Viết câu lệnh Insert
        if (grade != null) {
            st = con.createStatement();
            String sql = "INSERT INTO GRADE (MASV, TIENGANH, TINHOC, GDTC) VALUES (?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, grade.getmasv());
            pstm.setFloat(2, grade.getTiengAnh());
            pstm.setFloat(3, grade.getTinHoc());
            pstm.setFloat(4, grade.getGDTC());
            pstm.executeUpdate();
            _ListGrade = getListGradeDB();//Sau khi thêm vào CSDL gán lại List
            return true;
        }
        return false;
    }

    public boolean upDateGrade(Grade grade) throws SQLException {
        st = con.createStatement();
        String sql = "UPDATE GRADE SET TIENGANH=?, TINHOC=?, GDTC=?  WHERE MASV=?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(4, grade.getmasv());
        pstm.setFloat(1, grade.getTiengAnh());
        pstm.setFloat(2, grade.getTinHoc());
        pstm.setFloat(3, grade.getGDTC());
        pstm.executeUpdate();
        _ListGrade = getListGradeDB();//Sau khi thêm vào CSDL gán lại List
        return true;

    }

    public boolean deleteGrade(String input) throws SQLException {
        st = con.createStatement();
        String sql = "DELETE FROM GRADE  WHERE MASV=?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, input);
        pstm.executeUpdate();
        _ListGrade = getListGradeDB();//Sau khi thêm vào CSDL gán lại List
        return true;

    }

    public Grade findByMaSV(String input) throws SQLException {
        st = con.createStatement();
        String sql = "SELECT * FROM GRADE  WHERE MASV=?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, input);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Grade grade = new Grade();
            grade.setId(rs.getInt(1));
            grade.setmsv(rs.getString(2));
            grade.setTiengAnh(rs.getFloat(3));
            grade.setTinHoc(rs.getFloat(4));
            grade.setGDTC(rs.getFloat(5));
            return grade;
        }
//            pstm.executeUpdate();
//            _ListGrade = getListGradeDB();//Sau khi thêm vào CSDL gán lại List
        return null;
    }

}
