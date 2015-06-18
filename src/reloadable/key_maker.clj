(ns reloadable.key-maker
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]))

(defn start-making-keys [channel]
  (async/go-loop []
    (if-not (nil? channel)
      (do
        (async/>!! channel (.toString (java.util.UUID/randomUUID)))
        (println "New Precomputed Key added to Channel")
        (recur)))))

(defrecord KeyMaker [channels]
  component/Lifecycle
  (start [component]
    (println "Starting Key Maker Process")
    (start-making-keys (:key-chan channels))
    component)
  (stop [component]
    (println "Shutting Down Key Maker Process")
    component))

(defn new-key-maker []
  (map->KeyMaker {}))