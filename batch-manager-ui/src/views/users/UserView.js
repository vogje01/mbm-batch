import React from 'react';
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from 'devextreme-react/data-grid';
import {userDataSource} from "./UserDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import {Item} from "devextreme-react/autocomplete";
import UserUsergroupView from "./UserUsergroupView";

class UserView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            showDetails: false,
            currentUser: {},
            refreshLists: {}
        };
        this.selectionChanged = this.selectionChanged.bind(this);
    }

    selectionChanged(e) {
        this.setState({currentUser: e.data});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <div className="long-title"><h3>User List</h3></div>
                <DataGrid
                    id={'UserTable'}
                    dataSource={userDataSource()}
                    hoverStateEnabled={true}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    rowAlternationEnabled={true}
                    onEditingStart={this.selectionChanged}
                    style={{padding: "5px 0px 0px 0px"}}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode="form"
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <Form>
                            <Item itemType="group" colCount={2} colSpan={2} caption={"User Details"}>
                                <Item dataField="userId"/>
                                <Item dataField="password" editorType={"dxTextBox"} editorOptions={{mode: "password"}}/>
                                <Item dataField="firstName"/>
                                <Item dataField="lastName"/>
                                <Item
                                    dataField="description"
                                    editorType="dxTextArea"
                                    colSpan={2}
                                    editorOptions={{height: 100}}/>
                                <Item dataField="active" editorType={"dxCheckBox"}/>
                            </Item>
                            <Item itemType="group" colCount={2} colSpan={2} caption={"User Groups"}>
                                <UserUsergroupView user={this.state.currentUser}/>
                            </Item>
                        </Form>
                    </Editing>
                    <Column
                        caption={'UserID'}
                        dataField={'userId'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'password'}
                        caption={'Password'}
                        visible={false}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'firstName'}
                        caption={'First name'}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        dataField={'lastName'}
                        caption={'Last name'}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        dataField={'version'}
                        caption={'Version'}
                        dataType={'string'}
                        visible={false}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit user',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete user',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <RemoteOperations
                        sorting={true}
                        paging={true}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                </DataGrid>
                <UpdateTimer/>
            </React.Fragment>
        );
    }
}

export default UserView;