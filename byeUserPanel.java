import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//2023.07.16-2023.07.17 기능 구현 완
class byeUserPanel extends JPanel {
    public byeUserPanel(Connection con, String id){
        setLayout(null);
        setBounds(250,40,800,600);
        setBackground(Color.white);

        String sql="select * from 회원 where 아이디='"+id+"'";

        //각각 라벨과 텍스트필드q
        JLabel userid=new JLabel("아이디");
        userid.setFont(MainUI.font);
        userid.setBounds(250,120,100,50);
        add(userid);

        JTextField confirmID=new JTextField("");
        confirmID.setFont(MainUI.font);
        confirmID.setBounds(250,170,300,50);
        add(confirmID);

        JLabel nowPW=new JLabel("현재 비밀번호");
        nowPW.setFont(MainUI.font);
        nowPW.setBounds(250,240,100,50);
        add(nowPW);

        JTextField confirmPW=new JPasswordField("");
        confirmPW.setFont(MainUI.font);
        confirmPW.setBounds(250,290,300,50);
        add(confirmPW);

        JCheckBox check=new JCheckBox("회원탈퇴시 게시글이 다 삭제됩니다. 동의시 체크");
        check.setBounds(30,550,400,50);
        check.setBackground(Color.white);
        check.setFont(MainUI.font);
        add(check);


        JButton quit=new JButton("회원탈퇴");
        quit.setFont(MainUI.font);
        quit.setBackground(Color.white);
        quit.setBounds(650,550,100,50);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //저장 버튼을 눌렀을 때만 반영해야함
                if(check.isSelected()){
                    String ID=confirmID.getText();
                    String PW=confirmPW.getText();
                    try{
                        Statement stat=con.createStatement();
                        ResultSet rs=stat.executeQuery(sql);
                        if(rs.next()){
                            if(ID.equals(rs.getString("아이디"))){
                                if(PW.equals(rs.getString("비밀번호"))){
                                    //쿼리 실행해야함
                                    String delUser="delete from 회원 where 아이디='"+id+"'";
                                    try{
                                        Statement st=con.createStatement();
                                        ResultSet r=st.executeQuery(delUser);
                                        if(r.next()){
                                            JLabel label=new JLabel("회원탈퇴 되었습니다.");
                                            label.setFont(MainUI.font);
                                            JOptionPane.showMessageDialog(null,label);
                                            System.exit(0);
                                        }
                                    }catch (SQLException ee){
                                        throw new RuntimeException(ee);
                                    }
                                }
                                else{
                                    JLabel label=new JLabel("아이디 또는 비밀번호를 다시 확인해주세요");
                                    label.setFont(MainUI.font);
                                    JOptionPane.showMessageDialog(null,label,"확인 실패",JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            else{
                                JLabel label=new JLabel("아이디 또는 비밀번호를 다시 확인해주세요");
                                label.setFont(MainUI.font);
                                JOptionPane.showMessageDialog(null,label,"확인 실패",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }catch (SQLException ex){
                        throw new RuntimeException(ex);
                    }
                    //비밀번호도 다 통과되어야함

                }
                else{
                    JLabel label=new JLabel("체크를 해주세요.");
                    label.setFont(MainUI.font);
                    JOptionPane.showMessageDialog(null,label);
                }

            }
        });
        add(quit);

    }
}
