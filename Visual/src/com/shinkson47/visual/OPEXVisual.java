package com.shinkson47.visual;

import com.shinkson47.OPEX.backend.errormanagement.exceptions.OPEXStartFailure;
import com.shinkson47.OPEX.backend.runtime.engine.OPEX;
import com.shinkson47.OPEX.backend.runtime.environment.OPEXEnvironmentUtils;
import com.shinkson47.OPEX.backend.runtime.environment.ShutdownCauses;
import com.shinkson47.OPEX.backend.runtime.threading.OPEXGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Front end class for the OPEX Visual toolbox.
 */
public class OPEXVisual implements OPEXGame {
    //Main window
    private JFrame DisplayWindow;

    //Main panel
    private JPanel panelMain;

    //Left side button labels
    private JLabel btnExit;
    private JLabel btnRuntime;
    private JLabel btnCommand;
    private JLabel btnCJAR;
    private JLabel btnConsole;
    private JLabel btnEngine;
    private JLabel btnThreading;
    private JLabel btnHooks;

    //Card parent
    private JPanel panelCard;

    //Cards
    private JPanel cardEngine;
    private JPanel cardRuntime;
    private JPanel cardCommandBuilder;
    private JPanel cardCJARManager;
    private JPanel cardConsole;
    private JPanel cardThreading;
    private JPanel cardHooks;
    private JPanel panelSide;
    private JTabbedPane tabberCJAREditor;
    private JLabel btnCreateCJAR;

    /**
     * Layout manager for the card panel.
     */
    CardLayout cardLayout = (CardLayout) (panelCard.getLayout());

    /**
     * Stores if the engine is already attached to another client.
     * else the toolbox is running standalone.
     */
    public static boolean isAttached = false;

    public OPEXVisual() {
        /*
            Form does not add child cards to the card layout, add them.
         */
        panelCard.add(cardEngine, "cardEngine");
        panelCard.add(cardRuntime, "cardRuntime");
        panelCard.add(cardCommandBuilder, "cardCommandBuilder");
        panelCard.add(cardCJARManager, "cardCJARManager");
        panelCard.add(cardConsole, "cardConsole");
        panelCard.add(cardThreading, "cardThreading");
        panelCard.add(cardHooks, "cardHooks");
        cardLayout.show(panelCard, "cardCommandBuilder");

        /*
            Click listeners for each of the buttons
         */
        btnExit.addMouseListener(new MouseAdapter() {                                                                   //Exit button, closes opex visual. if the engine is unused, it's closed too.
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DisplayWindow.dispose();
                if (!isAttached){
                    OPEX.getGameSuper().stop();
                }
            }
        });
        btnRuntime.addMouseListener(new MouseAdapter() {                                                                //Runtime tab selector
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardRuntime", btnRuntime);
                super.mouseClicked(e);
            }
        });

        btnCommand.addMouseListener(new MouseAdapter() {                                                                //Command tab
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardCommandBuilder", btnCommand);
                super.mouseClicked(e);
            }
        });
        btnCJAR.addMouseListener(new MouseAdapter() {                                                                   //CJAR tab
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardCJARManager", btnCJAR);
                super.mouseClicked(e);
            }
        });
        btnConsole.addMouseListener(new MouseAdapter() {                                                                //Console tab
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardConsole", btnConsole);
                super.mouseClicked(e);
            }
        });
        btnEngine.addMouseListener(new MouseAdapter() {                                                                 //Engine tab
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardEngine", btnEngine);
                super.mouseClicked(e);
            }
        });
        btnThreading.addMouseListener(new MouseAdapter() {                                                              //Threading tab
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardThreading", btnThreading);
                super.mouseClicked(e);
            }
        });
        btnHooks.addMouseListener(new MouseAdapter() {                                                                  //Hooks tab
            @Override
            public void mouseClicked(MouseEvent e) {
                switchCards("cardHooks", btnHooks);
                super.mouseClicked(e);
            }
        });
    }


    private JLabel selectedTab = btnCommand;
    private static final Color SELECTED_TAB_COLOR = Color.decode("0x5C6370");                                           //HSL values for the exact same colour behaved strangely, and rendered a completely different colour.
    private static final Color DEFAULT_TAB_COLOR = Color.decode("0x373B43");
    private void switchCards(String name, JLabel sender){
        if (selectedTab != null) {
            selectedTab.setBackground(DEFAULT_TAB_COLOR);
            selectedTab.updateUI();
        }
        cardLayout.show(panelCard, name);
        selectedTab = sender;
        selectedTab.setBackground(SELECTED_TAB_COLOR);
        selectedTab.updateUI();
    }

    public static void main(String[] args)  {
        try {
            isAttached = !OPEX.isRunnable();                                                                            //Get state of the engine, and assume that if it's been started before that the engine is in use by a game.
            if (isAttached) {                                                                                           //If OPEX is in use by a game,
                OPEXVisual detachedGUI = new OPEXVisual();                                                              //Create a private instance,
                detachedGUI.run();                                                                                      //and run it outside of engine.
            } else {
                new OPEX(new OPEXVisual());                                                                             //Otherwise, start the engine; registered to this. toolbox is to run standalone.
            }
        } catch (OPEXStartFailure opexStartFailure) {
            opexStartFailure.printStackTrace();
        }
    }

    @Override
    public String VERSION() {
        return "Development pre-Alpha";
    }

    @Override
    public void stop() {
        OPEXEnvironmentUtils.shutdown(ShutdownCauses.ENGINE_SHUTDOWN_REQUEST);
    }

    @Override
    public void run() {
        DisplayWindow = new JFrame();
        DisplayWindow.setContentPane(panelMain);
        DisplayWindow.setUndecorated(true);
        DisplayWindow.pack();
        DisplayWindow.setVisible(true);
        DisplayWindow.setLocationRelativeTo(null);
    }
}
