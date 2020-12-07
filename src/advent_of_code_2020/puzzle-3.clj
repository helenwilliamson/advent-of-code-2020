(ns advent-of-code-2020.puzzle-3
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  []
  (->> (slurp "resources/puzzle-3")
       (str/split-lines)
       (map cycle)))

(defn navigate-simple-slope
  [slope]
  (let [indexes (iterate (partial + 3) 0)]
    (->> (interleave indexes slope)
         (partition 2)
         (map (fn [[i row]] (nth row i)))
         (filter (partial = \#))
         count)))
