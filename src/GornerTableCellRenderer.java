import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    // Ищем ячейки, строковое представление которых совпадает с needle
// (иголкой). Применяется аналогия поиска иголки в стоге сена, в роли
// стога сена - таблица
    private String needle = null;
    private Boolean looking4s;
    private DecimalFormat formatter =
            (DecimalFormat)NumberFormat.getInstance();
    public GornerTableCellRenderer() {
// Показывать только 5 знаков после запятой
        formatter.setMaximumFractionDigits(5);
// Не использовать группировку (т.е. не отделять тысячи
// ни запятыми, ни пробелами), т.е. показывать число как "1000",
// а не "1 000" или "1,000"
        formatter.setGroupingUsed(false);
// Установить в качестве разделителя дробной части точку, а не
// запятую. По умолчанию, в региональных настройках
// Россия/Беларусь дробная часть отделяется запятой
        DecimalFormatSymbols dottedDouble =
                formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
// Разместить надпись внутри панели
        panel.add(label);
// Установить выравнивание надписи по различным краям панели
      // panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        // Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);
// Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
        if ((col==1||col==2)&& needle!=null && needle.equals(formattedDouble)) {
// Номер столбца = 1 (т.е. второй столбец) + иголка не null
// (значит что-то ищем) +
// значение иголки совпадает со значением ячейки таблицы -
// окрасить задний фон панели в красный цвет
            panel.setBackground(Color.RED);
        } else {
// Иначе - в обычный белый
            panel.setBackground(Color.WHITE);
        }
        Double temp=0.0;
        if(label.getText().length()!=0) {
            temp = Math.abs(Double.parseDouble(label.getText()));
        }
        if (looking4s!=null&&looking4s) {
            Boolean simple = false;
            if ((temp + 0.1) >= (temp.intValue() + 1)) {
                simple = true;
                for (int i = 2; i < (temp.intValue() + 1) / 2+1; i++) {
                    if ((temp.intValue() + 1) % i == 0) {
                        simple = false;
                        break;
                    }
                }
            } else if (temp - 0.1 <= temp.intValue()) {
                simple = true;
                for (int i = 2; i < temp.intValue() / 2+1; i++) {
                    if (temp.intValue() % i == 0) {
                        simple = false;
                        break;
                    }
                }
            }
            if (simple)
                panel.setBackground(Color.BLUE);

        }
        if(label.getText().length()!=0) {
            if (Double.parseDouble(label.getText())>0) {
                panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            }
            else if (Double.parseDouble(label.getText()) < 0) {
                panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            }
            else {
                panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            }
        }
        return panel;
    }
    public void setNeedle(String needle) {
        this.needle = needle;
    }
    public void setLFS(Boolean l4s) {
        this.looking4s = l4s;
    }
}
