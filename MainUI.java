import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

//2023.04.27~
//Main UI
public class MainUI extends JFrame { //JFrame 상속
    public Point initialClick; //마우스의 x, y좌표 저장

    //프레임 생성
    public MainUI(){
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 18);


        //종료 버튼 클릭시 2023.04.29-2023.04.30
        JButton exit=new JButton("X");
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
        JLabel defaultIcon=new JLabel(defaultUserIcon);
        defaultIcon.setBounds(950,20,50,50);
        contentPane.add(defaultIcon);

        //2023.05.10 사용자 닉네임
        JLabel nickname=new JLabel("로그인해주세요");
        nickname.setLocation(830,20);
        nickname.setSize(100,50);
        nickname.setFont(font);
        nickname.setHorizontalAlignment(JLabel.RIGHT);
        contentPane.add(nickname);


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
        JPanel panel=new JPanel();
        panel.setBackground(Color.white);
        String col[]={"게시판 제목","작성자"};// 속성
        Object temp[][]={{"임시 게시판입니다","나"},{"대체 왜 안나와!??","또 나다"}}; //내용
        JTable board=new JTable(temp,col);
        board.setShowGrid(false);
        board.setFont(font);

        //테이블 수정 금지 2023.05.09
        board.setModel(new DefaultTableModel(temp,col){
           @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
        });
        board.getTableHeader().setReorderingAllowed(false);//열 이동 불가
        board.getTableHeader().setResizingAllowed(false);//크기 조절 불가
        board.setRowHeight(200); //행 높이


        //JScroll  2023.05.04 스크롤 바 만들기
        panel.add(board);
        JScrollPane scroll=new JScrollPane(board);
        panel.add(scroll);
        scroll.setPreferredSize(new Dimension(1000,550));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(40, 80,1000,600);
        contentPane.add(panel); // 스크롤바를 테이블에 부착


        //테이블 행 위치 못바꾸도록 설정

        setVisible(true); //Frame 화면에 띄우기


        //타이틀 바 없애서 프레임 화면 이동할 때 사용!
        this.addMouseListener(new moveWindows());//창 이동
        this.addMouseMotionListener(new moveWindows());//창 이동
    }

    //창 이동을 위한 것 (타이틀 바 없애서!)
    class moveWindows extends MouseAdapter{
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
