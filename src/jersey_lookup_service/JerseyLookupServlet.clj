(ns jersey-lookup-service.JerseyLookupServlet
  (:import (jerseyservice.JerseyServiceServlet))
  (:require [jerseyzoo.JerseyZooServletContainer :as Container])
  (:require [clj-zoo-service-tracker.core :as tr])
  (:import (javax.ws.rs GET Path Produces))
  (:gen-class :extends jerseyservice.JerseyServiceServlet
              ;; packages keepers env app region service major minor micro url
              :constructors {[String String String String
                              String String String String]
                             [String String String String
                              String String String String]}
              :state state
              :init init-state
              :methods [#^{:static true} [ getTracker [String String] Object]])
  )

(def getTrckr
  (memoize (fn [keepers region]
             (tr/initialize keepers region))))

(defn -getTracker
  [keepers region]
  (getTrckr keepers region))

(defn getTracker
  [keepers reguib]
  (-getTracker keepers region))

(defn -init-state
  [packages keepers region service major minor micro url]
  [[packages keepers region service major minor micro url]
   (ref {:tracker (getTrckr keepers region)})])
