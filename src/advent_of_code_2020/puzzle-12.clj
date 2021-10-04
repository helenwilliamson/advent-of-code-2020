(ns advent-of-code-2020.puzzle-12
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-12"))
  ([name]
   (into [] (->> (slurp (str "resources/" name))
                 (str/split-lines)
                 (map #(hash-map :direction (subs %1 0 1)
                                 :value (Integer/parseInt (subs %1 1))))))))

(defn process-compass-instruction
  [{:keys [north-south east-west] :as position}
   {:keys [direction value]}]
  (case direction
    "N" (assoc position :north-south (+ north-south value))
    "S" (assoc position :north-south (- north-south value))
    "E" (assoc position :east-west (+ east-west value))
    "W" (assoc position :east-west (- east-west value))))

(defn process-change-in-direction
  [direction-list
   {current-direction :current-direction :as position}
   {value :value}]
  (assoc position
          :current-direction
          (->> (cycle direction-list)
               (drop-while #(not= %1 current-direction))
               (take (+ 1 (/ value 90)))
               reverse
               first)))

(def directions ["N" "E" "S" "W"])

(defn process-instruction
  [{:keys [north-south east-west current-direction] :as position}
   {:keys [direction value] :as instruction}]
  (case direction
    ("N", "E" "S" "W") (process-compass-instruction position instruction)
    "F" (process-compass-instruction position {:direction current-direction :value value})
    "L" (process-change-in-direction (reverse directions) position instruction)
    "R" (process-change-in-direction directions position instruction)))

(defn version-1
  ([] (version-1 "puzzle-12"))
  ([name]
   (->> (select-keys
         (->> (read-input name)
              (reduce process-instruction {:north-south 0 :east-west 0 :current-direction "E"}))
         [:north-south :east-west])
        vals
        (map #(Math/abs %1))
        (reduce +))))
