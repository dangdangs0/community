//글쓰기 화면

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

//2023.07.14
//DB연동까지
public class WriteUI extends JFrame {
    Point initialClick;
    public WriteUI(Connection con, String id){
        setSize(1080,720);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

        //뒤로가기
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

        //종료 버튼 클릭시
        MainUI.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result= JOptionPane.showConfirmDialog(WriteUI.this,"종료하시겠습니까?","종료",JOptionPane.YES_NO_OPTION);

                if(result==JOptionPane.YES_OPTION){//시스템 종료 창
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
        });
        contentPane.add(MainUI.exit); //X 버튼

        JComboBox interestCombo=new JComboBox(SignUpPopupUI.interest);
        interestCombo.setFont(MainUI.font);
        interestCombo.setSize(200,40);
        interestCombo.setLocation(680,60);
        contentPane.add(interestCombo);

        TitledBorder border=BorderFactory.createTitledBorder("제목");
        border.setTitleFont(PostUI.titleFont);

        JTextField title=new JTextField("");
        title.setFont(MainUI.font);
        title.setLocation(180,100);
        title.setSize(700,100);
        contentPane.add(title);
        title.setBorder(border);

        JTextArea desc=new JTextArea("");
        desc.setFont(MainUI.font);
        desc.setLocation(180,230);
        desc.setSize(700,300);
        TitledBorder border2=BorderFactory.createTitledBorder("본문");
        border2.setTitleFont(PostUI.titleFont);
        desc.setBorder(border2);

        contentPane.add(desc);

        JButton regist=new JButton("등록");
        regist.setBounds(800,540,80,40);
        regist.setFont(MainUI.font);
        regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Title=title.getText().strip();
                String Desc=desc.getText().strip();
                String inter=interestCombo.getSelectedItem().toString();

                if(Title.equals("")||Desc.equals("")){
                    JOptionPane.showMessageDialog(null,"입력하지 않은 내용이 있습니다.",null,JOptionPane.WARNING_MESSAGE);
                }else{
                    try{
                        Integer interNum=0;
                        String findNum="select 분야번호 from 분야 where 분야명='"+inter+"'";
                        Statement stInter = con.createStatement();
                        ResultSet rsInter = stInter.executeQuery(findNum);
                        if (rsInter.next()) {
                            interNum = rsInter.getInt("분야번호");
                        }

                        PreparedStatement pstmt=null;
                        String insertPost="insert into 게시글(게시글번호,제목,작성자,내용,분야) values(seq_post.nextval,?,?,?,?)";
                        pstmt=con.prepareStatement(insertPost);
                        pstmt.setString(1,Title);
                        pstmt.setString(2,id);
                        pstmt.setString(3,Desc);
                        if (inter.equals("없음")) {
                            pstmt.setNull(4, Types.INTEGER);
                        } else {
                            pstmt.setInt(4, interNum);
                        }

                        pstmt.executeUpdate();

                        JOptionPane.showMessageDialog(null,"게시글이 작성되었습니다.",null,JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new MainUI(id);
                    }catch (SQLException ex){
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        contentPane.add(regist);

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
