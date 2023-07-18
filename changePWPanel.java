import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//2023.07.18 구현 완료
class changePWPanel extends JPanel {
    public changePWPanel(Connection con, String id){
        setLayout(null);
        setBounds(250,40,800,600);
        setBackground(Color.white);

        String sql="select * from 회원 where 아이디='"+id+"'";


         try{
            Statement stat=con.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            while(rs.next()){

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }



        //각각 라벨과 텍스트필드
        JLabel priorPass=new JLabel("이전 비밀번호");
        priorPass.setFont(MainUI.font);
        priorPass.setBounds(250,120,100,50);
        add(priorPass);

        JTextField priorPW=new JPasswordField("");
        priorPW.setFont(MainUI.font);
        priorPW.setBounds(250,170,300,50);
        add(priorPW);

        JLabel changePass=new JLabel("변경할 비밀번호");
        changePass.setFont(MainUI.font);
        changePass.setBounds(250,240,150,50);
        add(changePass);

        JTextField changePW=new JPasswordField("");
        changePW.setFont(MainUI.font);
        changePW.setBounds(250,290,300,50);
        add(changePW);


        JButton save=new JButton("저장");
        save.setFont(MainUI.font);
        save.setBackground(Color.white);
        save.setBounds(650,550,100,50);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //저장 버튼을 눌렀을 때만 반영해야함
                try{
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery(sql);

                    String prior=priorPW.getText();
                    String change=changePW.getText();
                    if(rs.next()){
                        if(prior.equals(rs.getString("비밀번호"))){
                            //이전 비밀번호랑 같으면
                            if(change.length()<8){
                                JOptionPane.showMessageDialog(null,"비밀번호는 8글자 이상으로 적어주세요.",null,JOptionPane.WARNING_MESSAGE);
                            }else{
                                try{
                                    //비밀번호 변경 DB
                                    String changePass="update 회원 set 비밀번호='"+change+"' where 아이디='"+id+"'";
                                    Statement s=con.createStatement();
                                    ResultSet r=s.executeQuery(changePass);
                                    if(r.next()){
                                        JOptionPane.showMessageDialog(null,"비밀번호가 변경되었습니다.",null,JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }catch (SQLException ee){
                                    throw new RuntimeException(ee);
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해 주세요", null, JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(save);
    }
}
