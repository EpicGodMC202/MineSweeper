import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MineSweeper implements ActionListener {
    JFrame frame = new JFrame("RaceToGold's MineSweeper");
    JButton reset = new JButton("Reset");
    JButton[][] buttons;
    int[][] counts;
    Container grid = new Container();
    int mineCount, gridCount;
    final static int MINE = 10;

    public MineSweeper() {
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        reset.addActionListener(this);
        //button grid

        gridCount = getIntergerUserInput("Grid XY?, MAX 40");
        if (gridCount > 40)
        {
            gridCount = getIntergerUserInput("Grid XY?, MAX 40");
        }
        mineCount = getIntergerUserInput("Amount of mines?");
        if (mineCount == -1) return;
        if (gridCount == -1) return;

        buttons = new JButton[gridCount][gridCount];
        counts = new int[gridCount][gridCount];

        grid.setLayout(new GridLayout(gridCount, gridCount));
        for (int a = 0; a < buttons.length; a++) {
            for (int b = 0; b < buttons[0].length; b++) {
                buttons[a][b] = new JButton();
                buttons[a][b].addActionListener(this);
                grid.add(buttons[a][b]);
            }
        }
        frame.add(grid, BorderLayout.CENTER);
        createRandomMines();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MineSweeper();
    }

    public int getIntergerUserInput(String question)
    {
        int answer = -1;
        String input = JOptionPane.showInputDialog(frame, question, "30");
        if (input == null){
            frame.setVisible(false);
            frame.dispose();
            return -1;
        }
        try{
            answer = Integer.parseInt(input);
        }catch (NumberFormatException e)
        {
            getIntergerUserInput(question);
        }
        return answer;
    }


    public void createRandomMines() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int x = 0; x < counts.length; x++) {
            for (int y = 0; y < counts[0].length; y++) {
                list.add(x * 100 + y);
            }
        }
        counts = new int[gridCount][gridCount];
        for (int a = 0; a < mineCount; a++) {
            int choice = (int) (Math.random() * list.size());
            counts[list.get(choice) / 100][list.get(choice) % 100] = MINE;
            list.remove(choice);
        }
        for (int x = 0; x < counts.length; x++) {
            for (int y = 0; y < counts[0].length; y++) {
                if (counts[x][y] != MINE) {
                    int neighborcount = 0;
                    if (x > 0 && y > 0 && counts[x - 1][y - 1] == MINE) {
                        neighborcount++;
                    }
                    if (y > 0 && counts[x][y - 1] == MINE) {
                        neighborcount++;
                    }
                    if (x < counts.length -1 && y > 0 && counts[x+1][y-1] == MINE) {
                        neighborcount++;
                    }
                    if (x > 0 && counts[x - 1][y] == MINE) {
                        neighborcount++;
                    }
                    if (x < counts.length - 1 && counts[x + 1][y] == MINE) {
                        neighborcount++;
                    }
                    if (x > 0 && y < counts[0].length - 1 && counts[x - 1][y + 1] == MINE) {
                        neighborcount++;
                    }
                    if (y < counts[0].length - 1 && counts[x][y + 1] == MINE) {
                        neighborcount++;
                    }
                    if (x < counts.length - 1 && y < counts[0].length - 1 && counts[x + 1][y + 1] == MINE) {
                        neighborcount++;
                    }

                    counts[x][y] = neighborcount;
                }
            }
        }
    }

    public void lostGame() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[0].length; y++) {
                if (buttons[x][y].isEnabled()) {
                    if (counts[x][y] != MINE) {
                        buttons[x][y].setText(counts[x][y] + "");
                        buttons[x][y].setEnabled(false);
                    } else {
                        buttons[x][y].setText("X");
                        buttons[x][y].setEnabled(false);
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "You Lose!");
    }

    public void clearZeros(ArrayList<Integer> toClear)
    {
        if (toClear.size() == 0)
        {
            return;
        }else{
            int x = toClear.get(0) / 100;
            int y = toClear.get(0) % 100;
            toClear.remove(0);

                if (x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()) {//up left
                    buttons[x-1][y-1].setText(counts[x-1][y-1]+"");
                    buttons[x-1][y-1].setEnabled(false);
                    if (counts[x-1][y-1] == 0)toClear.add((x-1)*100 + (y-1));
                }
                if (y > 0 && buttons[x][y-1].isEnabled()) {//up
                    buttons[x][y-1].setText(counts[x][y-1]+"");
                    buttons[x][y-1].setEnabled(false);
                    if (counts[x][y-1] == 0)toClear.add((x)*100 + (y-1));

                }
                if (x < counts.length -1 && y > 0 && buttons[x+1][y-1].isEnabled()){//up right
                    buttons[x+1][y-1].setText(counts[x+1][y-1]+"");
                    buttons[x+1][y-1].setEnabled(false);
                    if (counts[x+1][y-1] == 0)toClear.add((x+1)*100 + (y-1));
                }
                if (x > 0 && buttons[x-1][y].isEnabled()) {//left
                    buttons[x-1][y].setText(counts[x-1][y]+"");
                    buttons[x-1][y].setEnabled(false);
                    if (counts[x-1][y] == 0)toClear.add((x-1)*100 + (y));
                }
                if (x < counts.length -1 && buttons[x+1][y].isEnabled()){//right
                    buttons[x+1][y].setText(counts[x+1][y]+"");
                    buttons[x+1][y].setEnabled(false);
                    if (counts[x+1][y] == 0)toClear.add((x+1)*100 + (y));
                }
                if (x > 0 && y < counts[0].length -1 && buttons[x-1][y+1].isEnabled()) {//down left
                    buttons[x-1][y+1].setText(counts[x-1][y+1]+"");
                    buttons[x-1][y+1].setEnabled(false);
                    if (counts[x-1][y+1] == 0)toClear.add((x-1)*100 + (y+1));
                }
                if (y < counts[0].length -1 && buttons[x][y+1].isEnabled()) {//down
                    buttons[x][y+1].setText(counts[x][y+1]+"");
                    buttons[x][y+1].setEnabled(false);
                    if (counts[x][y+1] == 0)toClear.add((x)*100 + (y+1));

                }
                if (x < counts.length -1 && y < counts[0].length -1 && buttons[x+1][y+1].isEnabled()){//down right
                    buttons[x+1][y+1].setText(counts[x+1][y+1]+"");
                    buttons[x+1][y+1].setEnabled(false);
                    if (counts[x+1][y+1] == 0)toClear.add((x+1)*100 + (y+1));
                }
            clearZeros(toClear);
        }
    }

    public void checkWin()
    {
        boolean won = true;
        for (int x = 0; x < counts.length; x++) {
            for (int y = 0; y < counts[0].length; y++) {
                if (counts[x][y] != MINE && buttons[x][y].isEnabled())
                {
                    won = false;
                }
            }
        }
        if (won)
        {
            JOptionPane.showMessageDialog(frame, "You win!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reset)) {
            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons[0].length; y++) {
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                }
            }
            createRandomMines();
        } else {
            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons[0].length; y++) {
                    if (e.getSource().equals(buttons[x][y])) {
                        if (counts[x][y] == MINE) {
                            buttons[x][y].setForeground(Color.RED);
                            buttons[x][y].setText("X");
                            lostGame();
                        }
                        else if (counts[x][y] == 0)
                        {
                            buttons[x][y].setText(counts[x][y] + "");
                            buttons[x][y].setEnabled(false);
                            ArrayList<Integer> toClear = new ArrayList<>();
                            toClear.add(x*100+y);
                            clearZeros(toClear);
                            checkWin();
                        }
                        else {
                            buttons[x][y].setText(counts[x][y] + "");
                            buttons[x][y].setEnabled(false);
                            checkWin();
                        }
                    }
                }
            }
        }
    }
}
