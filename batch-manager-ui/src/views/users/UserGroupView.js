import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {UserGroupDataSource} from "./UserGroupDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import {Item} from "devextreme-react/autocomplete";
import UsergroupUserView from "./UsergroupUserView";
import {EmptyItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";

class UserGroupView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentUserGroup: {},
            showDetails: false
        };
        this.selectionChanged = this.selectionChanged.bind(this);
    }

    selectionChanged(e) {
        this.setState({currentUserGroup: e.data});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <div className="long-title"><h3>User Group List</h3></div>
                <DataGrid
                    id={'UserGroupTable'}
                    dataSource={UserGroupDataSource()}
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
                            <Item itemType="group" colCount={4} colSpan={4} caption={"User Group Details: " + this.state.currentUserGroup.name}>
                                <SimpleItem dataField="name" colSpan={2}>
                                    <RequiredRule message="Name is required"/>
                                    <StringLengthRule min={2} message="Name must be at least 2 characters long."/>
                                </SimpleItem>
                                <EmptyItem colSpan={2}/>
                                <SimpleItem dataField="description" editorType="dxTextArea" colSpan={4} editorOptions={{height: 100}}/>
                                <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                <EmptyItem colSpan={2}/>
                                <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                            </Item>
                            <Item itemType="group" colCount={4} colSpan={4} caption={"Users"}>
                                <UsergroupUserView userGroup={this.state.currentUserGroup}/>
                            </Item>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Name'}
                        dataField={'name'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Description'}
                        dataField={'description'}
                        visible={false}/>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}>
                        <FormItem editorType="dxCheckBox"/>
                    </Column>
                    <Column
                        dataField={'createdBy'}
                        caption={'Created By'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'createdAt'}
                        caption={'Created At'}
                        dataType={'datetime'}
                        visible={false}/>
                    <Column
                        dataField={'modifiedBy'}
                        caption={'Modified By'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'modifiedAt'}
                        caption={'Modified At'}
                        dataType={'datetime'}
                        visible={false}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit user group',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete user group',
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

export default UserGroupView;