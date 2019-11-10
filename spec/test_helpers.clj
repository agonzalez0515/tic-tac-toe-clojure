(ns test-helpers)

(defn stub-loops
  [values-to-switch]
  (let [results (atom values-to-switch)]
    (fn [& _] (ffirst (swap-vals! results rest)))))