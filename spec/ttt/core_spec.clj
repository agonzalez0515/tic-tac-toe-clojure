(ns ttt.core-spec
  (:require [speclj.core :refer [describe context it should=]]
            [ttt.core :as game]))

(def current-state {:board [0 1 2 3 4 5 6 7 8 ] 
                    :players [{:marker "X"} {:marker "O"}] 
                    :current-player {:marker "X"}})

(describe "Game"
  (context "#set-game-state"
    (it "sets a new game state"
      (should= {:board ["X" 1 2 3 4 5 6 7 8], :players [{:marker "X"} {:marker "O"}] :current-player {:marker "X"}}
        (game/set-game-state current-state ["X" 1 2 3 4 5 6 7 8] [{:marker "X"} {:marker "O"}])))))



