(ns ttt.board-spec
  (:require [speclj.core :refer :all]
            [ttt.board :refer :all]))

(describe "Board"
          (it "creates a new empty board"
              (should= [0 1 2 3 4 5 6 7 8] (initial-board)))
          (it "prints a board"
              (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n" (with-out-str (print-board (initial-board)))))
          (it "places a move on the board"
              (should= ["X" 1 2 3 4 5 6 7 8] (place-marker [0 1 2 3 4 5 6 7 8] 0 "X")))
          (it "checks if a position is available"
              (should= false (position_available? ["X" 1 2 3 4 5 6 7 8] 0))
              (should= true (position_available? ["X" 1 2 3 4 5 6 7 8] 1)))
          (it "checks if the position is valid"
              (should= true (valid? 2))
              (should= false (valid? 9))
              (should= false (valid? "a")))
          (it "makes a move"
              (should= [0 "X" 2 3 4 5 6 7 8] (make-move [0 1 2 3 4 5 6 7 8] 1 "X"))
              (should= "Invalid move\n" (with-out-str (make-move [0 "X" 2 3 4 5 6 7 8 9] 1 "O")))))
