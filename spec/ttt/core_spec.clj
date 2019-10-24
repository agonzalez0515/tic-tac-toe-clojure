(ns ttt.core-spec
  (:require [speclj.core :refer :all]
            [ttt.core :refer :all]))



(describe "Game"
  (context "#set-game-state"
    (it "has a game state"
      (should= {:board [0 1 2 3 4 5 6 7 8], :players [{:marker "X"} {:marker "O"}] :current-player {:marker "X"}}
        (set-game-state [0 1 2 3 4 5 6 7 8] [{:marker "X"} {:marker "O"}])))))



