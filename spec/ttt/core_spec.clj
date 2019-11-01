(ns ttt.core-spec
  (:require [speclj.core :refer [describe context it should= should-invoke stub with-stubs]]
            [ttt.core :as game]
            [ttt.game-rules :as rules]
            [ttt.ui :as ui]
            [ttt.board :as board]
            [ttt.player :as player]))

(def current-state {:board [0 1 2 3 4 5 6 7 8 ] 
                    :players [{:marker "X"} {:marker "O"}] 
                    :current-player {:marker "X"}})

(describe "Game"         
  (context "#set-game-state"
    (it "sets a new game state"
      (should= {:board ["X" 1 2 3 4 5 6 7 8], :players [{:marker "O"} {:marker "X"}] :current-player {:marker "O"}}
        (game/set-game-state current-state ["X" 1 2 3 4 5 6 7 8] [{:marker "O"} {:marker "X"}]))))
  
  (context "#play"
    (with-stubs)
    
    ; (it "calls game over message when the game is over"
    ;   (with-redefs [rules/game-over? (stub :rules/game-over? {:return true})]
        
    ;   (should-invoke ui/print-game-over-message {:with []} (game/play2 current-state))))
    
    (it "prints the new board after a move is made"
      (with-redefs [rules/game-over? (let [results (atom [false true])]
                                          (fn [_] (ffirst (swap-vals! results rest))))
                    player/get-move (stub :player/get-move {:return 1})
                    board/make-move (stub :board/make-move {:return [0 "X" 2 3 4 5 6 7 8]})]
        
      (should= "\n0 | X | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n" (with-out-str (game/play2 current-state)))))
))

