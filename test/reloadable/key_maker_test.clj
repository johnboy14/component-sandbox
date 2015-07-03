(ns reloadable.key-maker-test
  (:use midje.sweet)
  (:require [reloadable.key-maker :as key-maker]
            [reloadable.Key-Channels :as channel-maker]
            [clojure.core.async :as async]
            [com.stuartsierra.component :as component]))

(fact "When starting the Key_Channels component, then it should contain a channel called :key-chan"
      )