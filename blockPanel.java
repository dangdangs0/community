import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

//2023.07.28 끝
class blockPanel extends JPanel {
    DefaultTableModel model;
    public blockPanel(Connection con, String id){
        setLayout(null);
        setBounds(250,40,800,600);
        setBackground(Color.white);


        JLabel blockList=new JLabel("차단 목록");
        blockList.setFont(SettingUI.semiTitleFont);
        blockList.setBounds(350,50,100,50);
        add(blockList);

        model=sqlRun(con,"select 차단아이디, 닉네임 from 차단, 회원 where 차단.차단아이디=회원.아이디 and 차단.사용자명='"+id+"'");

        JTable board=new JTable(model);
        board.setShowGrid(false);
        board.setFont(MainUI.font);
//        board.setSelectionBackground(Color.white);
        board.setTableHeader(null); //테이블 헤더 없앰
        tableFit(board);

        board.setRowHeight(50); //행 높이
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(board.getSelectedColumn()==2){
                    Object value=board.getValueAt(board.getSelectedRow(),0);
                    String blockUser=(String) value;

                    JPopupMenu popupMenu=new JPopupMenu("삭제");//차단

                    JMenuItem delBlock=new JMenuItem("차단 해제");
                    delBlock.setFont(MainUI.font);
                    delBlock.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {


                            String delBlock="delete from 차단 where 사용자명='"+id+"'and 차단아이디='"+blockUser+"'";
                            try{
                                Statement s=con.createStatement();
                                ResultSet r=s.executeQuery(delBlock);
                                if(r.next()){
                                    JOptionPane.showMessageDialog(null, "차단해제 되었습니다", null, JOptionPane.INFORMATION_MESSAGE);
                                    SwingUtilities.getWindowAncestor(getParent()).dispose();
                                    new MainUI(id);
                                }
                            }catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });

                    popupMenu.add(delBlock);
                    popupMenu.show(blockPanel.this,e.getX()+150,e.getY()+80); //마우스 포인트 클릭한 곳에 팝업메뉴 생성!!
                }
            }
        });
        board.setBounds(200,110,400,600);
        add(board);

    }
    private DefaultTableModel sqlRun(Connection con, String sql) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object [][]data = null;
        String []columns = {"차단아이디","닉네임","설정"};


        ArrayList l = new ArrayList<Object>();
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int count = 0;
            int count2 = 0;
            while(rs.next()) {

                l.add(rs.getString(1).strip());
                l.add(rs.getString(2).strip());
                l.add(":");

                count++;
            }
            data = new Object[count][3];
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < 3; j++) {
                    data[i][j] = l.get(count2);
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
                if (column >= 3) column = column % 3;
                switch(column) {
                    case 0, 1, 2: return String.class;
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
    }
}
