import java.util.Random;

class Logic {
    private int[][] board = new int[4][4];
    private int idles = 16;
    private boolean isChanged = false;
    private int score = 0;
    private boolean autoPlay;

    //инициализация переменных для новой игры и добавление двух чисел
    void newGame() {
        board = new int[4][4];
        idles = 16;
        isChanged = false;
        score = 0;
        autoPlay = false;
        addNumber();
        addNumber();
    }

    //добавление числа на доску(в массив "board")
    private void addNumber() {
        if (idles > 0) {
            int position = (int) (Math.random() * 16);
            while (position == 16 || board[position / 4][position % 4] != 0) {
                position = (int) (Math.random() * 16);
            }
            int value = (int) (Math.random() * 10) > 5 ? 4 : 2;
            board[position / 4][position % 4] = value;
            idles--;
        }
    }

    //движение в направлении (1-вверх, 2-вниз, 3-влево, 4-вправо)
    void move(int direction) {
        switch (direction) {
            case 1:
                isChanged = moveUp();
                break;
            case 2:
                isChanged = moveDown();
                break;
            case 3:
                isChanged = moveLeft();
                break;
            case 4:
                isChanged = moveRight();
                break;
            default:
                System.out.println("Invaild Command!");
        }
        //"isChanged" - принимает значение возвращаемое одним из методов
        //означает - повернулась ли доска или осталась на месте. если да , то добавляет число.
        if (isChanged) {
            addNumber();
        }
    }

    //поворот вверх
    private boolean moveUp() {
        boolean result = false;
        //двумерный массив перебирает все элементы доски
        for (int i = 0; i < 4; i++) {
            int limit = -1;
            for (int j = 1; j < 4; j++) {
                //если в точке j:i есть число
                if (board[j][i] != 0) {
                    //m увеличивается вместе с j но на 1 меньше
                    int m = j - 1;
                    while (m >= 0) {
                        //если точка m:i пуста
                        if (board[m][i] == 0) {
                            //если первый ряд
                            if (m == 0) {
                                //точка на первом ряду становится точкой на следующем ряду
                                board[m][i] = board[j][i];
                                //точка на следующем ряду исчезает
                                board[j][i] = 0;
                                //предел обновляется
                                limit = m;
                                //сообщает для "isChanged" что доска повернулась
                                result = true;
                                //выйти из цикла
                                break;
                            } else {
                                //иначе если ряд не первый то m движется к первому ряду
                                m--;
                            }
                            //иначе если точка не пуста и m больше предела
                        } else if (board[m][i] == board[j][i] && m > limit) {
                            //точка на следующем ряду исчезает
                            board[j][i] = 0;
                            //точка на текущем ряду умножается на 2
                            board[m][i] *= 2;
                            //предел обновляется
                            limit = m;
                            //счетчик не занятых клеток
                            idles++;
                            //доска повернулась
                            result = true;
                            //увеличивается счетчик очков
                            score += board[m][i]*10;
                            //выйти из цикла
                            break;
                            //иначе если m отсатала или перегнала j (обычно m = j-1)
                        } else if ((m + 1) != j) {
                            //запоминаем точку на ряду j
                            int temp = board[j][i];
                            //эта точка исчезает
                            board[j][i] = 0;
                            //точка что должна быть на следующем ряду становится той которую запомнили
                            board[m + 1][i] = temp;
                            result = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        //возвращаем результат для "isChanged"
        return result;
    }


    private boolean moveDown() {
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            int limit = 4;
            for (int j = 2; j >= 0; j--) {
                if (board[j][i] != 0) {
                    int m = j + 1;
                    while (m < 4) {
                        if (board[m][i] == 0) {
                            if (m == 3) {
                                board[m][i] = board[j][i];
                                board[j][i] = 0;
                                limit = m;
                                result = true;
                                break;
                            } else {
                                m++;
                            }
                        } else if (board[m][i] == board[j][i] && m < limit) {
                            board[j][i] = 0;
                            board[m][i] *= 2;
                            limit = m;
                            idles++;
                            result = true;
                            score += board[m][i]*10;
                            break;
                        } else if ((m - 1) != j) {
                            int temp = board[j][i];
                            board[j][i] = 0;
                            board[m - 1][i] = temp;
                            result = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    private boolean moveLeft() {
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            int limit = -1;
            for (int j = 1; j < 4; j++) {
                if (board[i][j] != 0) {
                    int m = j - 1;
                    while (m >= 0) {
                        if (board[i][m] == 0) {
                            if (m == 0) {
                                board[i][m] = board[i][j];
                                board[i][j] = 0;
                                limit = m;
                                result = true;
                                break;
                            } else {
                                m--;
                            }
                        } else if (board[i][m] == board[i][j] && m > limit) {
                            board[i][j] = 0;
                            board[i][m] *= 2;
                            limit = m;
                            idles++;
                            result = true;
                            score += board[m][i]*10;
                            break;
                        } else if ((m + 1) != j) {
                            int temp = board[i][j];
                            board[i][j] = 0;
                            board[i][m + 1] = temp;
                            result = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    private boolean moveRight() {
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            int limit = 4;
            for (int j = 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int m = j + 1;
                    while (m < 4) {
                        if (board[i][m] == 0) {
                            if (m == 3) {
                                board[i][m] = board[i][j];
                                board[i][j] = 0;
                                limit = m;
                                result = true;
                                break;
                            } else {
                                m++;
                            }
                        } else if (board[i][m] == board[i][j] && m < limit) {
                            board[i][j] = 0;
                            board[i][m] *= 2;
                            limit = m;
                            idles++;
                            result = true;
                            score += board[m][i]*10;
                            break;
                        } else if ((m - 1) != j) {
                            int temp = board[i][j];
                            board[i][j] = 0;
                            board[i][m - 1] = temp;
                            result = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    //возвращает массив доски
    int[][] getBoard() {
        return board;
    }
    //устанавливает определенную доску (для тестов)
    void setBoard(int[][] board){this.board = board;}

    //если счетчик не занятых клеток обнулится и доска не повернута, то геймовер ю деад и тд
    boolean isOver() {
        return idles <= 0 && !isChanged;
    }

    //возвращает счетчик очка
    int getScore() {
        return score;
    }

    // вкл\выкл автоигры
    void changeAutoPlay() {
        autoPlay = !autoPlay;
    }

    //включена ли автоигра
    private boolean isAutoPlay() {
        return autoPlay;
    }

    //работа автоигра
    void startAutoPlay(GUI gui){
        Random random = new Random();
        //создаем поток
        Thread thread = new Thread(() -> {
            //пока не выключишь
            while (isAutoPlay()) {
                try {
                    //поворот в рандом направлении
                    move(random.nextInt(4) + 1);
                    if (!isOver()) {
                        gui.refreshUI();
                    } else {
                        changeAutoPlay();
                        gui.gameOver();
                        break;
                    }
                    //интервал времени между поворотами
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //начать поток
        thread.start();
    }


}
