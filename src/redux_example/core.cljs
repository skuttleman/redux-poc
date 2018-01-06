(ns redux-example.core
  (:require [reagent.core :as r]
            [redux-example.store :as store]
            [redux-example.nested-store :as nested-store]))

(enable-console-print!)

(defn app [{:keys [incremento bicremento decremento]} dispatch]
  [:div.app
   [:div "I only increase: " incremento]
   [:div "I go both ways: " bicremento]
   [:div "I only decrease: " decremento]
   [:div.buttons
    [:button {:on-click #(dispatch [:counter/increment])} "( + )"]
    [:button {:on-click #(dispatch [:counter/decrement])} "( - )"]]])

(defn app2 [state dispatch]
  (set! (.-dispatch js/window) (comp dispatch #(js->clj % :keywordize-keys true)))
  (fn [{:keys [incidents]} dispatch]
    [:div
     [:pre (pr-str incidents)]]))

(defn app-wrapper [component store]
  (let [{:keys [dispatch get-state]} store]
    (fn []
      [component (get-state) dispatch])))

(r/render-component [app-wrapper app store/store]
  (.getElementById js/document "app"))

(r/render-component [app-wrapper app2 nested-store/nested-store]
  (.getElementById js/document "app2"))
