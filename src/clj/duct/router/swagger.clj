(ns duct.router.swagger
  (:require [integrant.core :as ig]
            [reitit.swagger :refer [create-swagger-handler]]
            [reitit.swagger-ui :refer [create-swagger-ui-handler]]))


(defn- serve-if-enabled
  [handler req enabled?]
  (if enabled?
    (handler req)
    {:status 404}))


(defmethod ig/init-key ::json [_ {:keys [enabled?]}]
  (fn [req]
    (serve-if-enabled (create-swagger-handler) req enabled?)))


(defmethod ig/init-key ::ui [_ {:keys [enabled?]}]
  (fn [req]
    (serve-if-enabled (create-swagger-ui-handler {:url "/swagger/swagger.json"}) req enabled?)))
