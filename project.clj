(defproject jersey-lookup-service "1.0.0"
  :description "FIXME: write description"
  :aot [jersey-lookup-service.JerseyLookupServlet jersey-lookup-service.core
        jersey-lookup-service.LookupService]
  :main jersey-lookup-service.core
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [clj-zoo-service-tracker "1.0.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.clojure/data.json "0.1.3"]
                 [log4j/log4j "1.2.16"]
                 [jersey-service/jersey-service "1.0.2"]
                 [jersey-zoo-clj/jersey-zoo-clj "1.0.1"]]
  :dev-dependencies [[org.eclipse.jetty/jetty-servlet "8.1.2.v20120308"]])
