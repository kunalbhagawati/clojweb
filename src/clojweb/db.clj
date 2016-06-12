(ns clojweb.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname   "org.h2.Driver"
              :subprotocol "h2:file"
              :subname     "db/clojweb"})

(defn add-location-to-db
  [x y]
  (let [results (sql/with-connection db-spec
                                     (sql/insert-record :locations {:x x :y y}))]
    (assert (= (count results) 1))
    (first (vals results))))


(defn get-xy
  [loc-id]
  (let [results (sql/with-connection db-spec
                                     (sql/with-query-results res
                                                             ["select x, y from location where id = ?" loc-id]
                                                             (doall res)))]
    (assert (= (count results) 1))
    (first results)))


(defn get-all-locations
  []
  (let [results (sql/with-connection db-spec
                                     (sql/with-query-results res
                                                             ["select id, x, y from location"]
                                                             (doall res)))]
    results))