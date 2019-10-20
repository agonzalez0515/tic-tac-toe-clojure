(ns ttt.core-spec
  (:require [speclj.core :refer :all]
            [ttt.core :refer :all]))

(describe "UI"
          (it "prints an intro message"
              (should= "Let's play tic-tac-toe.\nEnter a number between 0-8 to select a spot.\n" (with-out-str (start-game-message))))
          (it "prints a message letting users know which player is marker X"
              (should= "Player 1 is X\n" (with-out-str(first-player-message players)))))


(describe "Board"
          (it "creates a new empty board"
              (should= [0 1 2 3 4 5 6 7 8] (initial-board)))
          (it "prints a board"
              (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n" (with-out-str (print-board (initial-board))))))

(describe "Players"
          (it "creates two players"
              (should= "X" (:marker (first players)))
              (should= "O" (:marker (second players))))
          (it "sets a current player"
              (should= "X" (:marker (current-player players)))))

