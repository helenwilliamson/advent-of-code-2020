(ns advent-of-code-2020.puzzle-11
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-11"))
  ([name]
   (into [] (->> (slurp (str "resources/" name))
                 (str/split-lines)
                 (map #(str/split %1 #""))))))

(defn seats-around
  [y x width height]
  (into #{})(->> (for [a (range (dec x) (+ 2 x))
                       b (range (dec y) (+ 2 y))]
                   [a b])
                 (filter #(and (not (and (= (first %1) x) (= (second %1) y)))
                               (> (first %1) -1)
                               (> (second %1) -1)
                               (< (first %1) width)
                               (< (second %1) height)))))

(defn count-occupied
  [seat-plan [x y]]
  (let [width (count (first seat-plan))
        height (count seat-plan)
        seats-around-here (seats-around x y width height)]
    (->> (map #(get-in seat-plan %1) seats-around-here)
         (filter #(= "#" %1))
         (count))))
