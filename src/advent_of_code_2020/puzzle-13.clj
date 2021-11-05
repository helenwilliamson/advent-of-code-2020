(ns advent-of-code-2020.puzzle-13
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-13"))
  ([name]
   (->> (slurp (str "resources/" name))
        (str/split-lines)
        ((fn [lines] (hash-map :earliest-departure (-> (first lines)
                                                       Integer/parseInt)
                               :buses (-> (second lines)
                                          (str/split #","))))))))

(defn find-bus-nearest
  [earliest-departure bus]
  (->> (iterate (partial + bus) 0)
       (drop-while (partial > earliest-departure))
       first))

(defn earliest-bus
  [{:keys [buses earliest-departure]}]
  (->> buses
       (filter #(not= "x" %1))
       (map #(Integer/parseInt %1))
       (map #(hash-map :bus %1 :earliest-bus (find-bus-nearest earliest-departure %1)))
       (sort-by :earliest-bus)
       first
       ((fn [{:keys [earliest-bus bus]}] (* bus (- earliest-bus earliest-departure))))))

(defn bus-near
  [value [bus-id bus-timetable]]
  (let [next-bus (first (drop-while (partial > (+ value bus-id)) bus-timetable))]
    (= (+ value bus-id) next-bus)))

(defn find-adjacent-times
  [[start & buses]]
  (loop [[current & remaining] (second start)]
    (if (or (> current 1068789) (every? #(bus-near current %1) buses))
      current
      (recur remaining))))

(defn subsequent-minute
  [{buses :buses}]
  (->> (zipmap (iterate inc 0) buses)
       (filter #(not= "x" (second %1)))
       (map #(list (first %1) (iterate (partial + (Integer/parseInt (second %1))) 0)))
       find-adjacent-times))

(defn version-1
  ([] (version-1 "puzzle-13"))
  ([name]
   (->> (read-input name)
        (earliest-bus))))
