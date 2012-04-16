(ns jersey-lookup-service.JerseyLookupServlet
  (:import (jerseyservice.JerseyServiceServlet))
  (:require [jerseyzoo.JerseyZooServletContainer :as Container])
  (:require [clj-zoo.serverSession :as ssession])
  (:require [clj-zoo-service-tracker.core :as tr])
  (:import (javax.ws.rs GET Path Produces))
  (:gen-class :extends jerseyservice.JerseyServiceServlet
              ;; packages keepers env app region service major minor micro url
              :constructors {[String String String String String String
                              String String String String]
                             [String String String String String String
                              String String String String]}
              :state state
              :init init-state
              :methods [#^{:static true} [ getTracker [String] Object]])
  )

(def getTrckr
  (memoize (fn [keepers]
             (tr/initialize keepers
                            (str "/services/PROD/SI/services/DC1")
                            (str "/clientregistrations/PROD/SI")
                            (str "/services/PROD/SI/servers/DC1"))
		)))

(defn -getTracker
  [keepers]
  (getTrckr keepers))

(defn getTracker
  [keepers]
  (-getTracker keepers))

(defn -init-state
  [packages keepers env app region service major minor micro url]
  [[packages keepers env app region service major minor micro url]
   (ref {:tracker (getTrckr keepers)})])
