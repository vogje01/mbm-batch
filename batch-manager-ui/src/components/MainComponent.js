import React from "react";
import ReactDOM from 'react-dom'
import {Drawer} from 'devextreme-react';
import {Item, Toolbar} from "devextreme-react/toolbar";
import {Route, Router} from "react-router-dom";

import JobExecutionView from "../views/jobexecutions/JobExecutionView";
import StepExecutionView from "../views/stepexecutions/StepExecutionView";
import JobSchedulerView from "../views/scheduler/JobSchedulerView";
import SettingsView from "../views/settings/SettingsView";
import PerformanceView from "../views/performance/PerformanceView";

import history from "./History";
import {Subject} from "rxjs";

import NavigationList from "./NavigationList.js";
import * as jwt from "jsonwebtoken";
import {logout} from "./ServerConnection";
import UserManagementView from "../views/user/UserManagementView";

export const refreshSubject = new Subject();
export const publish = (topic, data) => refreshSubject.next({topic, data});

const routing = (
    <Router history={history}>
        <div>
            <Route exact path="/jobexecutions" component={JobExecutionView}/>
            <Route exact path="/stepexecutions" component={StepExecutionView}/>
            <Route exact path="/jobschedules" component={JobSchedulerView}/>
            <Route exact path="/settings" component={SettingsView}/>
            <Route exact path="/users" component={UserManagementView}/>
            <Route exact path="/performance" component={PerformanceView}/>
        </div>
    </Router>
);

class MainComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isOpened: false,
            timer: {},
            updateFrequency: 30000
        };
        this.menuButtonOptions = {
            icon: "menu",
            onClick: () => {
                this.setState({isOpened: !this.state.isOpened})
            }
        };
        this.refreshButtonOptions = {
            icon: "refresh",
            onClick: () => {
                publish('Refresh', {})
            }
        };
        this.intervals = [{interval: 0, text: 'None'}, {interval: 30000, text: '30 sec'}, {
            interval: 60000,
            text: '1 min'
        }, {interval: 300000, text: '5 min'}, {interval: 600000, text: '10 min'},
            {interval: 1800000, text: '30 min'}, {interval: 3600000, text: '1 hour'}];
        this.intervalSelectOptions = {
            width: 140,
            items: this.intervals,
            valueExpr: "interval",
            displayExpr: "text",
            placeholder: "Update interval",
            value: this.intervals[0].id,
            onValueChanged: (args) => {
                clearTimeout(this.state.timer);
                if (args.value > 0) {
                    this.state.timer = setInterval(() => {
                        publish('Refresh', {})
                    }, args.value);
                    publish('Refresh', {})
                }
            }
        };
    }

    componentDidMount() {
        ReactDOM.render(routing, document.getElementById('view'));
        setInterval(() => {
            let token = localStorage.getItem('webToken');
            if (token !== undefined && token !== null) {
                let decodedToken = jwt.decode(token);
                if (decodedToken.exp < Math.floor(Date.now() / 1000)) {
                    logout();
                }
            }
        }, 300000);
    }

    render() {
        return (
            <React.Fragment>
                <Toolbar id="toolbar" style={{padding: '0 10px 5px 5px'}}
                         visible={localStorage.getItem('authenticated') === 'true'}>
                    <Item
                        widget="dxButton"
                        options={this.menuButtonOptions}
                        location="before"/>
                    <Item
                        widget="dxButton"
                        options={this.refreshButtonOptions}
                        location="before"
                        style={{margin: '5px'}}/>
                    <Item
                        location="after"
                        widget="dxSelectBox"
                        locateInMenu="auto"
                        options={this.intervalSelectOptions}/>
                </Toolbar>
                <Drawer
                    minSize={37}
                    height={'100%'}
                    revealMode="expand"
                    openedStateMode="shrink"
                    component={NavigationList}
                    opened={this.state.isOpened}
                    visible={localStorage.getItem('authenticated') === 'true'}>
                    <div id="view" style={{margin: '0 5px 0 0'}}/>
                </Drawer>
            </React.Fragment>
        );
    }
}

export default MainComponent;