import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//2023.05.26

public class SettingUI extends JFrame {
    public static Font semiTitleFont=new Font("Aa합정산스",Font.TRUETYPE_FONT, 30);
    Point initialClick;
    public SettingUI(){
        //2023.05.26
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

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
                new MainUI();
                dispose();
            }
        });
        contentPane.add(backIcon);

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

        JSeparator line=new JSeparator(); //세로 구분선
        line.setOrientation(SwingConstants.VERTICAL);
        line.setBounds(240,40,5,650);
        line.setForeground(Color.black);
        contentPane.add(line);

        //2023.05.27 사용자 사진 변경파트
        ImageIcon changeUserIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");
        Image image=changeUserIcon.getImage();
        Image newing=image.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        changeUserIcon=new ImageIcon(newing);

        JLabel changeIcon=new JLabel(changeUserIcon);
        changeIcon.setLocation(550,80);
        changeIcon.setSize(200,200);
        contentPane.add(changeIcon);

        //사용자 사진 변경 버튼
        JButton changeImg=new JButton("사진 변경");
        changeImg.setFont(MainUI.font);
        changeImg.setBackground(Color.white);
        changeImg.setBounds(500,300,100,50);
        contentPane.add(changeImg);

        //사진 삭제
        JButton delImg=new JButton("삭제");
        delImg.setFont(MainUI.font);
        delImg.setBounds(700,300,100,50);
        delImg.setBackground(Color.white);
        //삭제 버튼 눌렀을 때 다이얼로그 나옴. 나중에 더 예쁘게 꾸미기.
        delImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result= JOptionPane.showConfirmDialog(SettingUI.this,"삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);

                if(result==JOptionPane.YES_OPTION){//시스템 종료 창
                    //유저 아이콘이 기본 아이콘으로 바뀜.
                }
            }
        });
        contentPane.add(delImg);


        //각각 라벨과 텍스트필드
        JLabel nickname=new JLabel("닉네임");
        nickname.setFont(MainUI.font);
        nickname.setBounds(500,370,100,50);
        contentPane.add(nickname);

        JTextField changeNick=new JTextField(MainUI.nickname.getText());
        changeNick.setFont(MainUI.font);
        changeNick.setBounds(500,420,300,50);
        contentPane.add(changeNick);

        JLabel description=new JLabel("한 줄 소개");
        description.setFont(MainUI.font);
        description.setBounds(500,490,100,50);
        contentPane.add(description);

        JTextField changeDescript=new HintTextField("한 줄 소개를 입력해주세요");
        changeDescript.setFont(MainUI.font);
        changeDescript.setBounds(500,540,300,50);
        contentPane.add(changeDescript);

        JButton save=new JButton("저장");
        save.setFont(MainUI.font);
        save.setBackground(Color.white);
        save.setBounds(900,600,100,50);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //저장 버튼을 눌렀을 때만 반영해야함
                JOptionPane.showMessageDialog(SettingUI.this,"저장되었습니다.");
            }
        });
        contentPane.add(save);


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
