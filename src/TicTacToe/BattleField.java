package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleField extends JPanel {
    private GameWindow gameWindow;

    private int mode;
    private int fieldSize;
    private int winningLength;

    private boolean isInit;

    int cellWidth;
    int cellHeight;

    int shift = 15; // сдвиг для прорисовки крестиков и нольков

    public BattleField(GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        setBackground( Color.DARK_GRAY );

        addMouseListener( new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int cellX = e.getX() / cellWidth;
                int cellY = e.getY() / cellHeight;

                if (!Logic.isFinishedGame) {
                    Logic.humanTurn( cellX, cellY );
                }
                repaint();

                if (Logic.isFinishedGame) {
                    JOptionPane.showMessageDialog( null, Logic.dialogFinished );
                }
            }
        } );
    }

    public void startNewGame(int mode, int fieldSize, int winningLength) {
        this.mode = mode;
        this.fieldSize = fieldSize;
        this.winningLength = winningLength;

        isInit = true;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent( g );

        if (!isInit) {
            return;
        }

        cellWidth = getWidth() / fieldSize;
        cellHeight = getHeight() / fieldSize;

        for (int i = 0; i < fieldSize; i++) {
            int y = i * cellHeight;
            g.drawLine( 0, y, getWidth(), y );
        }

        for (int i = 0; i < fieldSize; i++) {
            int x = i * cellWidth;
            g.drawLine( x, 0, x, getHeight() );
        }

        for (int i = 0; i < Logic.SIZE; i++) {
            for (int j = 0; j < Logic.SIZE; j++) {
                if (Logic.map[i][j] == Logic.DOT_X) {
                    drawX( g, j, i );
                }
                if (Logic.map[i][j] == Logic.DOT_O) {
                    drawO( g, j, i );
                }
            }
        }
        if (Logic.isFinishedGame) {
            winLine( g, Logic.winLine );
        }

    }

    private void drawO(Graphics g, int cellX, int cellY) {
        ((Graphics2D) g).setStroke( new BasicStroke( 5 ) );
        g.setColor( Color.ORANGE );
        g.drawOval( cellX * cellWidth + shift, cellY * cellHeight + shift,
                cellWidth - (shift * 2), cellWidth - (shift * 2) );

    }

    private void drawX(Graphics g, int cellX, int cellY) {

        ((Graphics2D) g).setStroke( new BasicStroke( 5 ) );
        g.setColor( Color.PINK );
        g.drawLine( cellX * cellWidth + shift, cellY * cellHeight + shift,
                (cellX + 1) * cellWidth - shift, (cellY + 1) * cellHeight - shift );
        g.drawLine( (cellX + 1) * cellWidth - shift, cellY * cellHeight + shift,
                cellX * cellWidth + shift, (cellY + 1) * cellHeight - shift );

    }

    private void winLine(Graphics g, int[] winLine) {

        ((Graphics2D) g).setStroke( new BasicStroke( 2 ) );
        g.setColor( Color.red );
        g.drawLine( winLine[0] * cellWidth + cellWidth / 2, winLine[1] * cellHeight + cellHeight / 2,
                winLine[2] * cellWidth + cellWidth / 2, winLine[3] * cellHeight + cellHeight / 2 );

    }
}

