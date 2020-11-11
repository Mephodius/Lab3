import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }
    public int getColumnCount() {
// В данной модели два столбца
        return 4;
    }
    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }
    public Double getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step*row;
        if (col==0) {
// Если запрашивается значение 1-го столбца, то это X
            return x;
        } else {
            Double result;
            if(col==1){
// Если запрашивается значение 2-го столбца, то это значение
// многочлена
             result = coefficients[0];
            for(int i=0; i<coefficients.length-1;i++){
                result=result*x+coefficients[i+1];
            }
            }else{
                if(col==2){
                     result = 0.0;
                    for(int i=0; i<coefficients.length;i++){
                        result+=coefficients[i]*Math.pow(x, coefficients.length-i-1);
                    }
                }
                else{
                    result=Math.abs(getValueAt(row, 1)-getValueAt(row, 2));
                }
            }


// Вычисление значения в точке по схеме Горнера.
// Вспомнить 1-ый курс и реализовать
// ...
            return result;
        }
    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
// Название 2-го столбца
                return "Значение многочлена по Горнеру";
            case 2:
//название 3-го столбца
                return "Классически рассчитаное";
            default:
//название 4-го столбца
                return "Их разница";
        }
    }
    public Class<?> getColumnClass(int col) {
// И в 1-ом и во 2-ом столбце находятся значения типа Double
        return Double.class;
    }
}
