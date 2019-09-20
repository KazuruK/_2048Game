import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//имплементируется от ActionListener для реализации рисования (такая вот фича)
public class CellComponent extends JPanel implements ActionListener {
    private List<Cell> listCell =  new CopyOnWriteArrayList<>();
    //таймер нужен для рисования (типа рендеринг)
    private Timer timer = new Timer(20, this);

    //конструктор
    CellComponent(GridLayout layout){
        timer.start();
        setLayout(layout);
    }

    //добавляем объект Сell в список
    void addCell(Cell c){
        listCell.add(c);
    }

    //добавляем объект CellComponent для каждого Cell
    void initCells(){
        for (Cell cell : listCell){
            cell.setCellComponent(this);

        }
    }
/*
    //проверка столкновения слева
    public boolean overlapsLeft(Cell c){
        for (Cell cell : listCell){
            if (c.posX() == cell.posX() + cell.getWidth()) {
                if (!cell.isMooving()) {
                    return true;
                }
            }
        }
        return false;
    }

    //проверка столкновения справа
    public boolean overlapsRight(Cell c){
        for (Cell cell : listCell){
            if (c.posX() == cell.posX() - cell.getWidth()) {
                if (!cell.isMooving()) {
                    return true;
                }
            }
        }
        return false;
    }

    //проверка столкновения сверху
    public boolean overlapsUp(Cell c){
        for (Cell cell : listCell){
            if (c.posY() == cell.posY() + cell.getHeight()) {
                if (!cell.isMooving()) {
                    return true;
                }
            }
        }
        return false;
    }

    //проверка столкновения снизу
    public boolean overlapsDown(Cell c){
        for (Cell cell : listCell){
            if (c.posY() == cell.posY() - cell.getHeight()) {
                if (!cell.isMooving()) {
                    return true;
                }
            }
        }
        return false;
    }
*/
    //отрисовка доски
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBackground(Color.YELLOW);
        for(Cell cell: listCell){
            cell.paint(g2d);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    //та самая фича от ActionListener. вызывается метод "repaint()" по таймеру бесконечно.
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
