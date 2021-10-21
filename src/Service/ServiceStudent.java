/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import GUI.*;
import New_Class.Student;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author BUI QUANG HIEU
 */
public class ServiceStudent {

    New_Class.Student student = new Student();
    Connection con;
    Statement st = null;
    List<Student> _ListStudents = new ArrayList<>();
//    List<Student> _ListAdd = new ArrayList<>();
//    List<Student> _ListDelete = new ArrayList<>();
//    List<Student> _ListEdit = new ArrayList<>();
    MainGUIQuanLySinhVien _GuiQLSV;

    public ServiceStudent() {
        _ListStudents = getlstStudent();
        con = Connect.ketNoi("ASSIGNMENT");
    }

    public List<Student> getlstStudent() {
        return _ListStudents;
    }

    public List<Student> getListStudentDB() throws SQLException {
        try {
            List<Student> list = new ArrayList<>();
            String sql = "SELECT * FROM STUDENT";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                _ListStudents.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5).equalsIgnoreCase("Nam") ? true : false, rs.getString(6), rs.getString(7) == null ? "NO IMAGES" : rs.getString(7)));
            }
            rs.close();
            st.close();
            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi loadDataToList");
        }
        return null;
    }

    public Student findByMSV(String input) throws SQLException {
        String sql = "SELECT * FROM STUDENT WHERE MASV =?";
        PreparedStatement prsm = con.prepareStatement(sql);
        prsm.setString(1, input);
        ResultSet rs = prsm.executeQuery();
        if (rs.next()) {
            Student st = new Student();
            st.setMsv(rs.getString(1));
            st.setHoTen(rs.getString(2));
            st.setEmail(rs.getString(3));
            st.setSdt(rs.getString(4));
            st.setGioiTinh(rs.getString(5).equalsIgnoreCase("Nam") ? true : false);
            st.setDiaChi(rs.getString(6));
            st.setHinhAnh(rs.getString(7));
            return st;
        }
        return null;
    }

    public boolean addStudent(Student student) throws SQLException {

        //1. Viết câu lệnh Insert
        if (student != null) {
            st = Connect.ketNoi("ASSIGNMENT").createStatement();
            String sql = "INSERT INTO STUDENT (MASV, HOTEN,EMAIL,SDT, GIOITINH, DIACHI, HINH) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, student.getMsv());
            pstm.setString(2, student.getHoTen());
            pstm.setString(3, student.getEmail());
            pstm.setString(4, student.getSdt());
            pstm.setString(5, student.getGioiTinh() == true ? "Nam" : "Nữ");
            pstm.setString(6, student.getDiaChi());
            pstm.setString(7, student.getHinhAnh());
            pstm.executeUpdate();
            _ListStudents = getListStudentDB();//Sau khi thêm vào CSDL gán lại
            return true;
        }
        return false;
    }

    //Hàm này dùng để insert 1 List danh sách Sinh viên vào trong cơ sở dữ liệu sử dụng kiến thức JAVA 1
    public boolean insertStudentToDB(List<Student> lstStudent) throws SQLException {
        st = Connect.ketNoi("ASSIGNMENT").createStatement();
        for (Student x : lstStudent) {
//                String sex = x.getGioiTinh() == true ? "Nam" : "Nữ";
//                String sql = "INSERT INTO STUDENT (MASV, HOTEN,EMAIL,SDT, GIOITINH, DIACHI, HINH) VALUES"
//                        + "('" + x.getMsv() + "','" + x.getHoTen() + "','" + x.getEmail() + "','"
//                        + x.getSdt() + "','" + sex + "','" + x.getDiaChi() + "','" + x.getHinhAnh() + ");";
//                st.executeUpdate(sql);//Thực thi câu lệnh Insert SQL
            String sql = "INSERT INTO STUDENT (MASV, HOTEN,EMAIL,SDT, GIOITINH, DIACHI, HINH) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, x.getMsv());
            pstm.setString(2, x.getHoTen());
            pstm.setString(3, x.getEmail());
            pstm.setString(4, x.getSdt());
            pstm.setString(5, x.getGioiTinh() == true ? "Nam" : "Nữ");
            pstm.setString(6, x.getDiaChi());
            pstm.setString(7, x.getHinhAnh());
            pstm.executeUpdate();
        }
        return false;

    }

    public boolean deleteStudentToDB(String input) {
        try {
            st = con.createStatement();
//            for (Student x : lstStudent) {

//                String sex = x.getGioiTinh() == true ? "Nam" : "Nữ";
//                String sql = "INSERT INTO STUDENT (MASV, HOTEN,EMAIL,SDT, GIOITINH, DIACHI, HINH) VALUES"
//                        + "('" + x.getMsv() + "','" + x.getHoTen() + "','" + x.getEmail() + "','"
//                        + x.getSdt() + "','" + sex + "','" + x.getDiaChi() + "','" + x.getHinhAnh() + ");";
//                st.executeUpdate(sql);//Thực thi câu lệnh Insert SQL
            String sql = "DELETE FROM STUDENT  WHERE MASV=?";
            String sql2 = "DELETE FROM GRADE  WHERE MASV=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            PreparedStatement pstm2 = con.prepareStatement(sql2);
            pstm.setString(1, input);
            pstm2.setString(1, input);
            pstm2.executeUpdate();
            pstm.executeUpdate();
            _ListStudents = getListStudentDB();
//            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean editStudentToDB(Student student) {
        try {
            st = Connect.ketNoi("ASSIGNMENT").createStatement();
//            for (Student x : lstStudent) {

//                String sex = x.getGioiTinh() == true ? "Nam" : "Nữ";
//                String sql = "INSERT INTO STUDENT (MASV, HOTEN,EMAIL,SDT, GIOITINH, DIACHI, HINH) VALUES"
//                        + "('" + x.getMsv() + "','" + x.getHoTen() + "','" + x.getEmail() + "','"
//                        + x.getSdt() + "','" + sex + "','" + x.getDiaChi() + "','" + x.getHinhAnh() + ");";
//                st.executeUpdate(sql);//Thực thi câu lệnh Insert SQL
            String sql = "UPDATE STUDENT SET HOTEN=?, EMAIL=?, SDT=?, GIOITINH=?, DIACHI=?, HINH=? WHERE MASV = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(7, student.getMsv());
            pstm.setString(1, student.getHoTen());
            pstm.setString(2, student.getEmail());
            pstm.setString(3, student.getSdt());
            pstm.setString(4, student.getGioiTinh() == true ? "Nam" : "Nữ");
            pstm.setString(5, student.getDiaChi());
            pstm.setString(6, student.getHinhAnh());
            pstm.executeUpdate();
            _ListStudents = getListStudentDB();
//            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String chuanHoaDanhTuRieng(String chuoi) {

        chuoi = chuoi.toLowerCase();
        String temp[] = chuoi.split(" ");
        chuoi = ""; // ? ^-^
        for (int i = 0; i < temp.length; i++) {
            chuoi += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) {
                chuoi += " ";
            }
        }
        return chuoi;
    }

    public int getIndexStudent(String msv) {
        for (int i = 0; i < _ListStudents.size(); i++) {
            if (_ListStudents.get(i).getMsv().equalsIgnoreCase(msv)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkMsv(Student st) {
        if (!(getIndexStudent(st.getMsv()) == -1)) {
            return true;
        }
        return false;
    }

    public boolean checkName(String input) {
        Pattern pattern = Pattern.compile(".+[^0-9!@#$%^&*()-+|'<>.`=/]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean checkEmail(String input) {
        Pattern pattern = Pattern.compile("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean checkSdt(String input) {
        Pattern pattern = Pattern.compile("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
