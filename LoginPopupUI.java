//2023.05.17~05.19

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPopupUI extends JFrame{
    public LoginPopupUI(){
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

        JTextField pw=new HintTextField("PW");
        pw.setLocation(30,110);
        pw.setSize(300,50);
        c.add(pw);

        JButton login=new JButton("로그인");
        login.setLocation(350,50);
        login.setSize(100,110);
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

        setVisible(true);
    }
}
