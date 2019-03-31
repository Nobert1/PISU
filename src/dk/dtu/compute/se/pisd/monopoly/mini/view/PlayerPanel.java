package dk.dtu.compute.se.pisd.monopoly.mini.view;

import dk.dtu.compute.se.pisd.designpatterns.Observer;
import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import gui_main.GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayerPanel extends JFrame implements Observer {

    private Game game;
    private Player currentPlayer;
    private JPanel contentPane;

    @Override
    public Container getContentPane() {
        return super.getContentPane();
    }

    public PlayerPanel (Game game, Player currentPlayer){


        super(currentPlayer.getName());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocation(710,game.getPlayers().indexOf(currentPlayer)*150);
        this.game = game;
        this.currentPlayer = game.getCurrentPlayer();
        contentPane = new JPanel();
        this.setSize(800, 150);
        this.setContentPane(contentPane);
        this.validate();
        this.setVisible(true);
    }

    @Override
    public void update(Subject subject) {
        contentPane.removeAll();

        JPanel playerPanel = new JPanel();
        playerPanel.setMinimumSize(new Dimension(80,100));
        playerPanel.setPreferredSize(new Dimension(80,120));
        playerPanel.setMaximumSize(new Dimension(120,150));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(currentPlayer.getColor());
        playerPanel.setBorder(new CompoundBorder(
                new CompoundBorder(BorderFactory.createEmptyBorder(2,2,2,2),
                LineBorder.createBlackLineBorder()),
                BorderFactory.createEmptyBorder(5,5,5,5)));
    }
}