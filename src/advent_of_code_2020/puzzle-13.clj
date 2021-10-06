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

(defn version-1
  ([] (version-1 "puzzle-13"))
  ([name]
   (->> (read-input name)
        (earliest-bus))))
