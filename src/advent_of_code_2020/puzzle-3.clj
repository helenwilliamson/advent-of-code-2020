(ns advent-of-code-2020.puzzle-3
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  []
  (->> (slurp "resources/puzzle-3")
       (str/split-lines)
       (map cycle)))

(defn navigate-slope
  [slope right down]
  (let [data (take-nth down slope)
        indexes (iterate (partial + right ) 0)]
    (->> (interleave indexes slope)
         (partition 2)
         (map (fn [[i row]] (nth row i)))
         (filter (partial = \#))
         count)))

(defn navigate-simple
  [slope]
  (navigate-slope slope 3 1))

(defn navigate-complicated
  [slope]
  (->> [[1 1] [3 1] [5 1] [7 1] [1 2]]
         (map #(apply (partial navigate-slope slope) %))))
