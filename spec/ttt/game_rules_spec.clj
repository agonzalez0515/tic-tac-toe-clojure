(ns ttt.game-rules-spec
  (:require [speclj.core :refer :all]
            [ttt.game-rules :refer :all]))


(describe "Game Rules"
  (it "returns true if there is a winner"
      (should= true (win? ["X" "X" "X" 3 4 5 6 7 8]))))