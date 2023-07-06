//2023.05.17~05.19
//기능 구현 끝 2022.07.06

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class LoginPopupUI extends JFrame{
    public LoginPopupUI(Connection con){
        //06.22 DB 연동

        setSize(500,300);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        Container c=getContentPane();
        c.setLayout(null);
        c.setBackground(Color.white);

        Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 18);

        //HintTextField 사용해서 커서 가면 자동으로 지워지도록..! 2023.05.01
        JTextField id=new HintTextField("ID");
        id.setLocation(30,50);
        id.setSize(300,50);
        c.add(id);

        JPasswordField pw=new JPasswordField("");
        pw.setLocation(30,110);
        pw.setSize(300,50);
        c.add(pw);

        JButton login=new JButton("로그인");
        login.setLocation(350,50);
        login.setSize(100,110);
        login.setFont(MainUI.font);
        c.add(login);

        JLabel isFirst=new JLabel("처음이신가요?");
        isFirst.setLocation(140,190);
        isFirst.setSize(100,30);
        isFirst.setFont(font);
        c.add(isFirst);

        //하이퍼링크 걸기
        JLabel createAccount=new JLabel("<HTML><U>Create Account<HTML><U>");
        createAccount.setLocation(240,190);
        createAccount.setSize(150,30);
        createAccount.setFont(font);
        createAccount.setForeground(Color.BLUE);
        createAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //LoginPopupUI로 이동할거임
                new SignUpPopupUI();
                dispose();
            }
        });
        c.add(createAccount);


        //06.22 DB 연동
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID=id.getText();
                String PW=pw.getText();
                String sql="select * from 회원 where 아이디='"+ID+"'";
                try{
                    Statement stat=con.createStatement();
                    ResultSet rs=stat.executeQuery(sql);
                    if(rs.next()){
                        if(ID.equals(rs.getString("아이디"))){
                            if(PW.equals(rs.getString("비밀번호"))){
                                JLabel label=new JLabel("로그인 성공");
                                label.setFont(MainUI.font);
                                JOptionPane.showMessageDialog(null,label,"로그인 성공",JOptionPane.INFORMATION_MESSAGE);
                                new MainUI(ID);
                                dispose();
                            }
                            else{
                                JLabel label=new JLabel("아이디 또는 비밀번호를 다시 확인해주세요");
                                label.setFont(MainUI.font);
                                JOptionPane.showMessageDialog(null,label,"로그인 실패",JOptionPane.WARNING_MESSAGE);
                            }
                        }

                    }
                    else{
                        JLabel label=new JLabel("아이디 또는 비밀번호를 다시 확인해주세요");
                        label.setFont(MainUI.font);
                        JOptionPane.showMessageDialog(null,label,"로그인 실패",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setVisible(true);
    }
}
