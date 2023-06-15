import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserProfileUI extends JFrame {
    public UserProfileUI(){
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
                new PostUI();
                dispose();
            }
        });
        contentPane.add(backIcon);

        //나중에는 DB에서 해당 작성자 사진을 가져올거임
        ImageIcon wIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");
        Image img=wIcon.getImage();
        Image newing=img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        wIcon=new ImageIcon(newing);

        JLabel writerIcon=new JLabel(wIcon);
        writerIcon.setBounds(100,50,100,100);
        contentPane.add(writerIcon);


        JLabel writerName=new JLabel("닉네임");
        writerName.setLocation(220,60);
        writerName.setSize(200,50);
        writerName.setFont(SettingUI.semiTitleFont);
        writerName.setForeground(Color.BLACK);
        writerName.setHorizontalAlignment(JLabel.LEFT);
        contentPane.add(writerName);

        JLabel writerProfile=new JLabel("한줄소개쓰으으으");
        writerProfile.setLocation(220,100);
        writerProfile.setSize(200,50);
        writerProfile.setFont(SettingUI.semiTitleFont);
        writerProfile.setForeground(Color.BLACK);
        writerProfile.setHorizontalAlignment(JLabel.LEFT);
        contentPane.add(writerProfile);

        JPanel postList=new JPanel();
        postList.setBounds(40,200,650,700);

        //각각의 포스트들 JPanel에 붙이기! 2023.05.31
        String[] col={"사진,제목,설정"};
        Object temp[][]= {{new ImageIcon("C:\\Users\\손혜진\\Pictures\\잡\\13.png"),"임시 게시판입니다",":"},
        {new ImageIcon("C:\\Users\\손혜진\\Pictures\\잡\\14.png"),"대체 왜 안나와!??",":"},
        {new ImageIcon("C:\\Users\\손혜진\\Pictures\\잡\\15.png"),"미안합니다 테스트 해야합니다",":"}};

        JTable board=new JTable(temp,col);
        board.setShowGrid(false);
        board.setFont(MainUI.font);
        board.setSelectionBackground(Color.white);
        board.setTableHeader(null); //테이블 헤더 없앰
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



        //내일은 옆에 애들 마저 붙이기
//        board.getColumnModel().getColumn(2).setCellRenderer(new TableCell());
//        board.getColumnModel().getColumn(2).setCellEditor(new TableCell());
        board.setRowHeight(100); //행 높이
        postList.add(board);
        JScrollPane scrollPane=new JScrollPane(board);
        postList.add(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(postList);

        setVisible(true);
}

}
