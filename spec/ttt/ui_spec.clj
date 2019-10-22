(ns ttt.ui-spec
  (:require [speclj.core :refer :all]
            [ttt.ui :refer :all]))


(describe "UI"
          (it "prints an intro message"
              (should= "Let's play tic-tac-toe.\nPlayer 1 is X\nEnter a number between 0-8 to select a spot.\n" (with-out-str (start-game-message [{:marker "X"} {:marker "O"}]))))
          (it "prints a message letting users know which player is marker X"
              (should= "Player 1 is X\n" (with-out-str (first-player-message [{:marker "X"} {:marker "O"}])))))

