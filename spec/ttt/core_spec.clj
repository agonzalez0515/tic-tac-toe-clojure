(ns ttt.core-spec
  (:require [speclj.core :refer [describe context it should= should-invoke with-stubs stub]]
            [ttt.core :as game]
            [ttt.game-rules :as rules]
            [ttt.ui :as ui]))

(def current-state {:board [0 1 2 3 4 5 6 7 8 ] 
                    :players [{:marker "X"} {:marker "O"}] 
                    :current-player {:marker "X"}})

(def last-state {:board ["X" "X" "X" 3 4 5 6 7 8]
                 :players [{:marker "X"} {:marker "O"}]
                 :current-player {:marker "X"}})

(describe "Game"
  (with-stubs)
          
  (context "#set-game-state"
    (it "sets a new game state"
      (should= {:board ["X" 1 2 3 4 5 6 7 8], :players [{:marker "O"} {:marker "X"}] :current-player {:marker "O"}}
        (game/set-game-state current-state ["X" 1 2 3 4 5 6 7 8] [{:marker "O"} {:marker "X"}]))))
  
  (context "#play"
    (it "displays game over message when the game is over"
      (should-invoke ui/print-game-over-message {:with []} (game/play2 ["X" "X" "X" 3 4 5 6 7 8])))))



