(ns ttt.ui-spec
  (:require [speclj.core :refer [describe it should=]]
            [ttt.ui :as ui]))


(describe "UI"
  (it "prints a message when the game starts"
    (should= "Let's play tic-tac-toe.\nPlayer 1 is X\nPlayer 2 is O\nEnter a number between 0-8 to select a spot.\n" 
      (with-out-str (ui/print-start-game-message [{:marker "X"} {:marker "O"}]))))
  (it "prints a board"
    (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n" 
      (with-out-str (ui/print-board [0 1 2 3 4 5 6 7 8]))))
  (it "prints an invalid move message"
      (should= "Invalid move. Please try again.\n" (with-out-str (ui/print-invalid-move-message)))))

