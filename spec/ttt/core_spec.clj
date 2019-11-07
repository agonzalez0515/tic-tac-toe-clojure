(ns ttt.core-spec
  (:require [speclj.core :refer [describe context it should-invoke stub with-stubs should-contain]]
            [ttt.core :as game]
            [ttt.game-rules :as rules]
            [ttt.ui :as ui]
            [ttt.board :as board]
            [ttt.player :as player]
            [database :as db]))

(def players [{:marker "O"} {:marker "X"}])

(def initial-state {:board [0 1 2 3 4 5 6 7 8] 
                    :players [{:username "Angie", :marker "X"} {:username "Jon", :marker "O"}] })

(def state-after-one-move {:board [0 1 2 3 "X" 5 6 7 8]
                           :players [{:username "Jon", :marker "O"} {:username "Angie", :marker "X"}]
                           :current-player {:username "Jon" :marker "O"}})

(def state-after-two-moves {:board [0 1 2 3 "X" 5 6 7 "O"]
                            :players [{:username "Angie", :marker "X"} {:username "Jon", :marker "O"}]})

(def boards-printed-after-one-move "\n0 | X | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n")
(def boards-printed-after-two-moves "\n0 | 1 | 2\n---------\n3 | X | 5\n---------\n6 | 7 | O\n")

(def players-stats [{:stats_id 9 :username "Angie" :wins 0 :ties 0 :losses 0} {:stats_id 2 :username "Jon" :wins 0 :ties 0 :losses 0}])

(describe "Game"
  (context "#play"
    (with-stubs)

    (it "calls win message when the game is over"
        (with-redefs [rules/win? (stub :rules/win? {:return true})]
          (with-redefs [db/update-stats-when-winner (stub :db/update-stats-when-winner {:return 1})
                        db/retrieve-all-player-stats (stub :db/retrieve-all-player-stats {:return players-stats})]
          (should-invoke ui/print-win-message {:with ["Jon"]} (game/play initial-state)))))

    (it "makes a move when the game is not over"
        (with-redefs [player/get-move (stub :player/get-move {:return 4})
                      game/set-game-state (stub :game/set-game-state {:return state-after-one-move})
                      db/update-stats-when-winner (stub :db/update-stats-when-winner {:return 1})
                      db/retrieve-all-player-stats (stub :db/retrieve-all-player-stats {:return players-stats})
                      rules/win? (let [results (atom [false true])]
                                         (fn [_] (ffirst (swap-vals! results rest))))
                      rules/tie? (let [results (atom [false true])]
                                         (fn [_] (ffirst (swap-vals! results rest))))]
          (should-invoke board/make-move {:with [[0 1 2 3 4 5 6 7 8] 4 "X"]} (game/play initial-state))))

    (it "prints the new board after a move is made"
        (with-redefs [player/get-move (stub :player/get-move {:return 1})
                      board/make-move (stub :board/make-move {:return [0 "X" 2 3 4 5 6 7 8]})
                      db/update-stats-when-winner (stub :db/update-stats-when-winner {:return 1})
                      db/retrieve-all-player-stats (stub :db/retrieve-all-player-stats {:return players-stats})
                      rules/win? (let [results (atom [false true])]
                                   (fn [_] (ffirst (swap-vals! results rest))))
                      rules/tie? (let [results (atom [false false])]
                                   (fn [_] (ffirst (swap-vals! results rest))))]
          (should-contain boards-printed-after-one-move (with-out-str (game/play initial-state)))))

    (it "prints new boards after each player makes a move"
        (with-redefs [player/get-move (let [results (atom [4 8])]
                                        (fn [] (ffirst (swap-vals! results rest))))
                      board/make-move (let [results (atom [[0 1 2 3 "X" 5 6 7 8] [0 1 2 3 "X" 5 6 7 "O"]])]
                                        (fn [_a _b _c] (ffirst (swap-vals! results rest))))
                      db/update-stats-when-winner (stub :db/update-stats-when-winner {:return 1})
                      db/retrieve-all-player-stats (stub :db/retrieve-all-player-stats {:return players-stats})
                      rules/win? (let [results (atom [false false true])]
                                    (fn [_] (ffirst (swap-vals! results rest))))
                        rules/tie? (let [results (atom [false false true])]
                                    (fn [_] (ffirst (swap-vals! results rest))))]
          (should-contain boards-printed-after-two-moves (with-out-str (game/play initial-state)))))))
