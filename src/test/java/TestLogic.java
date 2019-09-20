import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestLogic {
    private Logic logic;

    //инициализация перед каждым тестом
    @BeforeEach
    void setUp() {
        logic = new Logic();
        logic.newGame();
    }

    //проверка на то что после вызова метода "move()" доска как-либо меняется
    @Test
    void boardIsChangingAfterMoveTest() {
        boolean passed = false;
        int[][] board = new int[8][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = logic.getBoard()[i][j];
            }
        }
        logic.move(1);
        logic.move(2);
        logic.move(3);
        logic.move(4);
        logic.move(1);
        logic.move(4);
        logic.move(1);
        logic.move(2);
        logic.move(3);
        logic.move(4);
        logic.move(1);
        logic.move(4);
        logic.move(1);
        logic.move(2);
        logic.move(3);
        logic.move(4);
        logic.move(1);
        logic.move(4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                passed = board[i][j] != logic.getBoard()[i][j];
                if (passed) break;
            }
        }
        Assertions.assertTrue(passed);
    }

    //проверка на правильную работу механизма сложения чисел при вызове "move()" в разных направлениях
    @Test
    void moveTest() {

        //move up
        int[][] board1 = new int[][]{
                new int[]{0, 0, 4, 0},
                new int[]{0, 0, 4, 0},
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 0},
        };

        logic.setBoard(board1);
        logic.move(1);
        Assertions.assertEquals(8, logic.getBoard()[0][2]);

        //move down
        int[][] board2 = new int[][]{
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 0},
                new int[]{2, 0, 0, 0},
                new int[]{2, 0, 0, 0},
        };

        logic.setBoard(board2);
        logic.move(2);
        Assertions.assertEquals(4, logic.getBoard()[3][0]);


        //move left
        int[][] board3 = new int[][]{
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 0},
                new int[]{8, 8, 0, 0},
        };

        logic.setBoard(board3);
        logic.move(3);
        Assertions.assertEquals(16, logic.getBoard()[3][0]);

        //move right
        int[][] board4 = new int[][]{
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 0, 0},
                new int[]{0, 0, 1024, 1024},
        };

        logic.setBoard(board4);
        logic.move(4);
        Assertions.assertEquals(2048, logic.getBoard()[3][3]);
    }

    //проверка работы "автоигры"
    @Test
    void autoPlayTest(){
        int c = 0;
        logic.changeAutoPlay();
        logic.startAutoPlay(new GUI());
        if (logic.isOver()){
            Assertions.assertTrue(logic.getScore() != 0);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logic.changeAutoPlay();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c += logic.getBoard()[i][j];
            }
        }
        Assertions.assertTrue(c > 8);
    }

}
