(ns jersey-lookup-service.LookupService
  (:require [zookeeper :as zk] [clojure.tools.logging :as log]
            [clojure.string])
  (:use [clj-zoo-service-tracker.core :as tracker])
  (:use [clj-zoo.serverSession])
  (:use [clojure.data.json :only [json-str]])
  (:use [jerseyzoo.JerseyZooServletContainer :only [getZooConnection]])
  (:import (javax.ws.rs GET POST PUT DELETE FormParam PathParam Path Produces)
           (jerseyzoo JerseyZooServletContainer))
  (:gen-class))

(definterface LookerUpper
  (^String lookup [^String service ^String major ^String minor]))

(deftype ^{Path "/lookup/{service}/{major}/{minor}"} LookerUpperImpl []
         LookerUpper
         (^{GET true
            Produces ["application/json"]}
          ^String lookup [this
                          ^{PathParam "service"} ^String service
                          ^{PathParam "major"} ^String major
                          ^{PathParam "minor"} ^String minor
                          ]
          (use 'clojure.data.json)
          (use 'jerseyzoo.JerseyZooServletContainer)
          (let [t (getTracker)
		url (tracker/lookup t service major minor)]
            (json-str url))))

