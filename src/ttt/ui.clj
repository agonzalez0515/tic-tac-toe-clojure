(ns ttt.ui
    (:require  [ttt.game-rules :as rules]))

(defn- add-padding-to-cells
  [row]
  (clojure.string/join " | " row))


(defn print-player-instructions
  [username]
  (println (str username " enter a number between 0-8 to select a spot.")))

(defn print-start-game-message
  []
  (println "Let's play tic-tac-toe."))


(defn print-board
  [board]
  (let [rows (into [] (map add-padding-to-cells (rules/create-rows board)))
        divider (str "\n" (clojure.string/join (repeat (count (first rows)) "-")) "\n")]
      (println (str "\n" (clojure.string/join divider rows)))))

(defn print-invalid-move-message
  []
  (println "Invalid move. Please try again."))

(defn print-win-message
  [username]
  (println (str username " wins the game")))

(defn print-tie-message
  []
  (println "You tied!"))

(defn- print-player-username-question
  [player-marker]
  (println (str "Player " player-marker"," " please enter your username")))

(defn get-player-username
  [player]
  (print-player-username-question (:marker player))
  (read-line))

(defn print-make-move-with-name
  [name]
  (println (str name"," " please make a move.")))

(defn print-stats
  [stats]
  (let [player1 (first stats)
        player2 (second stats)]
  (println "====PLAYER STATS====")
  (println (str (:username player1) "- wins: " (:wins player1) " losses: " (:losses player1) " ties: " (:ties player1)))
  (println (str (:username player2) "- wins: " (:wins player2) " losses: " (:losses player2) " ties: " (:ties player2)))))