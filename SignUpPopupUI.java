import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//2023.05.19~2023.05.22 1차 끝
public class SignUpPopupUI extends JFrame {
    public SignUpPopupUI(){
        Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 22);
        String[] interest={"없음","요리","게임","정치","종교","만화","음악"};

        setSize(410,650);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        Container c=getContentPane();
        c.setLayout(null);
        c.setBackground(Color.white);

        //2023.05.21 아이디~관심지역 combobox, 비밀번호 * 표시
        JLabel idLabel=new JLabel("아이디");
        idLabel.setFont(font);
        idLabel.setHorizontalAlignment(JLabel.LEFT);
        idLabel.setSize(70,50);
        idLabel.setLocation(70,10);
        c.add(idLabel);

        JTextField id=new JTextField();
        id.setFont(font);
        id.setSize(250,40);
        id.setLocation(70,50);
        c.add(id);


        JLabel pwLabel=new JLabel("비밀번호");
        pwLabel.setFont(font);
        pwLabel.setSize(70,50);
        pwLabel.setLocation(70,100);
        c.add(pwLabel);

        JPasswordField pw=new JPasswordField();
        pw.setFont(font);
        pw.setSize(250,40);
        pw.setLocation(70,140);
        pw.setEchoChar('*');
        c.add(pw);


        JLabel confirmPwLabel=new JLabel("비밀번호 확인");
        confirmPwLabel.setFont(font);
        confirmPwLabel.setSize(120,50);
        confirmPwLabel.setLocation(70,190);
        c.add(confirmPwLabel);

        JPasswordField confirmPw=new JPasswordField();
        confirmPw.setFont(font);
        confirmPw.setSize(250,40);
        confirmPw.setLocation(70,230);
        confirmPw.setEchoChar('*');
        c.add(confirmPw);


        JLabel nicknameLabel=new JLabel("닉네임");
        nicknameLabel.setFont(font);
        nicknameLabel.setSize(120,50);
        nicknameLabel.setLocation(70,280);
        c.add(nicknameLabel);

        JTextField nickname=new JTextField();
        nickname.setFont(font);
        nickname.setSize(250,40);
        nickname.setLocation(70,320);
        c.add(nickname);



        JLabel interestLabel=new JLabel("관심 분야");
        interestLabel.setFont(font);
        interestLabel.setSize(120,50);
        interestLabel.setLocation(70,370);
        c.add(interestLabel);

        JComboBox interestCombo=new JComboBox(interest);
        interestCombo.setFont(font);
        interestCombo.setSize(250,40);
        interestCombo.setLocation(70,410);
        c.add(interestCombo);


        JButton regist=new JButton("회원가입");
        regist.setFont(font);
        regist.setSize(120,50);
        regist.setLocation(130,500);
        regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        c.add(regist);

        setVisible(true);
    }

}
