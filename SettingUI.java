import javax.swing.*;
import java.awt.*;

//2023.05.26

public class SettingUI extends JFrame {
    public static Font semiTitleFont=new Font("Aa합정산스",Font.TRUETYPE_FONT, 30); //타이틀 폰트

    public SettingUI(){
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        JLabel profile=new JLabel("프로필");
        profile.setFont(semiTitleFont);
        profile.setHorizontalAlignment(JLabel.LEFT);
        profile.setLocation(40,80);
        profile.setSize(100,50);
        contentPane.add(profile);

        JLabel changePW=new JLabel("비밀번호 변경");
        changePW.setFont(semiTitleFont);
        changePW.setHorizontalAlignment(JLabel.LEFT);
        changePW.setLocation(40,130);
        changePW.setSize(200,50);
        contentPane.add(changePW);

        JLabel denyList=new JLabel("차단목록 관리");
        denyList.setFont(semiTitleFont);
        denyList.setHorizontalAlignment(JLabel.LEFT);
        denyList.setLocation(40,180);
        denyList.setSize(200,50);
        contentPane.add(denyList);

        JLabel leaveUser=new JLabel("회원 탈퇴");
        leaveUser.setFont(semiTitleFont);
        leaveUser.setHorizontalAlignment(JLabel.LEFT);
        leaveUser.setLocation(40,600);
        leaveUser.setSize(100,50);
        contentPane.add(leaveUser);

        JSeparator line=new JSeparator();
        line.setOrientation(SwingConstants.VERTICAL);
        line.setBounds(240,40,5,650);
        line.setForeground(Color.black);
        contentPane.add(line);

        //사용자 사진 밑 그 외 버튼 만들기

        setVisible(true);
    }
    public static void main(String[] args) {
        SettingUI settingUI=new SettingUI();
    }
}
