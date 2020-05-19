import React from 'react';
import Tabs from "devextreme-react/tabs";
import UserView from "./UserView";
import UserGroupView from "./UserGroupView";

const tabs = [
    {id: 'Users', text: 'Users', icon: 'material-icons-outlined ic-people md-18'},
    {id: 'UserGroups', text: 'User Groups', icon: 'material-icons-outlined ic-group md-18'}
];

class UserManagementView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 0
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
                <Tabs dataSource={tabs}
                      selectedIndex={this.state.selectedIndex}
                      onOptionChanged={this.onTabsSelectionChanged}/>
                <div>
                    <UserView hidden={this.state.selectedIndex !== 0}/>
                    <UserGroupView hidden={this.state.selectedIndex !== 1}/>
                </div>
            </React.Fragment>
        );
    }
}

export default UserManagementView;