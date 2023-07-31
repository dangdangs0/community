import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

//2023.05.24~25 1차
//~2023.07.22 2차
//2023.07.30 끝


public class PostUI extends JFrame {
    public static Font titleFont=new Font("Aa정말예쁜건이응이야",Font.BOLD, 40); //타이틀 폰트
    Point initialClick;
    String postOwner;
    public static JLabel defaultIcon;
    public PostUI(Connection con, int postID, String id){
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        //뒤로가기 아이콘
        ImageIcon go_back_Icon=new ImageIcon("D:\\study\\Community\\img\\go_back_icon.png");
        Image image=go_back_Icon.getImage();
        Image news=image.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        go_back_Icon=new ImageIcon(news);
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


        ImageIcon defaultUserIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");


        JLabel nickname=new JLabel("<html><u>로그인해주세요</u></html>");
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
        nickname.setFont(MainUI.font);
        nickname.setHorizontalAlignment(JLabel.RIGHT);

        if(!nickname.getText().equals("<html><u>로그인해주세요</u></html>")) {
            JPopupMenu popupMenu = new JPopupMenu("User");

            JMenuItem managePost = new JMenuItem("내 게시글 관리");
            managePost.setFont(MainUI.font);
            managePost.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UserProfileUI(con, id,id);
                    dispose();
                }
            });
            JMenuItem write = new JMenuItem("글쓰기");
            write.setFont(MainUI.font);
            JMenuItem setting = new JMenuItem("설정");
            setting.setFont(MainUI.font);
            setting.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SettingUI(con, id);
                    dispose();
                }
            });
            JMenuItem logout = new JMenuItem("로그아웃");
            logout.setFont(MainUI.font);
            logout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new MainUI(id);
                }
            });

            popupMenu.add(write);
            popupMenu.addSeparator();//구분선 추가
            popupMenu.add(managePost);
            popupMenu.addSeparator();//구분선 추가
            popupMenu.add(setting);
            popupMenu.addSeparator();
            popupMenu.add(logout);


            defaultIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    popupMenu.show(contentPane, 920, 80);
                }
            });
            contentPane.add(popupMenu);
        }

        String noLogin=nickname.getText();
        if(noLogin.equals("<html><u>로그인해주세요</u></html>")){
            nickname.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //LoginPopupUI로 이동할거임
                    new LoginPopupUI(con);
                    dispose();
                }
            });
        }
        contentPane.add(nickname);


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
        panel.setBounds(67, 80,950,180);

        //게시글 제목

        //MainUI에서 받아와서 띄워줄거임

        JLabel title=new JLabel("게시글 제목이 적힐 것 입니다");
        ImageIcon wIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");



        String userText="Lorem ipsum dolor sit amet, consectetur adipiscing elit, <br/><br/>" +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br/><br/>" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br/><br/>" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. <br/><br/>" +
                "Exceptioneur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labourum."+
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, <br/><br/>" +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br/><br/>" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br/><br/>" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. <br/><br/>" +
                "Exceptioneur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labourum."+
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br/><br/>" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br/><br/>" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. <br/><br/>" +
                "Exceptioneur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labourum."+
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,<br/><br/>" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br/><br/>" +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. <br/><br/>" +
                "Exceptioneur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labourum.";

        String commentSpace="                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>"+
                "                                            <br/><br/>";


        JLabel writerName=new JLabel("작성자 닉네임");


        String sql="select * from 게시글 where 게시글번호= ?";
        try{
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,postID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                title=new JLabel(rs.getString(2).strip());
                userText=rs.getString(4).replace("\n","<br/>");

                postOwner=rs.getString(3).strip();
                String findPic="select * from 회원 where 아이디='"+postOwner+"'";
                try{
                    Statement st=con.createStatement();
                    ResultSet r=st.executeQuery(findPic);
                    while(r.next()){
                        wIcon=new ImageIcon(r.getString(6).strip());
                        writerName=new JLabel(r.getString(3).strip());
                    }
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

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

        Image imgs=wIcon.getImage();
        Image newings=imgs.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        wIcon=new ImageIcon(newings);

        JLabel writerIcon=new JLabel(wIcon);
        writerIcon.setBounds(55,130,50,50);
        panel.add(writerIcon);

        //2023.05.25 작성자 닉네임

        writerName.setLocation(110,130);
        writerName.setSize(100,50);
        writerName.setFont(MainUI.font);
        writerName.setForeground(Color.BLACK);
        writerName.setHorizontalAlignment(JLabel.LEFT);
        panel.add(writerName);

        //2023.05.29 작성자 팝업
        JPopupMenu writerMenu=new JPopupMenu("Writer");

        JMenuItem seePose=new JMenuItem(writerName.getText()+"님의 게시글 보기");
        seePose.setFont(MainUI.font);
        seePose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserProfileUI(con,postOwner,id);
                dispose();
            }
        });

        writerMenu.add(seePose);

        writerName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                writerMenu.show(contentPane,100,250);
            }
        });

        contentPane.add(writerMenu);

        JPanel forDetail=new JPanel();
        forDetail.setBackground(Color.white);
        forDetail.setBounds(120,260,850,450);



        userText+=commentSpace;


        JLabel text=new JLabel("<html><br/>"+userText+"<html>",SwingConstants.CENTER);
        text.setLocation(100,200);
        text.setSize(800,900);
        text.setFont(MainUI.font);
        text.setOpaque(true);
        text.setBackground(Color.white);


        JSeparator sep=new JSeparator();
        sep.setOrientation(SwingConstants.HORIZONTAL);
        sep.setBounds(80,250,700,5);
        sep.setForeground(Color.black);
        text.add(sep);
        text.setLayout(null);

        forDetail.add(text);


        //이미 있는 댓글들 부터 JList로 불러옴
        DefaultListModel listModel=new DefaultListModel();
        String comsql="select * from 댓글 where 게시글번호='"+postID+"'";

        try{
            Statement st=con.createStatement();
            ResultSet rss=st.executeQuery(comsql);
            while(rss.next()){
                String coms= rss.getString(4).strip();
                listModel.addElement(coms);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //댓글에 스크롤바 붙이기 성공
        JList list=new JList(listModel);
        list.setFont(MainUI.font);
        list.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        list.setBounds(130,300,600,150);
        JScrollPane js=new JScrollPane();
        js.setBounds(130,300,600,150);
        js.setViewportView(list);
        text.add(js);



        //댓글창도 만들기
        JTextArea comment=new JTextArea("");
        comment.setBounds(130,500,600,200);
        comment.setFont(MainUI.font);

        TitledBorder border=BorderFactory.createTitledBorder("댓글");
        border.setTitleFont(MainUI.font);
        comment.setBorder(border);
        text.add(comment);

        JButton regist=new JButton("등록");
        regist.setBounds(650,720,80,40);
        regist.setFont(MainUI.font);
        text.add(regist);


        regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reply=comment.getText();
                if(id.equals("")){
                    JOptionPane.showMessageDialog(null,"로그인을 해주세요.",null,JOptionPane.INFORMATION_MESSAGE);

                }
                else{
                    if(reply.equals("")){
                        JOptionPane.showMessageDialog(null,"내용을 입력해주세요.",null,JOptionPane.INFORMATION_MESSAGE);

                    }else{
                        String insertComment="insert into 댓글(게시글번호, 댓글번호, 작성자, 내용) values(?,seq_reply.nextval,?,?)";
                        PreparedStatement p=null;
                        try {
                            p=con.prepareStatement(insertComment);
                            p.setInt(1,postID);
                            p.setString(2,id);
                            p.setString(3,reply);

                            p.executeUpdate();

                            JOptionPane.showMessageDialog(null,"댓글이 등록되었습니다.",null,JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new PostUI(con,postID,id);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });



        JScrollPane scroll=new JScrollPane(text);
        forDetail.add(scroll);

        //2023.05.25
        scroll.setLocation(1000,260);
        scroll.setSize(15,430);
        scroll.setPreferredSize(new Dimension(850,430));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(16); //스크롤 시 속도 개선


        scroll.getViewport().setViewPosition(new Point(300,0)); //스크롤바 시작 위치


        contentPane.add(panel);
        contentPane.add(forDetail);



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
