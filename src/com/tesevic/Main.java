package com.tesevic;

import com.tesevic.game.Game;
import com.tesevic.interaction.Input;
import com.tesevic.text.TextHolder;

import javax.xml.soap.Text;

import static com.tesevic.Main.STATE.*;

public class Main {
    enum STATE {
        MENU, HOWTOPLAY, PLAYING, EXIT
    }

    // Initialize state
    private STATE gameState = MENU;

    private void mainMenu() {
        System.out.println(TextHolder.MAIN_MENU);

            switch (Input.getInput()) {
                case 1:
                    gameState = PLAYING;
                    break;
                case 2:
                    gameState = HOWTOPLAY;
                    break;
                case 3:
                    gameState = EXIT;
                    break;
                default:
                    System.out.println(TextHolder.INPUT_NOT_IN_RANGE);
            }
    }

    private void howToPlay() {
        System.out.println(TextHolder.HOW_TO_PLAY);

        switch (Input.getInput()) {
            case 1:
                gameState = MENU;
                break;
            default:
                System.out.println(TextHolder.INPUT_NOT_IN_RANGE);
                break;
        }
    }

    private void run() {
        do {
            switch (gameState) {
                case PLAYING:
                    new Game().start();
                    gameState = MENU;
                    break;
                case MENU:
                    mainMenu();
                    break;
                case HOWTOPLAY:
                    howToPlay();
                    break;
                default:
                    System.out.println(TextHolder.INPUT_NOT_IN_RANGE);
            }
        } while (gameState != EXIT);

        System.out.println("Bye bye!");
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
