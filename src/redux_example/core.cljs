(ns redux-example.core
  (:require [reagent.core :as r]
            [redux-example.store :as store]))

(enable-console-print!)

(defn app [{:keys [incremento bicremento decremento]} dispatch]
  [:div.app
   [:div "I only increase:" incremento]
   [:div "I only decrease:" decremento]
   [:div "I go both ways:" bicremento]
   [:div.buttons
    [:button {:on-click #(dispatch [:counter/increment])} "( + )"]
    [:button {:on-click #(dispatch [:counter/decrement])} "( - )"]]])

(defn app-wrapper [store x]
  (let [{:keys [dispatch get-state]} store]
    (fn []
      [app (get-state) dispatch])))

(r/render-component [app-wrapper store/store 3]
  (.getElementById js/document "app"))
