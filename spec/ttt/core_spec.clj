(ns ttt.core-spec
  (:require [speclj.core :refer [describe context it should= should-invoke stub with-stubs]]
            [picomock.core :as pico]
            [ttt.core :as game]
            [ttt.game-rules :as rules]
            [ttt.ui :as ui]
            [ttt.board :as board]
            [ttt.player :as player]))

(def current-state {:board [0 1 2 3 4 5 6 7 8] 
                    :players [{:marker "X"} {:marker "O"}] 
                    :current-player {:marker "X"}})

(def new-state {:board [0 1 2 3 "X" 5 6 7 8]
                :players [{:marker "O"} {:marker "X"}]
                :current-player {:marker "O"}})

(def state-after-two-moves {:board [0 1 2 3 "X" 5 6 7 "O"]
                            :players [{:marker "X"} {:marker "O"}]
                            :current-player {:marker "X"}})

(def players [{:marker "O"} {:marker "X"}])


(describe "Game"        
  ; (context "#set-game-state"
    ; (it "sets a new game state"
    ;   (should= {:board ["X" 1 2 3 4 5 6 7 8], :players players :current-player {:marker "O"}}
    ;     (game/set-game-state current-state ["X" 1 2 3 4 5 6 7 8] players))))
  
  (context "#play"
    (with-stubs)
    
    (it "calls game over message when the game is over"
      (with-redefs [rules/game-over? (stub :rules/game-over? {:return true})]
        
      (should-invoke ui/print-game-over-message {:with []} (game/play current-state))))     
    (it "makes a move when the game is not over"
      (with-redefs [player/get-move (stub :player/get-move {:return 4})
                    game/set-game-state (stub :game/set-game-state {:return new-state})
                    rules/game-over? (let [results (atom [false true])]
                                        (fn [_] (ffirst (swap-vals! results rest))))]

      (should-invoke board/make-move {:with [[0 1 2 3 4 5 6 7 8] 4 "X"]} (game/play current-state))))
    (it "prints the new board after a move is made"
      (with-redefs [player/get-move (stub :player/get-move {:return 1})
                    board/make-move (stub :board/make-move {:return [0 "X" 2 3 4 5 6 7 8]})
                    rules/game-over? (let [results (atom [false true])]
                                        (fn [_] (ffirst (swap-vals! results rest))))]
        
      (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n\n0 | X | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\nGame over\n" 
               (with-out-str (game/play current-state)))))
    
    (it "prints new boards after each player makes a move"
        (with-redefs [player/get-move (let [results (atom [4 8])]
                                        (fn [] (ffirst (swap-vals! results rest))))
                      
                      board/make-move (let [results (atom [[0 1 2 3 "X" 5 6 7 8] [0 1 2 3 "X" 5 6 7 "O"]])]
                                        (fn [_a _b _c] (ffirst (swap-vals! results rest))))
                      
                      ; game/set-game-state (let [results (atom [new-state state-after-two-moves])]
                      ;                       (fn [_] (ffirst (swap-vals! results rest))))
                      
                      rules/game-over? (let [results (atom [false false true])]
                                         (fn [_] (ffirst (swap-vals! results rest))))]
        

          (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n\n0 | 1 | 2\n---------\n3 | X | 5\n---------\n6 | 7 | 8\n\n0 | 1 | 2\n---------\n3 | X | 5\n---------\n6 | 7 | O\nGame over\n"
                   (with-out-str (game/play current-state)))))))

