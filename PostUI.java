import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//2023.05.24~25 1차

public class PostUI extends JFrame {
    public static Font titleFont=new Font("Aa합정산스",Font.BOLD, 40); //타이틀 폰트
    Point initialClick;
    public PostUI(){
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        MainUI.defaultIcon.setBounds(950,20,50,50);
        contentPane.add(MainUI.defaultIcon); //유저 아이콘

        MainUI.nickname.setLocation(830,20);
        MainUI.nickname.setSize(100,50);
        contentPane.add(MainUI.nickname);

        MainUI.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result= JOptionPane.showConfirmDialog(PostUI.this,"종료하시겠습니까?","종료",JOptionPane.YES_NO_OPTION);

                if(result==JOptionPane.YES_OPTION){//시스템 종료 창
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
        });
        contentPane.add(MainUI.exit); //X 버튼

        //게시글 내용 나오는 곳
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setBounds(67, 80,950,700);
        JScrollPane scroll=new JScrollPane(panel);

        //2023.05.25
        scroll.setLocation(1010,80);
        scroll.setSize(15,640);
        scroll.setPreferredSize(new Dimension(15,650));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scroll);
        contentPane.add(panel);

        //게시글 제목
        JLabel title=new JLabel("게시글 제목이 적힐 것 입니다");
        title.setFont(titleFont);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setLocation(110,40);
        title.setSize(700,50);
        panel.add(title);

        //제목 밑 구분선
        JSeparator line=new JSeparator();
        line.setOrientation(SwingConstants.HORIZONTAL);
        line.setBounds(55,120,850,5);
        line.setForeground(Color.black);
        panel.add(line);

        //2023.05.25 작성자 아이콘
        ImageIcon wIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");
        JLabel writerIcon=new JLabel(wIcon);
        writerIcon.setBounds(55,130,50,50);
        panel.add(writerIcon);

        //2023.05.25 작성자 닉네임
        JLabel writerName=new JLabel("작성자 닉네임");
        writerName.setLocation(110,130);
        writerName.setSize(100,50);
        writerName.setFont(MainUI.font);
        writerName.setForeground(Color.BLACK);
        writerName.setHorizontalAlignment(JLabel.LEFT);
        panel.add(writerName);

        String userText="Lorem ipsum dolor sit amet, consectetur adipiscing elit, <br/><br/>" +
        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br/><br/>" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br/><br/>" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. <br/><br/>" +
                "Exceptioneur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labourum."+
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, <br/><br/>" +
        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br/><br/>" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br/><br/>" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. <br/><br/>" +
                "Exceptioneur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labourum.";

        JLabel text=new JLabel("<HTML><center>"+userText+"</center></HTML>");
        text.setLocation(100,150);
        text.setBackground(Color.gray);
        text.setSize(800,700);
        text.setFont(MainUI.font);
        text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(text);

        //JLabel 길이 길면 스크롤바 움직이도록






        setVisible(true); //Frame 화면에 띄우기

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
