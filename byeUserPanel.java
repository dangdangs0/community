import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

class byeUserPanel extends JPanel {
    public byeUserPanel(Connection con, String id){
        setLayout(null);
        setBounds(250,40,800,600);
        setBackground(Color.white);

        String sql="select * from 회원 where 아이디='"+id+"'";

        //각각 라벨과 텍스트필드
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

                    //비밀번호도 다 통과되어야함
                    JLabel label=new JLabel("회원탈퇴 되었습니다.");
                    label.setFont(MainUI.font);
                    JOptionPane.showMessageDialog(null,label);
                    new MainUI("");
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
