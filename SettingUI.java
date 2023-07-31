import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

//2023.05.26

//앞으로의 기능구현

public class SettingUI extends JFrame {
    public static Font semiTitleFont=new Font("Aa정말예쁜건이응이야",Font.TRUETYPE_FONT, 30);
    Point initialClick;

    public SettingUI(Connection con, String id){
        //2023.05.26
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        JPanel profiles=new profilePanel(con,id);
        JPanel change=new changePWPanel(con,id);
        JPanel byeUser=new byeUserPanel(con,id);
        JPanel blockList=new blockPanel(con,id);

        //2023.05.27 뒤로가기 추가
        ImageIcon go_back_Icon=new ImageIcon("D:\\study\\Community\\img\\go_back_icon.png");
        Image back=go_back_Icon.getImage();
        Image newBack=back.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        go_back_Icon=new ImageIcon(newBack);
        JLabel backIcon=new JLabel(go_back_Icon);
        backIcon.setBounds(20,20,50,50);
        backIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainUI(id);
                dispose();
            }
        });
        contentPane.add(backIcon);

        JLabel profile=new JLabel("프로필");
        profile.setFont(semiTitleFont);
        profile.setHorizontalAlignment(JLabel.LEFT);
        profile.setLocation(40,80);
        profile.setSize(100,50);
        profile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPane.remove(change);
                contentPane.remove(byeUser);
                contentPane.remove(blockList);
                contentPane.add(profiles);
                revalidate();
                repaint();
            }
        });
        contentPane.add(profile);

        JLabel changePW=new JLabel("비밀번호 변경");
        changePW.setFont(semiTitleFont);
        changePW.setHorizontalAlignment(JLabel.LEFT);
        changePW.setLocation(40,130);
        changePW.setSize(200,50);
        changePW.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPane.remove(profiles);
                contentPane.remove(byeUser);
                contentPane.remove(blockList);
                contentPane.add(change);
                revalidate();
                repaint();
            }
        });
        contentPane.add(changePW);

        JLabel denyList=new JLabel("차단목록 관리");
        denyList.setFont(semiTitleFont);
        denyList.setHorizontalAlignment(JLabel.LEFT);
        denyList.setLocation(40,180);
        denyList.setSize(200,50);
        denyList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPane.remove(profiles);
                contentPane.remove(change);
                contentPane.remove(byeUser);
                contentPane.add(blockList);
                revalidate();
                repaint();
            }
        });
        contentPane.add(denyList);

        JLabel leaveUser=new JLabel("회원 탈퇴");
        leaveUser.setFont(semiTitleFont);
        leaveUser.setHorizontalAlignment(JLabel.LEFT);
        leaveUser.setLocation(40,600);
        leaveUser.setSize(100,50);
        leaveUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPane.remove(profiles);
                contentPane.remove(change);
                contentPane.remove(blockList);
                contentPane.add(byeUser);
                revalidate();
                repaint();
            }
        });
        contentPane.add(leaveUser);

        JSeparator line=new JSeparator(); //세로 구분선
        line.setOrientation(SwingConstants.VERTICAL);
        line.setBounds(240,40,5,650);
        line.setForeground(Color.black);
        contentPane.add(line);

        //2023.05.27 사용자 사진 변경파트

        contentPane.add(profiles);


        setVisible(true);

        this.addMouseListener(new moveWindows());//창 이동
        this.addMouseMotionListener(new moveWindows());//창 이동
    }

    public class moveWindows extends MouseAdapter {
        public void mousePressed(MouseEvent e){
            initialClick=e.getPoint();//현재 좌표 저장
            getComponentAt(initialClick);//지정한 좌표 포함 컴포넌트 리턴
        }
        public void mouseDragged(MouseEvent e){
            JFrame jf=(JFrame) e.getSource();//드래그 된 JFrame 정보 get

            int thisX=jf.getLocation().x;
            int thisY=jf.getLocation().y;

            //현재 마우스 위치(x,y)- 첫 마우스 클릭 위치(x,y)
            int xMoved=e.getX()-initialClick.x;
            int yMoved=e.getY()-initialClick.y;

            int X=thisX+xMoved;
            int Y=thisY+yMoved;

            jf.setLocation(X,Y);
        }
    }
}
