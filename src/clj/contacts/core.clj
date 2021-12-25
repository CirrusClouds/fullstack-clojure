(ns contacts.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.parameters :refer [parameters-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                                     format-request-middleware
                                                     format-response-middleware]]
            [reitit.ring.coercion :refer [coerce-exceptions-middleware
                                          coerce-request-middleware
                                          coerce-response-middleware]]
            [reitit.coercion.schema]
            [schema.core :as s]
            [muuntaja.core :as m]
            [contacts.db :as db]
            [clojure.data.json :as json]))


(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    [["/api"
      ["/bookmarks"
       ["/" {:get (fn [_]
                    (let [data (json/write-str (db/get-all-bookmarks db/config))]
                      {:status 200
                       :body data}))
             :post {:parameters {:body {:name s/Str
                                        :url s/Str
                                        :description s/Str}}
                    :handler (fn [body]
                               (let [data (:body (:parameters body))
                                     created-id (db/insert-bookmark db/config data)]
                                 {:status 200
                                  :body (json/write-str (db/get-bookmark-by-id db/config created-id))}))}}]
       
       ["/:id" {:parameters {:path {:id s/Int}}
                :get (fn [body]
                       (let [id (:path (:parameters body))]
                         {:status 201
                          :body (db/get-bookmark-by-id db/config id)}))
                :put {:parameters {:body {:name s/Int
                                          :url s/Str
                                          :description s/Str}}
                      :handler (fn [body]
                                 (let [id (:path (:parameters body))
                                       body (:body (:parameters body))
                                       data (assoc body :id id)]
                                   (db/update-bookmark-by-id db/config data)
                                   {:status 201
                                    :body (db/get-bookmark-by-id db/config id)}))}}]]]]
    
    {:data {:coercion reitit.coercion.schema/coercion
            :muntaja m/instance
            :middleware [[wrap-cors
                          :access-control-allow-origin [#"http://localhost:3000"]
                          :access-control-allow-methods [:get :post :put :delete]]
                         parameters-middleware
                         format-negotiate-middleware
                         format-response-middleware
                         exception-middleware
                         format-request-middleware
                         coerce-exceptions-middleware
                         coerce-request-middleware
                         coerce-response-middleware
                         format-response-middleware]}})
   (ring/routes
    (ring/redirect-trailing-slash-handler)
    (ring/create-default-handler
     {:not-found (constantly {:status 404
                              :body "404 - Page Not Found"})}))))

(defn -main []
  (println "Server initialized")
  (reset! server (run-server app {:port 4000}))
  (db/update-bookmark-by-id db/config {:id 1 :name "wow" :url "example" :description "wow"}))
