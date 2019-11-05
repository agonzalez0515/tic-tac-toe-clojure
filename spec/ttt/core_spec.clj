(ns ttt.core-spec
  (:require [speclj.core :refer [describe context it should= should-invoke stub with-stubs]]
            [ttt.core :as game]
            [ttt.game-rules :as rules]
            [ttt.ui :as ui]
            [ttt.board :as board]
            [ttt.player :as player]))

(def players [{:marker "O"} {:marker "X"}])

(def initial-state {:board [0 1 2 3 4 5 6 7 8] 
                    :players [{:marker "X"} {:marker "O"}] 
                    :current-player {:marker "X"}})

(def state-after-one-move {:board [0 1 2 3 "X" 5 6 7 8]
                           :players [{:marker "O"} {:marker "X"}]
                           :current-player {:marker "O"}})

(def state-after-two-moves {:board [0 1 2 3 "X" 5 6 7 "O"]
                            :players [{:marker "X"} {:marker "O"}]
                            :current-player {:marker "X"}})

(def boards-printed-after-one-move "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n\n0 | X | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\nGame over\n")
(def boards-printed-after-two-moves "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n\n0 | 1 | 2\n---------\n3 | X | 5\n---------\n6 | 7 | 8\n\n0 | 1 | 2\n---------\n3 | X | 5\n---------\n6 | 7 | O\nGame over\n")


(describe "Game"
  (context "#play"
    (with-stubs)

    (it "calls game over message when the game is over"
        (with-redefs [rules/game-over? (stub :rules/game-over? {:return true})]
          (should-invoke ui/print-game-over-message {:with []} (game/play initial-state))))

    (it "makes a move when the game is not over"
        (with-redefs [player/get-move (stub :player/get-move {:return 4})
                      game/set-game-state (stub :game/set-game-state {:return state-after-one-move})
                      rules/game-over? (let [results (atom [false true])]
                                         (fn [_] (ffirst (swap-vals! results rest))))]
          (should-invoke board/make-move {:with [[0 1 2 3 4 5 6 7 8] 4 "X"]} (game/play initial-state))))

    (it "prints the new board after a move is made"
        (with-redefs [player/get-move (stub :player/get-move {:return 1})
                      board/make-move (stub :board/make-move {:return [0 "X" 2 3 4 5 6 7 8]})
                      rules/game-over? (let [results (atom [false true])]
                                         (fn [_] (ffirst (swap-vals! results rest))))]
          (should= boards-printed-after-one-move (with-out-str (game/play initial-state)))))

    (it "prints new boards after each player makes a move"
        (with-redefs [player/get-move (let [results (atom [4 8])]
                                        (fn [] (ffirst (swap-vals! results rest))))
                      board/make-move (let [results (atom [[0 1 2 3 "X" 5 6 7 8] [0 1 2 3 "X" 5 6 7 "O"]])]
                                        (fn [_a _b _c] (ffirst (swap-vals! results rest))))
                      rules/game-over? (let [results (atom [false false true])]
                                         (fn [_] (ffirst (swap-vals! results rest))))]
          (should= boards-printed-after-two-moves (with-out-str (game/play initial-state)))))))
