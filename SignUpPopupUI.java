import javax.swing.*;
import java.awt.*;

//2023.05.19
public class SignUpPopupUI extends JFrame {
    public SignUpPopupUI(){
        Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 22);
        String[] interest={"없음","요리","게임","정치","종교"};

        setSize(500,600);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        Container c=getContentPane();
        c.setLayout(null);
        c.setBackground(Color.white);

        //2023.05.21 아이디~관심지역 combobox, 비밀번호 * 표시
        JLabel idLabel=new JLabel("아이디");
        idLabel.setFont(font);
        idLabel.setSize(70,50);
        idLabel.setLocation(50,60);
        c.add(idLabel);

        JTextField id=new JTextField();
        id.setFont(font);
        id.setSize(200,40);
        id.setLocation(200,70);
        c.add(id);


        JLabel pwLabel=new JLabel("비밀번호");
        pwLabel.setFont(font);
        pwLabel.setSize(70,50);
        pwLabel.setLocation(50,110);
        c.add(pwLabel);

        JPasswordField pw=new JPasswordField();
        pw.setFont(font);
        pw.setSize(200,40);
        pw.setLocation(200,120);
        pw.setEchoChar('*');
        c.add(pw);


        JLabel confirmPwLabel=new JLabel("비밀번호 확인");
        confirmPwLabel.setFont(font);
        confirmPwLabel.setSize(120,50);
        confirmPwLabel.setLocation(30,160);
        c.add(confirmPwLabel);

        JPasswordField confirmPw=new JPasswordField();
        confirmPw.setFont(font);
        confirmPw.setSize(200,40);
        confirmPw.setLocation(200,170);
        confirmPw.setEchoChar('*');
        c.add(confirmPw);


        JLabel nicknameLabel=new JLabel("닉네임");
        nicknameLabel.setFont(font);
        nicknameLabel.setSize(120,50);
        nicknameLabel.setLocation(50,210);
        c.add(nicknameLabel);

        JTextField nickname=new JTextField();
        nickname.setFont(font);
        nickname.setSize(200,40);
        nickname.setLocation(200,220);
        c.add(nickname);



        JLabel interestLabel=new JLabel("관심 분야");
        interestLabel.setFont(font);
        interestLabel.setSize(120,50);
        interestLabel.setLocation(40,260);
        c.add(interestLabel);

        JComboBox interestCombo=new JComboBox(interest);
        interestCombo.setFont(font);
        interestCombo.setSize(200,40);
        interestCombo.setLocation(200,270);
        c.add(interestCombo);



        setVisible(true);
    }

    public static void main(String[] args) { //나중에 지워야지
        new SignUpPopupUI();
    }
}
