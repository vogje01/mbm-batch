import React from 'react';

import MainComponent from "./components/MainComponent.js";
import LoginView from "./views/login/LoginView";
import TopToolbar from "./components/TopToolbar";
import {Route, Router} from "react-router-dom";
import history from "./components/History";

function App() {
    return (
        <div className="App">
            <TopToolbar/>
            <Router history={history}>
                <Route exact path="/" component={LoginView}/>
                <Route exact path="/jobexecutions" component={MainComponent}/>
                <Route exact path="/stepexecutions" component={MainComponent}/>
                <Route exact path="/jobschedules" component={MainComponent}/>
                <Route exact path="/settings" component={MainComponent}/>
                <Route exact path="/performance" component={MainComponent}/>
            </Router>
        </div>
  );
}

export default App;
