import React from "react";
import List from "devextreme-react/list.js";
import {Tooltip} from 'devextreme-react/tooltip';
import history from "./History";

const navigation = [
    {
        id: 1,
        text: "Jobs",
        icon: 'material-icons-outlined ic-dashboard',
        filePath: "jobexecutions",
        hint: "Shows the list of job executions"
    },
    {
        id: 2,
        text: "Steps",
        icon: 'material-icons-outlined ic-bookmarks',
        filePath: "stepexecutions",
        hint: "Shows the list of step executions"
    },
    {
        id: 3,
        text: "Schedules",
        icon: 'material-icons-outlined ic-alarm',
        filePath: "jobschedules",
        hint: "Shows the list of job schedules"
    },
    {
        id: 4,
        text: "Settings",
        icon: 'material-icons-outlined ic-settings',
        filePath: "settings",
        hint: "General settings"
    },
    {
        id: 5,
        text: "Users",
        icon: 'material-icons-outlined ic-people',
        filePath: "users",
        hint: "User administration"
    },
    {
        id: 6,
        text: "Performance",
        icon: 'material-icons-outlined ic-performance',
        filePath: "performance",
        hint: "Performance data"
    }
];

class NavigationList extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            defaultVisible: false
        };
        this.toggleDefault = this.toggleDefault.bind(this);
        this.getMenuItem = this.getMenuItem.bind(this);
    }

    loadView(e) {
        history.push(e.addedItems[0].filePath);
    }

    getMenuItem(item) {
        return (
            <div style={{float: 'left'}}>
                <i className={item.icon} id={'nav-' + item.text}
                   onMouseEnter={this.toggleDefault} onMouseLeave={this.toggleDefault}/>
                <Tooltip
                    target={'#nav-' + item.text}
                    visible={this.state.defaultVisible}
                    closeOnOutsideClick={true}>
                    <div>{item.hint}</div>
                </Tooltip>
            </div>
        );
    }

    toggleDefault() {
        this.setState({
            defaultVisible: !this.state.defaultVisible
        });
    }

    render() {
        return (
            <React.Fragment>
                <List
                    items={navigation}
                    itemRender={this.getMenuItem}
                    selectionMode="single"
                    onSelectionChanged={this.loadView}/>
            </React.Fragment>
        );
    }
}

export default NavigationList;