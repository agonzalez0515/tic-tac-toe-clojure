(ns ttt.game-rules-spec
  (:require [speclj.core :refer [describe context it should=]]
            [ttt.game-rules :as rules]))


(describe "Game Rules"
          
  (context "create-rows"
    (it "returns a collection of rows"
      (should= [[0 1][2 3]] (rules/create-rows [0 1 2 3]))))
  (context "#win?"
    (it "returns false if there is no winner"
      (should= false (rules/win? ["X" "X"  "O"
                                  "O" "O"  "X"
                                   6   7   "X"])))
    (it "checks if the game is won for row 1"
      (should= true (rules/win? ["X" "X" "X"
                                 "O"  4  "O"
                                  5   6   7])))
    (it "checks if the game is won for row 2"
      (should= true (rules/win? ["X"  1  "X"
                                 "O" "O" "O"
                                  5   6   7])))
    (it "checks if the game is won for row 3"
      (should= true (rules/win? ["X"  1  "O"
                                 "O" "X" "O"
                                 "X" "X" "X"])))
    (it "checks if the game is won for column 1"
      (should= true (rules/win? ["X" "O" "O"
                                 "X"  4  "O"
                                 "X"  6   7])))
    (it "checks if the game is won for a column 2"
      (should= true (rules/win? ["X" "O" "X"
                                 "X" "O" "O"
                                  5  "O"  7])))
    (it "checks if the game is won for a column 3"
      (should= true (rules/win? ["X" "O" "O"
                                 "X" "X" "O"
                                  5  "O" "O"])))
    (it "checks if the game is won for diagonal 1"
      (should= true (rules/win? ["O" "X" "X"
                                 "O" "X" "O"
                                 "X"  6   7])))
    (it "checks if the game is won for diagonal 2"
      (should= true (rules/win? ["O" "X" "X"
                                  3  "O" "O"
                                 "X"  6  "O"]))))
  (context "#tie?"
    (it "returns true if the game is tied"
      (should= true (rules/tie? ["O" "X" "O"
                                 "O" "X" "X"
                                 "X" "O" "X"])))
    (it "returns false if the game is tied and there is a winner"
      (should= false (rules/tie? ["O" "X" "O"
                                 "X" "X" "X"
                                 "X" "O" "X"])))
    (it "returns false if the game is not tied"
      (should= false (rules/tie? ["O"  1  "O"
                                  "O" "X" "X"
                                  "X" "O" "X"]))))
  (context "#game-over?" 
    (it "returns true if the game is over because of a win"
      (should= true (rules/game-over? ["O" "X" "X"
                                        3  "O" "O"
                                       "X"  6  "O"])))
    (it "returns true if the game is over because there is a tie"
        (should= true (rules/game-over? ["O" "X" "O"
                                         "O" "X" "X"
                                         "X" "O" "X"])))
    (it "returns false if the game is not over"
      (should= false (rules/game-over? ["O"  1  "O"
                                        "O" "X" "X"
                                        "X" "O" "X"])))))
