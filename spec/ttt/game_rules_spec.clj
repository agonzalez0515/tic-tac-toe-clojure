(ns ttt.game-rules-spec
  (:require [speclj.core :refer :all]
            [ttt.game-rules :refer :all]))


(describe "Game Rules"
  (context "#win?"
    (it "returns false if there is no winner"
      (should= false (win? ["X","X", "O"
                            "O","O", "X"
                             6,  7,  "X"])))
    (it "checks if the game is won for a row of X's"
        (should= true (win? ["X","X","X"
                             "O", 4, "O"
                              5,  6,  7 ])))
    (it "checks if the game is won for a row of O's"
        (should= true (win? ["X", 1, "X"
                             "O","O","O"
                              5,  6,  7])))
     (it "checks if the game is won for a column of X's"
         (should= true (win? ["X","O","O"
                              "X", 4, "O"
                              "X", 6,  7])))
      (it "checks if the game is won for a column of O's"
          (should= true (win? ["X","O","X"
                               "X","O","O"
                                5, "O", 7])))
     (it "checks if the game is won for a diagonal of X's"
         (should= true (win? ["O","X","X"
                              "O","X","O"
                              "X", 6,  7])))
     (it "checks if the game is won for a diagonal of O's"
         (should= true (win? ["O","X","X"
                               3, "O","O"
                              "X", 6, "O"])))))
