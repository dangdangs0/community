import oracle.jdbc.proxy.annotation.OnError;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;

//2023.04.27~2023.05.15 1차 완료
//2023.06.26 DB 연동 및 새로고침 기능, 등등 추가
//Main UI
public class MainUI extends JFrame { //JFrame 상속
    public Point initialClick; //마우스의 x, y좌표 저장
    public static JLabel defaultIcon;
    public static JButton exit;
    public static JLabel nickname;
    public static Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 18);
    Connection con=null;
    public static JTable board;
    DefaultTableModel model;
    int inter;
    //프레임 생성
    String id="";
    public MainUI(String ID){
        id=ID;

        try{//DB 연동
            
        }

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

        //사진 크기 조정 (05.27.. 덕분에 원본 화질로 이쁘게 저장 가능)



        //2023.05.10 사용자 닉네임
        //로그인 됐는지 안됐는지
        inter=0;
        if(id.equals("")){
            nickname=new JLabel("<html><u>로그인해주세요</u></html>");
            nickname.setForeground(Color.BLUE);
        }else{
            String sql="select * from 회원 where 아이디='"+id+"'";
            try{
                Statement stat=con.createStatement();
                ResultSet rs=stat.executeQuery(sql);
                while(rs.next()){
                    nickname=new JLabel(rs.getString(3).strip());
                    defaultUserIcon=new ImageIcon(rs.getString(6).strip());
                    inter=rs.getInt(4);
                    System.out.println(inter);
                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            nickname.setForeground(Color.BLACK);
        }

        Image img=defaultUserIcon.getImage();
        Image newing=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        defaultUserIcon=new ImageIcon(newing);
        defaultIcon=new JLabel(defaultUserIcon);
        defaultIcon.setBounds(950,20,50,50);
        contentPane.add(defaultIcon);

        nickname.setLocation(830,20);
        nickname.setSize(100,50);
        nickname.setFont(font);
        nickname.setHorizontalAlignment(JLabel.RIGHT);

        //사용자 팝업창(2023.05.28)
        //로그인 된 사용자만! 나중에 풀어야함
        //2023.07.06 풀었다

        if(!nickname.getText().equals("<html><u>로그인해주세요</u></html>")){
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
                    new SettingUI(con,id);
                    dispose();
                }
            });

            popupMenu.add(managePost);
            popupMenu.addSeparator();//구분선 추가
            popupMenu.add(manageLike);
            popupMenu.addSeparator();//구분선 추가
            popupMenu.add(setting);

            //PostUI에서 팝업메뉴 안열리는거 고쳐야함!
            defaultIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    popupMenu.show(getContentPane(),920,80);
                }
            });

            contentPane.add(popupMenu);
        }


        //사용자 닉네임에 어차피 특수문자 안받을거니까.. _이거 빼고
        //로그인 하지 않은 사용자 (.equals("로그인해주세요"))
        //2023.05.19
        String noLogin=nickname.getText();
        if(noLogin.equals("<html><u>로그인해주세요</u></html>")){
            nickname.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //LoginPopupUI로 이동할거임
                    new LoginPopupUI(con);
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
        JTextField search=new JTextField();
        search.setLocation(100,20);
        search.setSize(500,50);
        contentPane.add(search);

        //검색 버튼 2023.05.01
        JButton searchBtn=new JButton("검색");
        searchBtn.setLocation(600, 20);
        searchBtn.setSize(80,50);
        searchBtn.setFont(font);
        searchBtn.setFocusPainted(false);//선택됐을 때 테두리 안함
        searchBtn.setBackground(Color.ORANGE);
        contentPane.add(searchBtn);


        //게시판 테이블 2023.05.03
        //게시판에 사진 넣기, 헤더 없애기 2023.05.15
        //버튼 테이블에 넣기
        JPanel panel=new JPanel();
        panel.setBackground(Color.white);


        //2023.06.28 DB랑 더 제대로 연동해보기
        if(inter==0){
            model=sqlRun(con,"select * from 게시글 order by DBMS_RANDOM.RANDOM");
        }
        else{
            model=sqlRun(con,"select * from 게시글 where 분야='"+inter+"'order by DBMS_RANDOM.RANDOM");
        }

        board=new JTable(model);
        board.setShowGrid(false);
        board.setFont(font);
        board.setSelectionBackground(Color.white);
        board.setTableHeader(null); //테이블 헤더 없앰

        //테이블 수정 금지 2023.05.09
        //테이블 행 위치 못바꾸도록 설정

        tableFit(board);
        //더블 클릭 이벤트 2023.05.24;
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    new PostUI(con);
                    dispose();
                }
            }
        });
        board.setRowHeight(200); //행 높이


        rIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //새로고침을 누르면
                if(inter==0){
                    model=sqlRun(con,"select * from 게시글 order by DBMS_RANDOM.RANDOM");
                }
                else{
                    model=sqlRun(con,"select * from 게시글 where 분야='"+inter+"'order by DBMS_RANDOM.RANDOM");
                }
                board.setModel(model);
                model.fireTableDataChanged();


                panel.revalidate();
                panel.repaint();
                SwingUtilities.updateComponentTreeUI(contentPane);
            }
        });

        //JScroll  2023.05.04 스크롤 바 만들기
        panel.add(board);
        JScrollPane scroll=new JScrollPane(board);
        panel.add(scroll);
        scroll.setPreferredSize(new Dimension(1000,550));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setBounds(40, 80,1000,600);
        contentPane.add(panel); //

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!search.getText().toString().equals("")){
                    //검색 내용이 있으면

                    //검색 하면 왜 안바뀌는지 내일 찾아보기
                    if(inter==0){
                        model=sqlRun(con,"select * from 게시글 where 제목 like '%"+search.getText().strip()+"%' order by DBMS_RANDOM.RANDOM");
                    }
                    else{
                        model=sqlRun(con,"select * from 게시글 where 분야='"+inter+"'and 제목 like '%"+search.getText().strip()+"%' order by DBMS_RANDOM.RANDOM");
                    }

                    board.setModel(model);
                    tableFit(board);
                    model.fireTableDataChanged();

                    panel.revalidate();
                    panel.repaint();
                    SwingUtilities.updateComponentTreeUI(contentPane);
                }
                else{
                    if(inter==0){
                        model=sqlRun(con,"select * from 게시글 order by DBMS_RANDOM.RANDOM");
                    }
                    else{
                        model=sqlRun(con,"select * from 게시글 where 분야='"+inter+"'order by DBMS_RANDOM.RANDOM");
                    }

                    board.setModel(model);
                    model.fireTableDataChanged();


                    panel.revalidate();
                    panel.repaint();
                    SwingUtilities.updateComponentTreeUI(contentPane);
                }
            }
        });

        //새로고침 이벤트


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
        MainUI mainUI =new MainUI(""); //프레임을 불러옴
    }

    private DefaultTableModel sqlRun(Connection con, String sql) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object [][]data = null;
        String []columns = {"사진","제목","작성자","설정"};


        ArrayList l = new ArrayList<Object>();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int count = 0;
            int count2 = 0;
            while(rs.next()) {
                //DB에서 사진은 파일 위치만 저장해서 파일 위치에 있는 사진을 갖고오기
                String i="";
                if(rs.getString(6).strip()==null){
                    i=null;
                }else{
                    i=rs.getString(6).strip();
                }
                l.add(new ImageIcon(i));
                l.add(rs.getString(2).strip());
                l.add(rs.getString(3).strip());
                l.add(":");

                //2023.06.29 중간 정렬 하기!!

                count++;
            }
            data = new Object[count][4];
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < 4; j++) {
                    if (j == 0) {
                        Image img = ((ImageIcon)l.get(count2)).getImage();
                        img = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        data[i][j] = new ImageIcon(img);
                    }
                    else {
                        data[i][j] = l.get(count2);
                    }
                    count2++;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                if (column >= 4) column = column % 4;
                switch(column) {
                    case 0: return ImageIcon.class;
                    case 1, 2: return String.class;
                    default: return Object.class;
                }
            }
        };
        return model;
    }

    private void tableFit(JTable table) {

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
//        headerRenderer.setBackground(new Color(22, 185, 163));

        for (int i0 = 0; i0 < table.getModel().getColumnCount(); i0++) {
            table.getColumnModel().getColumn(i0).setHeaderRenderer(headerRenderer);
        }

        for (int row = 0; row < table.getRowCount(); row++)
        {
            int rowHeight = table.getRowHeight();
            for (int column = 0; column < table.getColumnCount(); column++)
            {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
            table.setRowHeight(row, rowHeight);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
    }
}
