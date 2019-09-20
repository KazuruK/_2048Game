import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private JPanel boardPanel;
    private JPanel controlPanel;
    private JPanel arrowPanel;
    private JTextField scoreLabel;
    private JTextField score;
    private JButton newGame;
    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;
    private JButton autoPlayButton;
    private Cell[] cells;
    private CellComponent cellComponent;


    private Logic controller = new Logic();

    //|
    GUI() {
        //this.setPreferredSize(new Dimension(700, 500));
        //адаптер для управления клавиатурой
        DirectionKeyAdapter dKAdapter = new DirectionKeyAdapter();
        //менеджер отрисованных клеток и панель доски
        cellComponent = new CellComponent(new GridLayout(4, 4));
        cells = new Cell[16];
        //расстановка отрисованных клеток по местам
        int x = 0;
        int y = 0;
        int w = 500/4;
        int h = 500/4;
        int c = 0;
        for (int i = 0; i < 16; i++) {
            c++;
            cells[i] = new Cell(x, y);
            x += w;
            if (c == 4) {
                c = 0;
                y += h;
                x = 0;
            }
            cellComponent.addCell(cells[i]);
        }
        cellComponent.initCells();
        this.getContentPane().add(BorderLayout.WEST, cellComponent);
        //панель управления (справа)
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(200, 500));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        //лэйбл слова Счет
        scoreLabel = new JTextField("Счет");
        scoreLabel.setFont(new Font("Serief", Font.BOLD, 35));
        scoreLabel.setPreferredSize(new Dimension(200, 50));
        scoreLabel.setEditable(false);
        controlPanel.add(scoreLabel);
        //лэйбл счетчика очко в
        score = new JTextField("0");
        score.setPreferredSize(new Dimension(200, 200));
        score.setFont(new Font("Serief", Font.BOLD, 30));
        score.setEditable(false);
        controlPanel.add(score);

        //панель кнопок
        JPanel newButtonPanel = new JPanel();
        newGame = new JButton("Новая игра");
        newGame.setName("newGame");
        newGame.setPreferredSize(new Dimension(200, 70));
        newGame.addActionListener(new NewGameListener());
        newButtonPanel.add(newGame);
        controlPanel.add(newButtonPanel);

        //панель автоигры
        JPanel autoButtonPanel = new JPanel();
        autoPlayButton = new JButton("автоигра");
        autoPlayButton.setName("autoPlay");
        autoPlayButton.setPreferredSize(new Dimension(200, 70));
        autoPlayButton.addActionListener(new AutoPlayListener(this));
        autoButtonPanel.add(autoPlayButton);
        controlPanel.add(autoButtonPanel);

        //панель кнопок управления
        arrowPanel = new JPanel(new GridLayout(2, 2));
        arrowPanel.setPreferredSize(new Dimension(100, 150));
        DirectionListener dListener = new DirectionListener();
        //кнопки вверх вниз вправо влево
        up = new JButton("Вверх");
        up.setName("up");
        up.addActionListener(dListener);
        down = new JButton("Вниз");
        down.setName("down");
        down.addActionListener(dListener);
        left = new JButton("Влево");
        left.setName("left");
        left.addActionListener(dListener);
        right = new JButton("Вправо");
        right.setName("right");
        right.addActionListener(dListener);
        arrowPanel.add(up);
        arrowPanel.add(down);
        arrowPanel.add(left);
        arrowPanel.add(right);

        controlPanel.add(arrowPanel);

        this.getContentPane().add(BorderLayout.EAST, controlPanel);
        this.setSize(700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(dKAdapter);
    }

    //обновляет видимое состояние доски
     void refreshUI() {
        int[][] board = controller.getBoard();
        for (int i = 0; i < 16; i++) {
            if (board[i / 4][i % 4] == 0) {
                cells[i].setText("");
            } else {
                int value = board[i / 4][i % 4];
                cells[i].setText(Integer.toString(value));
            }
        }
        score.setText(Integer.toString(controller.getScore()));
        requestFocus();
    }

     void gameOver() {
        cells[0].setText("*");
        cells[1].setText("*");
        cells[2].setText("*");
        cells[3].setText("*");
        cells[4].setText("G");
        cells[5].setText("A");
        cells[6].setText("M");
        cells[7].setText("E");
        cells[8].setText("O");
        cells[9].setText("V");
        cells[10].setText("E");
        cells[11].setText("R");
        cells[12].setText("*");
        cells[13].setText("*");
        cells[14].setText("*");
        cells[15].setText("*");
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.pack();
        gui.setVisible(true);
    }

    //слушатель действий для кнопки автоигры
    class AutoPlayListener implements ActionListener {
        GUI gui;
        AutoPlayListener(GUI gui){
            this.gui = gui;
        }
        public void actionPerformed(ActionEvent e) {
            controller.changeAutoPlay();
            controller.startAutoPlay(gui);
        }
    }

    //слушатель кнопок управления
    class DirectionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(up)) {
                controller.move(1);
                //cellComponent.animateUp();
            } else if (e.getSource().equals(down)) {
                controller.move(2);
                //cellComponent.animateDown();
            } else if (e.getSource().equals(left)) {
                controller.move(3);
                //cellComponent.animateLeft();
            } else if (e.getSource().equals(right)) {
                controller.move(4);
                //cellComponent.animateRight();
            }

            if (!controller.isOver()) {
                refreshUI();
            } else {
                gameOver();
            }
        }
    }

    //слушатель для клавиш
    class DirectionKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'w':
                    controller.move(1);
                    break;
                case 's':
                    controller.move(2);
                    break;
                case 'a':
                    controller.move(3);
                    break;
                case 'd':
                    controller.move(4);
                    break;

            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    controller.move(1);
                    break;
                case KeyEvent.VK_DOWN:
                    controller.move(2);
                    break;
                case KeyEvent.VK_LEFT:
                    controller.move(3);
                    break;
                case KeyEvent.VK_RIGHT:
                    controller.move(4);
                    break;
            }

            if (!controller.isOver()) {
                refreshUI();
            } else {
                gameOver();
            }
        }
    }

    //слушатель для кнопки новой игры
    class NewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.newGame();
            refreshUI();
        }
    }
}
