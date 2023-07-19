import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class profilePanel extends JPanel {
    //2023.07.19 DB랑 연동하기 완료
    //기능 구현 완
    public profilePanel(Connection con, String id){
        setLayout(null);
        setBounds(250,40,800,600);
        setBackground(Color.white);

        String sql="select * from 회원 where 아이디='"+id+"'";


        JTextField changeDescript=new HintTextField("한 줄 소개를 입력해주세요");


        ImageIcon changeUserIcon=new ImageIcon("D:\\study\\Community\\img\\user_icon_default.png");
        try{
            Statement stat=con.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            while(rs.next()){
                changeUserIcon=new ImageIcon(rs.getString(6).strip());
                Image image=changeUserIcon.getImage();
                Image newing=image.getScaledInstance(200,200,Image.SCALE_SMOOTH);
                changeUserIcon=new ImageIcon(newing);

                if(rs.getString(5)==null){//한 줄 소개가 처음 가입하면 null 이기 때문.
                    changeDescript=new HintTextField("한 줄 소개를 입력해주세요");
                }else{
                    String show=rs.getString(5).strip();
                    changeDescript=new JTextField(show);
                }


            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        Image image=changeUserIcon.getImage();
        Image newing=image.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        changeUserIcon=new ImageIcon(newing);

        JLabel changeIcon=new JLabel(changeUserIcon);
        changeIcon.setLocation(300,30);
        changeIcon.setSize(200,200);
        add(changeIcon);

        //사용자 사진 변경 버튼
        JButton changeImg=new JButton("사진 변경");
        changeImg.setFont(MainUI.font);
        changeImg.setBackground(Color.white);
        changeImg.setBounds(250,250,100,50);
        add(changeImg);

        //사진 삭제
        JButton delImg=new JButton("삭제");
        delImg.setFont(MainUI.font);
        delImg.setBounds(450,250,100,50);
        delImg.setBackground(Color.white);
        //삭제 버튼 눌렀을 때 다이얼로그 나옴. 나중에 더 예쁘게 꾸미기.
        delImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result= JOptionPane.showConfirmDialog(null,"삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);

                if(result==JOptionPane.YES_OPTION){//시스템 종료
                }
            }
        });
        add(delImg);


        //각각 라벨과 텍스트필드
        JLabel nickname=new JLabel("닉네임");
        nickname.setFont(MainUI.font);
        nickname.setBounds(250,320,100,50);
        add(nickname);

        JTextField changeNick=new JTextField(MainUI.nickname.getText());
        changeNick.setFont(MainUI.font);
        changeNick.setBounds(250,370,300,50);
        add(changeNick);

        JLabel description=new JLabel("한 줄 소개");
        description.setFont(MainUI.font);
        description.setBounds(250,440,100,50);
        add(description);

        changeDescript.setFont(MainUI.font);
        changeDescript.setBounds(250,490,300,50);
        add(changeDescript);


        JButton save=new JButton("저장");
        save.setFont(MainUI.font);
        save.setBackground(Color.white);
        save.setBounds(650,550,100,50);

        JTextField finalChangeDescript = changeDescript;
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //저장 버튼을 눌렀을 때만 반영해야함
                try{
                    String changeName=changeNick.getText();

                    String sqlNick = "select * from 회원 where 닉네임='" + changeName + "'and 아이디!='"+id+"'";

                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery(sqlNick);

                    String temp=changeName;
                    temp=temp.replaceAll("[a-zA-Z0-9가-힣]","");
                    temp=temp.replaceAll("_","");

                    if(temp.length()==0){
                        if(rs.next()){
                            JOptionPane.showMessageDialog(null, "이미 존재하는 별명입니다.", null, JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            try{
                                String changeq="update 회원 set 닉네임='"+changeName+"', 한줄소개='"+ finalChangeDescript.getText()+"' where 아이디='"+id+"'";
                                Statement s=con.createStatement();
                                ResultSet r=s.executeQuery(changeq);
                                if(r.next()){
                                    JOptionPane.showMessageDialog(null,"변경되었습니다.",null,JOptionPane.INFORMATION_MESSAGE);
                                }
                            }catch (SQLException ee){
                                throw new RuntimeException(ee);
                            }

                        }
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(save);
    }
}
