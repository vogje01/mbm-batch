import React from 'react';
import Tabs from 'devextreme-react/tabs';

import JobDefinitionView from "../jobdefinitions/JobDefinitionView";
import JobSchedulerView from "../scheduler/JobSchedulerView";
import AgentView from "../agent/AgentView";
import JobGroupView from "../jobgroup/JobGroupView";

const tabs = [
    {id: 'Jobs', text: 'Job Definitions', icon: 'material-icons-outlined ic-dashboard md-18', content: 'Main'},
    {id: 'Schedules', text: 'Job Schedules', icon: 'material-icons-outlined ic-alarm md-18', content: 'Schedules'},
    {id: 'Groups', text: 'Job Groups', icon: 'material-icons-outlined ic-device-hub md-18', content: 'Groups'},
    {id: 'Agents', text: 'Batch Agents', icon: 'material-icons-outlined ic-developer-board md-18', content: 'Agents'}
];

class SettingsView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 0,
            refreshLists: {}
        };
        this.onTabsSelectionChanged = this.onTabsSelectionChanged.bind(this);
    }

    onTabsSelectionChanged(args) {
        if (args.name === 'selectedIndex') {
            this.setState({
                selectedIndex: args.value
            });
        }
    }

    render() {
        return (
            <React.Fragment>
                <div className="long-title"><h3>Settings</h3></div>
                <Tabs dataSource={tabs} selectedIndex={this.state.selectedIndex}
                      onOptionChanged={this.onTabsSelectionChanged}/>
                <div>
                    <JobDefinitionView hidden={this.state.selectedIndex !== 0}/>
                    <JobSchedulerView hidden={this.state.selectedIndex !== 1}/>
                    <JobGroupView hidden={this.state.selectedIndex !== 2}/>
                    <AgentView hidden={this.state.selectedIndex !== 3}/>
                </div>
            </React.Fragment>
        );
    }
}

export default SettingsView;