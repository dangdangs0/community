import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableCell extends  AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    JButton jButton;

    public TableCell(){
        jButton=new JButton(":");
        jButton.setBackground(Color.white);
        jButton.setBorder(new LineBorder(Color.white));
        //나중에 팝업 메뉴도 넣기
//        jButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //사용자 입력받아서 차단
//                //즉 2번째 열의 값을 받아와야함
//            }
//        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return jButton;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return jButton;
    }
}
