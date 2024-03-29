(ns ttt.board-spec
  (:require [speclj.core :refer [describe context it should= should-contain]]
            [ttt.board :as board]))

(describe "Board"
          
  (context "#create-initial-board"
    (it "creates a new empty board"
      (should= [0 1 2 3 4 5 6 7 8] (board/create-initial-board))))
          
  (context "#make-move"
    (it "returns a new board when a valid move is entered"
      (should= [0 "X" 2 3 4 5 6 7 8] (board/make-move [0 1 2 3 4 5 6 7 8] 1 "X")))
    (it "returns a message when an unavailable move is entered"
      (should-contain "Invalid move" (with-out-str (board/make-move [0 "X" 2 3 4 5 6 7 8 9] 1 "O"))))
    (it "returns a message when a non-numerical character is entered"
      (should-contain "Invalid move" (with-out-str(board/make-move [0 "X" 2 3 4 5 6 7 8 9] "a" "X"))))))

