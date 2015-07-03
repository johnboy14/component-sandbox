(ns reloadable.key-maker
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async])
  (:import (java.security KeyPairGenerator SecureRandom)))

(def keyGenerator (KeyPairGenerator/getInstance "RSA" "SunRsaSign"))
(.initialize keyGenerator 2048 (SecureRandom/getInstance "SHA1PRNG" "SUN"))

(defn generate-keys [keyGenerator]
  (let [keypairs (.generateKeyPair keyGenerator)]
    {:public  (.toString (.getPublic keypairs))
     :private (.toString (.getPrivate keypairs))}))

(defn start-making-keys [channel]
  (async/go-loop []
    (if-not (nil? channel)
      (do
        (async/put! channel (generate-keys keyGenerator)
                    (fn [_]
                      (println "New Precomputed Key added to Channel")))
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