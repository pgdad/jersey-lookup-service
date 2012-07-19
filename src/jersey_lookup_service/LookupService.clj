(ns jersey-lookup-service.LookupService
  (:require [clojure.tools.logging :as log]
            [clojure.string])
  (:use [clj-zoo-service-tracker.core :as tracker])
  (:use [clj-zoo.serverSession])
  (:use [clojure.data.json :only [json-str]])
  (:use [jersey-lookup-service.JerseyLookupServlet :only [getTracker]])
  (:import (javax.ws.rs GET POST PUT DELETE FormParam PathParam Path Produces)
           (jerseyzoo JerseyZooServletContainer))
  (:gen-class))

(definterface LookerUpper
  (^String lookup [^String preferRegion ^String service ^int major ^int minor]))

(definterface HostLookerUpper
  (^String lookup [^Strig preferRegion ^String host ^String service ^int major ^int minor]))

(deftype ^{Path "/lookup/{preferRegion}/{service}/{major}/{minor}"} LookerUpperImpl []
         LookerUpper
         (^{GET true
            Produces ["application/json"]}
          ^String lookup [this
                          ^{PathParam "preferRegion"} ^String preferRegion
                          ^{PathParam "service"} ^String service
                          ^{PathParam "major"} ^int major
                          ^{PathParam "minor"} ^int minor
                          ]
          (use 'clojure.data.json)
          (use 'jersey-lookup-service.JerseyLookupServlet)
          (use 'clj-zoo-service-tracker.core)
          (let [t (getTracker "localhost" preferRegion)
		url (clj-zoo-service-tracker.core/lookup-service
			t service major minor "")]
            (json-str url))))


(deftype ^{Path "/lookup/{preferRegion}/{host}/{service}/{major}/{minor}"} HostLookerUpperImpl []
         HostLookerUpper
         (^{GET true
            Produces ["application/json"]}
          ^String lookup [this
                          ^{PathParam "preferRegion"} ^String preferRegion
                          ^{PathParam "host"} ^String host
                          ^{PathParam "service"} ^String service
                          ^{PathParam "major"} ^int major
                          ^{PathParam "minor"} ^int minor
                          ]
          (use 'clojure.data.json)
          (use 'jersey-lookup-service.JerseyLookupServlet)
          (use 'clj-zoo-service-tracker.core)
          (let [t (getTracker "localhost" preferRegion)
		url (clj-zoo-service-tracker.core/lookup-service
			t service major minor "" :host host)]
            (json-str url))))
