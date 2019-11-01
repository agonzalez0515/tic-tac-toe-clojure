(ns ttt.ui-spec
  (:require [speclj.core :refer [context describe it should=]]
            [ttt.ui :as ui]))


(describe "UI"
  (context "it prints messages"
    (it "prints a message when the game starts"
      (should= "Let's play tic-tac-toe.\nPlayer 1 is X\nPlayer 2 is O\nEnter a number between 0-8 to select a spot.\n" 
        (with-out-str (ui/print-start-game-message [{:marker "X"} {:marker "O"}]))))
    (it "prints a board"
      (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n" 
        (with-out-str (ui/print-board [0 1 2 3 4 5 6 7 8]))))
    (it "prints an invalid move message"
      (should= "Invalid move. Please try again.\n" (with-out-str (ui/print-invalid-move-message)))))
          
  (context "it prompts the players for input"
    (it "prompts the player for their name"
      (should= "Player X please enter your username\n" (with-out-str (ui/print-player-username-question {:marker "X"})))))

  (context "it gets player's input"        
    (it "gets player's username"
      (should= "Angie\n" (with-in-str "Angie" (ui/get-player-username))))))

