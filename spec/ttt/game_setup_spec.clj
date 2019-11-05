(ns ttt.game-setup-spec
  (:require [speclj.core :refer [context describe it should=]]
            [ttt.game-setup :as setup]
            [ttt.ui :as ui]))

(describe "Game Setup"
  (it "creates a list of players"
      (with-redefs [ui/get-player-username (let [results (atom ["Angie" "Jon"])]
                                             (fn [_] (ffirst (swap-vals! results rest))))]
    (should= ["Angie" "Jon"] (setup/get-players [{:marker "X"} {:marker "O"}]))))
  
  (it ))