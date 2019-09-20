import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cell extends JPanel implements ActionListener {
    private int x;
    private int y;
    private int width;
    private int height;
    private String digit;
    private CellComponent cellComponent;
    private Timer timer = new Timer(20, this);

    Cell(int x, int y) {
        timer.start();
        this.x = x;
        this.y = y;
        width = 500 / 4;
        height = 500 / 4;
        digit = "";
    }

    void setCellComponent(CellComponent cellComponent) {
        this.cellComponent = cellComponent;
    }

    public int posX() {
        return x;
    }

    public int posY() {
        return y;
    }

    //рисование одной клетки
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.ORANGE);
        g.drawRoundRect(x, y, width, height, 10, 10);
        g.setColor(Color.black);
        g.fillRoundRect(x, y, width - 1, height - 1, 10, 10);
        g.setColor(Color.white);
        g.setFont(new Font("font", Font.BOLD, 30));
        g.drawString(digit, x + width / 2 - 10, y + height / 2 + 5);
    }

    //фича от ActionListener для рендеринга
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    //устанавливает число
    void setText(String s) {
        digit = s;
    }

    //ширина
    @Override
    public int getWidth() {
        return width;
    }

    //высота
    @Override
    public int getHeight() {
        return height;
    }

}
