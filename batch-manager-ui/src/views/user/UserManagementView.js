import React from 'react';
import FisPage from "../../components/FisPage";
import Toolbar, {Item} from "devextreme-react/toolbar";
import Tabs from "devextreme-react/tabs";
import UserView from "./UserView";
import UserGroupView from "./UserGroupView";

const tabs = [
    {id: 'Users', text: 'Users', icon: 'material-icons-outlined ic-dashboard md-18', content: 'Users'},
    {id: 'UserGroups', text: 'User Groups', icon: 'material-icons-outlined ic-alarm md-18', content: 'UserGroups'}
];

class UserManagementView extends FisPage {
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
                <Toolbar>
                    <Item location="before"
                          locateInMenu="never">
                        <div className="toolbar-label"><b>Uer Management</b></div>
                    </Item>
                </Toolbar>
                <Tabs dataSource={tabs} selectedIndex={this.state.selectedIndex}
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