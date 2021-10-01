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
  [y x height width]
  (into #{})(->> (for [a (range (dec x) (+ 2 x))
                       b (range (dec y) (+ 2 y))]
                   [b a])
                 (filter #(and (not (and (= (first %1) y) (= (second %1) x)))
                               (> (first %1) -1)
                               (> (second %1) -1)
                               (< (first %1) width)
                               (< (second %1) height)))))

(defn all-seats
  [width height]
  (for [x (range width)
        y (range height)]
    [y x]))

(def occupied-seat "#")
(def empty-seat "L")

(defn count-occupied
  [seat-plan [y x]]
  (let [width (count (first seat-plan))
        height (count seat-plan)
        seats-around-here (seats-around y x height width)]
    (->> (map #(get-in seat-plan %1) seats-around-here)
         (filter #(= occupied %1))
         (count))))

(defn apply-rules
  [seat-plan seat-location]
  (let [seat (get-in seat-plan seat-location)
        number-occupied (count-occupied seat-plan seat-location)]
    (cond
       (and (= empty-seat seat) (= 0 number-occupied)) occupied-seat
       (and (= occupied-seat seat) (>= number-occupied 4)) empty-seat
       :else seat)))

(defn seat-people
  [seat-plan]
  (let [width (count (first seat-plan))
        height (count seat-plan)
        seats (all-seats width height)]
    (loop [plan seat-plan]
      (let [updated-plan (reduce (fn [current-plan seat-location]
                                   (let [seat (get-in plan seat-location)
                                         new-seat (apply-rules plan seat-location)]
                                     (if (not= new-seat seat)
                                       (assoc-in current-plan seat-location new-seat)
                                       current-plan)))
                                 plan
                                 seats)]
        (if (= plan updated-plan)
          plan
          (recur updated-plan))))))

(defn version-1
  ([] (version-1 "puzzle-11"))
  ([name]
   (->> (read-input name)
        (seat-people)
        (flatten)
        (filter #(= %1 occupied))
        (count))))
