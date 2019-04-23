package dk.dtu.compute.se.pisd.monopoly.mini.view;


import dk.dtu.compute.se.pisd.designpatterns.Observer;
import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Colors;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import gui_main.GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerPanel extends JFrame {

    private Game game;
    private Player currentPlayer;
    private JPanel contentPane;

    @Override
    public Container getContentPane() {
        return super.getContentPane();
    }

    public PlayerPanel(Game game, Player currentPlayer) {

        super(currentPlayer.getName());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocation(710, game.getPlayers().indexOf(currentPlayer) * 150);
        this.game = game;
        this.currentPlayer = game.getCurrentPlayer();
        contentPane = new JPanel();
        this.setSize(800, 150);

        this.setContentPane(contentPane);
        this.validate();
        this.setVisible(true);

    }

    @SuppressWarnings("Duplicates")
    public void update(Player player) {
        contentPane.removeAll();
        JPanel playerPanel = new JPanel();
        playerPanel.setMinimumSize(new Dimension(80, 60));
        playerPanel.setPreferredSize(new Dimension(80, 80));
        playerPanel.setMaximumSize(new Dimension(120, 100));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(player.getColor());
        playerPanel.setBorder(new CompoundBorder(
                new CompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),
                        LineBorder.createBlackLineBorder()),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JLabel l = new JLabel(player.getName());
        JLabel j = new JLabel(String.valueOf(player.getBalance()));
        playerPanel.add(l);
        playerPanel.add(j);

        contentPane.add(playerPanel);


        ArrayList<Color> colorArrayList = new ArrayList<>();
        for (Property property2 : player.getOwnedProperties()) {
            if (!colorArrayList.contains(property2.getColor()))
                colorArrayList.add(property2.getColor());
        }


            for (Color color : colorArrayList) {
                JPanel estatepanel = new JPanel();
                estatepanel.setMinimumSize(new Dimension(80, 60));
                estatepanel.setPreferredSize(new Dimension(80, 80));
                estatepanel.setMaximumSize(new Dimension(120, 100));
                estatepanel.setLayout(new BoxLayout(estatepanel, BoxLayout.Y_AXIS));
                estatepanel.setBorder(new CompoundBorder(
                        new CompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),
                                LineBorder.createBlackLineBorder()),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                //Farven ser godt ud pt,
                String nameString = "";
                for (Property property1 : player.getOwnedProperties()) {
                    if (color.equals(property1.getColor())) {
                        nameString += property1.getName() + "\n";
                    }
                }

                JTextArea textArea = new JTextArea(3, 5);

                Font font = new Font("Times new roman", Font.PLAIN, 10);
                textArea.setText(nameString);
                textArea.setFont(font);
                textArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(textArea);
                this.add(scrollPane, BorderLayout.CENTER);
                JPanel estatecolorpanel = new JPanel();
                estatecolorpanel.setMinimumSize(new Dimension(60, 15));
                estatecolorpanel.setPreferredSize(new Dimension(60, 15));
                estatecolorpanel.setMaximumSize(new Dimension(80, 15));
                estatecolorpanel.setBackground(color);

                estatepanel.add(estatecolorpanel);
                estatepanel.add(textArea);
                contentPane.add(estatepanel);
            }
                this.revalidate();
                this.repaint();
        }
    }