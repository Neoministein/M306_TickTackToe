package com.bbb.m306.tictactoe;

import com.bbb.m306.tictactoe.game.GameLogic;
import com.bbb.m306.tictactoe.game.GameLogicImpl;
import com.bbb.m306.tictactoe.game.GameRulesImpl;

public class Singleton {

    private volatile static GameLogic gameLogic = new GameLogicImpl(new GameRulesImpl());

    public static synchronized GameLogic getGameLogic() {
        return gameLogic;
    }
}
