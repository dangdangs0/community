import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//2023.05.19~2023.05.22 1차 끝
public class SignUpPopupUI extends JFrame {
    Connection con=null;
    public SignUpPopupUI(){
        try{//DB 연동
            
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("입력 실패");
        }

        Font font=new Font("Aa합정산스",Font.TRUETYPE_FONT, 22);
        String[] interest={"없음","요리","게임","정치","종교","만화","음악"};

        setSize(410,650);
        setResizable(false); //크기 변경 불가능
        setLocationRelativeTo(null); //화면 가운데 배치
        Container c=getContentPane();
        c.setLayout(null);
        c.setBackground(Color.white);

        //2023.05.21 아이디~관심지역 combobox, 비밀번호 * 표시
        JLabel idLabel=new JLabel("아이디");
        idLabel.setFont(font);
        idLabel.setHorizontalAlignment(JLabel.LEFT);
        idLabel.setSize(70,50);
        idLabel.setLocation(70,10);
        c.add(idLabel);

        JTextField id=new JTextField();
        id.setFont(font);
        id.setSize(250,40);
        id.setLocation(70,50);
        c.add(id);


        JLabel pwLabel=new JLabel("비밀번호");
        pwLabel.setFont(font);
        pwLabel.setSize(70,50);
        pwLabel.setLocation(70,100);
        c.add(pwLabel);

        JPasswordField pw=new JPasswordField();
        pw.setFont(font);
        pw.setSize(250,40);
        pw.setLocation(70,140);
        pw.setEchoChar('*');
        c.add(pw);


        JLabel confirmPwLabel=new JLabel("비밀번호 확인");
        confirmPwLabel.setFont(font);
        confirmPwLabel.setSize(120,50);
        confirmPwLabel.setLocation(70,190);
        c.add(confirmPwLabel);

        JPasswordField confirmPw=new JPasswordField();
        confirmPw.setFont(font);
        confirmPw.setSize(250,40);
        confirmPw.setLocation(70,230);
        confirmPw.setEchoChar('*');
        c.add(confirmPw);


        JLabel nicknameLabel=new JLabel("닉네임");
        nicknameLabel.setFont(font);
        nicknameLabel.setSize(120,50);
        nicknameLabel.setLocation(70,280);
        c.add(nicknameLabel);

        JTextField nickname=new JTextField();
        nickname.setFont(font);
        nickname.setSize(250,40);
        nickname.setLocation(70,320);
        c.add(nickname);


        JLabel interestLabel=new JLabel("관심 분야");
        interestLabel.setFont(font);
        interestLabel.setSize(120,50);
        interestLabel.setLocation(70,370);
        c.add(interestLabel);

        JComboBox interestCombo=new JComboBox(interest);
        interestCombo.setFont(font);
        interestCombo.setSize(250,40);
        interestCombo.setLocation(70,410);
        c.add(interestCombo);


        JButton regist=new JButton("회원가입");
        regist.setFont(font);
        regist.setSize(120,50);
        regist.setLocation(130,500);
        regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID=id.getText();
                String PW=pw.getText();
                String confirmPW=confirmPw.getText();
                String nick=nickname.getText();
                String inter=interestCombo.getSelectedItem().toString();

                if(ID.equals("")||PW.equals("")||confirmPW.equals("")||nick.equals("")){
                    //하나라도 공백이면
                    JOptionPane.showMessageDialog(null,"입력하지 않은 내용이 있습니다.",null,JOptionPane.WARNING_MESSAGE);
                } else if (PW.length()<8) {
                    JOptionPane.showMessageDialog(null,"비밀번호는 8글자 이상으로 적어주세요.",null,JOptionPane.WARNING_MESSAGE);
                    //비밀번호가 8글자 이상
                }else {
                    String sql = "select * from 회원 where 아이디='" + ID + "'";
                    try {
                        Statement stat = con.createStatement();
                        ResultSet rs = stat.executeQuery(sql);
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.", null, JOptionPane.WARNING_MESSAGE);
                        }else { //사용 가능한 아이디
                            if (!PW.equals(confirmPW)) {
                                //비밀번호 확인이 틀림
                                JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해 주세요", null, JOptionPane.WARNING_MESSAGE);
                            } else {

                                String sqlNick = "select * from 회원 where 닉네임='" + nick + "'";
                                Statement stnick = con.createStatement();
                                ResultSet rsnick = stnick.executeQuery(sqlNick);

                                String temp=nick;
                                temp=temp.replaceAll("[a-zA-Z0-9가-힣]","");
                                temp=temp.replaceAll("_","");
                                if(temp.length()==0){
                                    //닉네임 굿굿
                                    if (rsnick.next()) {
                                        JOptionPane.showMessageDialog(null, "이미 존재하는 별명입니다.", null, JOptionPane.WARNING_MESSAGE);
                                    } else {
                                        Integer interNum = 0;
                                        String findNum = "select 분야번호 from 분야 where 분야명='" + inter + "'";
                                        try {
                                            Statement stInter = con.createStatement();
                                            ResultSet rsInter = stInter.executeQuery(findNum);
                                            if (rsInter.next()) {
                                                interNum = rsInter.getInt("분야번호");
                                            }
                                        } catch (SQLException ex) {
                                            throw new RuntimeException(ex);
                                        }

                                        PreparedStatement pstmt = null;
                                        String insertUser = "insert into 회원 values(?,?,?,?,?,?)";
                                        pstmt = con.prepareStatement(insertUser);
                                        pstmt.setString(1, ID);
                                        pstmt.setString(2, PW);
                                        pstmt.setString(3, nick);
                                        if (inter.equals("없음")) {
                                            pstmt.setNull(4, Types.INTEGER);
                                        } else {
                                            pstmt.setInt(4, interNum);
                                        }

                                        pstmt.setString(5, null);
                                        pstmt.setString(6, null);
                                        pstmt.executeUpdate();

                                        JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.", null, JOptionPane.INFORMATION_MESSAGE);
                                        dispose();

                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "영어, 숫자, 한글, _ 로만 정해주세요.", null, JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        c.add(regist);

        setVisible(true);
    }

}
