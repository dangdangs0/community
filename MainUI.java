import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

//2023.04.27~2023.05.15 1차 완료
//Main UI
public class MainUI extends JFrame { //JFrame 상속
    public Point initialClick; //마우스의 x, y좌표 저장
    public static JLabel defaultIcon;
    public static JButton exit;
    public static JLabel nickname;
    public static Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 18);
    //프레임 생성
    public MainUI(){
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        //종료 버튼 클릭시 2023.04.29-2023.04.30
        exit=new JButton("X");
        exit.setBackground(null);
//        exit.setBorderPainted(false);//테두리 없앰
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);//선택됐을 때 테두리 안함
        exit.setSize(45,45);
        exit.setLocation(1020,20);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result= JOptionPane.showConfirmDialog(MainUI.this,"종료하시겠습니까?","종료",JOptionPane.YES_NO_OPTION);

                if(result==JOptionPane.YES_OPTION){//시스템 종료 창
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
        });
        contentPane.add(exit); //X 버튼 생성 및 종료 기능 추가!


        //2023.05.10 사용자 프로필 사진
        ImageIcon defaultUserIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");
        Image img=defaultUserIcon.getImage();
        Image newing=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        defaultUserIcon=new ImageIcon(newing);
        //사진 크기 조정 (05.27.. 덕분에 원본 화질로 이쁘게 저장 가능)

        defaultIcon=new JLabel(defaultUserIcon);
        defaultIcon.setBounds(950,20,50,50);
        contentPane.add(defaultIcon);

        //2023.05.10 사용자 닉네임
        nickname=new JLabel("<html><u>로그인해주세요</u></html>");
        nickname.setLocation(830,20);
        nickname.setSize(100,50);
        nickname.setFont(font);
        nickname.setForeground(Color.BLUE);
        nickname.setHorizontalAlignment(JLabel.RIGHT);

        //사용자 팝업창(2023.05.28)
        //로그인 된 사용자만! 나중에 풀어야함
        
//        if(!nickname.getText().equals("<html><u>로그인해주세요</u></html>")){
            JPopupMenu popupMenu=new JPopupMenu("User");

            JMenuItem managePost=new JMenuItem("내 게시글 관리");
            managePost.setFont(font);
            JMenuItem manageLike=new JMenuItem("좋아요 관리");
            manageLike.setFont(font);
            JMenuItem setting=new JMenuItem("설정");
            setting.setFont(font);
            setting.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SettingUI();
                    dispose();
                }
            });

            popupMenu.add(managePost);
            popupMenu.addSeparator();//구분선 추가
            popupMenu.add(manageLike);
            popupMenu.addSeparator();//구분선 추가
            popupMenu.add(setting);



            defaultIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    popupMenu.show(contentPane,950,80);
                }
            });

            contentPane.add(popupMenu);
//        }


        //사용자 닉네임에 어차피 특수문자 안받을거니까.. _이거 빼고
        //로그인 하지 않은 사용자 (.equals("로그인해주세요"))
        //2023.05.19
        String noLogin=nickname.getText();
        if(noLogin.equals("<html><u>로그인해주세요</u></html>")){
            nickname.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //LoginPopupUI로 이동할거임
                    new LoginPopupUI();
                }
            });
        }
        contentPane.add(nickname);


        //2023.05.11 돋보기 아이콘
        ImageIcon searchIcon=new ImageIcon("D:\\study\\Community\\img\\search_icon.png");
        JLabel sIcon=new JLabel(searchIcon);
        sIcon.setBounds(40,20,50,50);
        contentPane.add(sIcon);

        //2023.05.11 새로고침 아이콘 -- 누르면 새로고침 되어야함..
        ImageIcon refreshIcon=new ImageIcon("D:\\study\\Community\\img\\refresh_icon.png");
        JLabel rIcon=new JLabel(refreshIcon);
        rIcon.setBounds(980,650,50,50);
        contentPane.add(rIcon);

        //HintTextField 사용해서 커서 가면 자동으로 지워지도록..! 2023.05.01
        JTextField search=new HintTextField("Search");
        search.setLocation(100,20);
        search.setSize(500,50);
        contentPane.add(search);

        //검색 버튼 2023.05.01
        JButton searchBtn=new JButton("검색");
        searchBtn.setLocation(600, 20);
        searchBtn.setSize(80,50);
        searchBtn.setFocusPainted(false);//선택됐을 때 테두리 안함
        searchBtn.setBackground(Color.gray);
        contentPane.add(searchBtn);


        //게시판 테이블 2023.05.03
        //게시판에 사진 넣기, 헤더 없애기 2023.05.15
        //버튼 테이블에 넣기
        JPanel panel=new JPanel();
        panel.setBackground(Color.white);
        JButton deny=new JButton(":");
        String col[]={"사진","제목","글쓴이","설정"};// 속성
        Object temp[][]=
                {{new ImageIcon("C:\\Users\\손혜진\\Pictures\\잡\\13.png"),"임시 게시판입니다","나",deny},
                        {new ImageIcon("C:\\Users\\손혜진\\Pictures\\잡\\14.png"),"대체 왜 안나와!??","또 나다",deny},
                        {new ImageIcon("C:\\Users\\손혜진\\Pictures\\잡\\15.png"),"미안합니다 테스트 해야합니다","관리자",deny}}; //내용
        JTable board=new JTable(temp,col);
        board.setShowGrid(false);
        board.setFont(font);
        board.setTableHeader(null); //테이블 헤더 없앰

        //테이블 수정 금지 2023.05.09
        //테이블 행 위치 못바꾸도록 설정
        board.setModel(new DefaultTableModel(temp,col){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }
        });

        //더블 클릭 이벤트 2023.05.24
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    new PostUI();
                    dispose();
                }
            }
        });

//        board.getTableHeader().setReorderingAllowed(false);//열 이동 불가
//        board.getTableHeader().setResizingAllowed(false);//크기 조절 불가
        board.setRowHeight(200); //행 높이


        //JScroll  2023.05.04 스크롤 바 만들기
        panel.add(board);
        JScrollPane scroll=new JScrollPane(board);
        panel.add(scroll);
        scroll.setPreferredSize(new Dimension(1000,550));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(40, 80,1000,600);
        contentPane.add(panel); // 스크롤바를 테이블에 부착


        setVisible(true); //Frame 화면에 띄우기


        //타이틀 바 없애서 프레임 화면 이동할 때 사용!
        this.addMouseListener(new moveWindows());//창 이동
        this.addMouseMotionListener(new moveWindows());//창 이동
    }

    //창 이동을 위한 것 (타이틀 바 없애서!)
    public class moveWindows extends MouseAdapter{
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

    public static void main(String[] args) {
        MainUI mainUI =new MainUI(); //프레임을 불러옴
    }
}
