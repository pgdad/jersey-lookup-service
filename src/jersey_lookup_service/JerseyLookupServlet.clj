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
              :methods [#^{:static true} [ getTracker [] Object]])
  )

(def getTrckr
  (memoize (fn [keepers route-root client-reg-root instance-root]
             (tr/initialize keepers route-root client-reg-root instance-root))))

(defn -getTracker
  []
  (getTrckr keepers route-root client-reg-root instance-root))

(defn -init-state
  [packages keepers env app region service major minor micro url]
  [[packages keepers env app region service major minor micro url]
   (ref {:tracker (getTrckr keepers
                            (str "/services/" env "/" app "/services/" region)
                            (str "/clientregistrations/" env "/" app)
                            (str "/services" env "/" app "/servers/" region))
         })])
