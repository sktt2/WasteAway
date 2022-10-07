import React from "react";
import ReactDOM from "react-dom";
import App from "./App";

/**
 * This is the starting point of the react project just like main class in java projects.
 * here it is inserting the app component in the only Html file of our project in /public/index.html.
 * command + click on the "./App" in line 3 to see the app component.
 */
ReactDOM.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
    document.getElementById("root")
);
