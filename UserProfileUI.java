import oracle.jdbc.proxy.annotation.Pre;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

//2023.07.30 끝
public class UserProfileUI extends JFrame {
    DefaultTableModel model;
    public UserProfileUI(Connection con, String seeID,String id){
        //나중에는 매개변수로 어떤 사용자의 게시글 정보를 볼 지 지정

        setSize(720,900);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        setUndecorated(true);//타이틀 바 제거

        Container contentPane=getContentPane();
        contentPane.setBackground(Color.white);//배경 색 지정
        contentPane.setLayout(null);

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


        JLabel writerName=new JLabel("닉네임");
        JLabel writerProfile=new JLabel("한줄소개쓰으으으");
        ImageIcon wIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");


        String sql="select * from 회원 where 아이디='"+seeID+"'";
        try{
            Statement stat=con.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            while(rs.next()){
                writerName=new JLabel(rs.getString(3).strip());
                writerProfile=new JLabel(rs.getString(5).strip());
                wIcon=new ImageIcon(rs.getString(6).strip());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        Image img=wIcon.getImage();
        Image newing=img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        wIcon=new ImageIcon(newing);

        JLabel writerIcon=new JLabel(wIcon);
        writerIcon.setBounds(100,50,100,100);
        contentPane.add(writerIcon);


        writerName.setLocation(220,60);
        writerName.setSize(200,50);
        writerName.setFont(SettingUI.semiTitleFont);
        writerName.setForeground(Color.BLACK);
        writerName.setHorizontalAlignment(JLabel.LEFT);
        contentPane.add(writerName);


        writerProfile.setLocation(220,100);
        writerProfile.setSize(500,50);
        writerProfile.setFont(SettingUI.semiTitleFont);
        writerProfile.setForeground(Color.BLACK);
        writerProfile.setHorizontalAlignment(JLabel.LEFT);
        contentPane.add(writerProfile);

        JPanel postList=new JPanel();
        postList.setBackground(Color.white);

        //각각의 포스트들 JPanel에 붙이기! 2023.05.31

        model=sqlRun(con,"select * from 게시글 where 작성자='"+seeID+"'");//2023.7.13 받아옴

        JTable board=new JTable(model);
        board.setShowGrid(false);
        board.setFont(MainUI.font);
        board.setSelectionBackground(Color.white);
        board.setTableHeader(null); //테이블 헤더 없앰
        tableFit(board);

        board.setRowHeight(100); //행 높이
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(id.equals(seeID)){
                    if(board.getSelectedColumn()==3){
                        Object value=board.getValueAt(board.getSelectedRow(),0);
                        int postID=(int)value;

                        JPopupMenu popupMenu=new JPopupMenu("삭제");//차단

                        JMenuItem delPost=new JMenuItem("게시글 삭제");
                        delPost.setFont(MainUI.font);
                        delPost.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int result=JOptionPane.showConfirmDialog(null,"삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);

                                if(result==JOptionPane.YES_OPTION) {//차단함


                                    String delPost="delete from 게시글 where 게시글번호=?";
                                    try{
                                        PreparedStatement p=con.prepareStatement(delPost);
                                        p.setInt(1,postID);

                                        p.executeUpdate();

                                        JOptionPane.showMessageDialog(null, "삭제되었습니다.", null, JOptionPane.INFORMATION_MESSAGE);
                                        new UserProfileUI(con,seeID,id);
                                        dispose();

                                    }catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                }
                            }
                        });
                        popupMenu.add(delPost);
                        popupMenu.show(postList,e.getX(),e.getY());
                    }
                }
            }
        });

        postList.add(board);

        //2023.07.04
        JScrollPane scrollPane=new JScrollPane(board);
        scrollPane.getViewport().setBackground(Color.white); //행이 없는 빈 셀의 색
        postList.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(640,620));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        postList.setBounds(40,200,640,650);
        contentPane.add(postList);

        setVisible(true);
    }
    private DefaultTableModel sqlRun(Connection con, String sql) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object [][]data = null;
        String []columns = {"번호","사진","제목","설정"};


        ArrayList l = new ArrayList<Object>();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int count = 0;
            int count2 = 0;
            while(rs.next()) {
                //DB에서 사진은 파일 위치만 저장해서 파일 위치에 있는 사진을 갖고오기
                String i="";
                if(rs.getString(6)==null){
                    i=null;
                }else{
                    i=rs.getString(6).strip();
                }
                l.add(rs.getInt(1));
                l.add(new ImageIcon(i));
                l.add(rs.getString(2).strip());
                l.add(":");

                //2023.06.29 중간 정렬 하기!!

                count++;
            }
            data = new Object[count][4];
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < 4; j++) {
                    if (j == 1) {
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
                    case 0: return Integer.class;
                    case 1: return ImageIcon.class;
                    case 2, 3: return String.class;
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

        table.getColumnModel().getColumn(0).setPreferredWidth(110);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
    }
}
