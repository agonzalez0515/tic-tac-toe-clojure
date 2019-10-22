(ns ttt.player-spec
  (:require [speclj.core :refer :all]
            [ttt.player :refer :all]))

(describe "Players"
          (it "creates two players"
              (should= "X" (:marker (first players)))
              (should= "O" (:marker (second players))))
          (it "sets a current player"
              (should= "X" (:marker (current-player players))))
          (it "gets player input"
              (should= 1 (with-in-str "1" (get-move)))))